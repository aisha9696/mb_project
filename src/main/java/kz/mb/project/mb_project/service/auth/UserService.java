package kz.mb.project.mb_project.service.auth;

import static kz.mb.project.mb_project.mapper.UserMapper.toCreateKUserConvert;
import static kz.mb.project.mb_project.mapper.UserMapper.toKUserConvert;
import static kz.mb.project.mb_project.mapper.UserMapper.toLoginResponseDtoConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.mb.project.mb_project.dto.auth.keycloak.CreateKUser;
import kz.mb.project.mb_project.dto.auth.keycloak.KAction;
import kz.mb.project.mb_project.dto.auth.keycloak.KUser;
import kz.mb.project.mb_project.dto.auth.request.CreateUserRequest;
import kz.mb.project.mb_project.dto.auth.request.LoginRequest;
import kz.mb.project.mb_project.dto.auth.request.UpdateUserRequest;
import kz.mb.project.mb_project.dto.auth.response.OtpCheckResponse;
import kz.mb.project.mb_project.dto.auth.response.UserInfoResponse;
import kz.mb.project.mb_project.dto.auth.response.TokenResponse;
import kz.mb.project.mb_project.dto.auth.response.UserLockedResponse;
import kz.mb.project.mb_project.dto.auth.response.UserResponse;
import kz.mb.project.mb_project.entity.Otp;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserDetail;
import kz.mb.project.mb_project.entity.UserRole;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.ForbiddenException;
import kz.mb.project.mb_project.exception.FoundException;
import kz.mb.project.mb_project.exception.InternalServerException;
import kz.mb.project.mb_project.exception.InvalidRequestException;
import kz.mb.project.mb_project.exception.NotAuthorizedException;
import kz.mb.project.mb_project.exception.NotFoundException;
import kz.mb.project.mb_project.repository.BusinessRepository;
import kz.mb.project.mb_project.repository.UserBusinessRepository;
import kz.mb.project.mb_project.repository.UsersRepository;
import kz.mb.project.mb_project.service.KeycloakService;
import kz.mb.project.mb_project.service.NotificationService;
import kz.mb.project.mb_project.service.OtpService;
import kz.mb.project.mb_project.utils.PhoneNumberUtils;
import kz.mb.project.mb_project.utils.ValidationUtils;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

  protected final UsersRepository usersRepository;
  protected final KeycloakService keycloakService;
  protected final UserBusinessRepository userBusinessRepository;
  protected final NotificationService notificationService;
  protected final OtpService otpService;
  protected final BusinessRepository businessRepository;

  static Function<String, String> getIDFromUrl = url -> {
    String[] uidParts = url.split("/");
    return uidParts[uidParts.length - 1];
  };


  @Transactional
  public UserInfoResponse create(CreateUserRequest createUserRequest) {
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.AUTHORIZATION_ERROR);
    }
    if (usersRepository.findUserDetailByUsername(createUserRequest.phoneNumber()).isPresent()
        && keycloakService.getUsers(token).stream()
        .anyMatch(user -> user.getUsername().equals(createUserRequest.phoneNumber()))) {
      throw new FoundException(ErrorMessage.USER_FOUND_EXCEPTION);
    }
    if (PhoneNumberUtils.ensureKzCtnWithCountryCode(createUserRequest.phoneNumber()) == null) {
      throw new InvalidRequestException(ErrorMessage.CREATE_INCORRECT_PHONE_NUMBER);
    }
    if (usersRepository.findUserDetailByEmail(createUserRequest.email()).isPresent()
        && keycloakService.getUsers(token).stream()
        .anyMatch(user -> user.getEmail().equals(createUserRequest.email()))) {
      throw new FoundException(ErrorMessage.USER_CREATE_EMAIL_EXCEPTION);
    }
    if (!ValidationUtils.validateEmail(createUserRequest.email())) {
      throw new InvalidRequestException(ErrorMessage.INVALID_EMAIL);
    }
    CreateKUser user = toCreateKUserConvert(createUserRequest);
    String uid = keycloakService.createUser(user, token);
    uid = getIDFromUrl.apply(uid);
    if (uid == null) {
      throw new InternalServerException(ErrorMessage.USER_CREATE_EXCEPTION);
    }
    UserDetail detail = UserDetail.builder().username(createUserRequest.phoneNumber())
        .id(UUID.fromString(uid)).temporal(true).firstName(createUserRequest.firstname())
        .lastName(createUserRequest.lastname())
        .email(createUserRequest.email()).build();
    usersRepository.save(detail);
    sendConfirmationOtp(createUserRequest.phoneNumber());
    return userInfo(createUserRequest.phoneNumber());
  }


  public void updateUser(UpdateUserRequest updateUserRequest) {
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.AUTHORIZATION_ERROR);
    }
    if (usersRepository.findUserDetailByUsername(updateUserRequest.phoneNumber()).isEmpty()
        && keycloakService.getUsers(token).stream()
        .noneMatch(user -> user.getUsername().equals(updateUserRequest.phoneNumber()))) {
      throw new FoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    KUser user = toKUserConvert(updateUserRequest);
    keycloakService.updateUser(user, token);
    UserDetail detail = UserDetail.builder().username(updateUserRequest.phoneNumber())
        .id(updateUserRequest.id()).temporal(updateUserRequest.toTemporal())
        .firstName(updateUserRequest.firstname())
        .lastName(updateUserRequest.lastname())
        .email(updateUserRequest.email()).build();
    usersRepository.save(detail);
  }


  @Transactional
  public TokenResponse signIn(LoginRequest loginRequest) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(
        loginRequest.username());
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.AUTHORIZATION_ERROR);
    }
    UserLockedResponse locked = keycloakService.isUserLocked(user.get().getId().toString(), token)
        .block();
    if (locked != null && locked.disabled()) {
      KUser kUser = KUser.builder().id(user.get().getId().toString())
          .requiredActions(new KAction[]{KAction.UPDATE_PASSWORD, KAction.CONFIGURE_TOTP})
          .totp(false).build();
      keycloakService.updateUser(kUser, token);
      throw new ForbiddenException(ErrorMessage.USER_LOCKED);
    }
    return keycloakService.getToken(loginRequest.username(), loginRequest.password()).block();
  }

  @Transactional
  public void setPasswordAfterSmsVerification(String username, String password, String hash) {
    var otp = otpService.getOpt(username);
    if (!otp.getOtpHash().equalsIgnoreCase(hash)) {
      throw new InvalidRequestException(ErrorMessage.INCCORECT_HASH_SET_PASSWORD);
    }
    setPassword(username, password);
    otp.setVerified(true);
    otpService.setOtp(otp);
  }

  @Transactional
  public void setPassword(String username, String password) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(
        username);
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.AUTHORIZATION_ERROR);
    }
    KUser kUser = keycloakService.getUser(user.get().getId().toString(), token);
    if (kUser == null) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    if (Arrays.asList(kUser.getRequiredActions()).contains(KAction.CONFIGURE_TOTP)) {
      throw new InvalidRequestException(ErrorMessage.USER_UPDATE_EXCEPTION);
    }

    kUser.setRequiredActions(new KAction[]{});
    UserDetail toUpdateUser = user.get();
    toUpdateUser.setTemporal(false);
    kUser.setEnabled(true);

    keycloakService.setCredentials(kUser.getId(), password, token);
    keycloakService.updateUser(kUser, token);
    usersRepository.save(toUpdateUser);
  }


  public UserInfoResponse userInfo(String username) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(
        username);
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    KUserWithToken userWithToken = getKUserWithToken(user.get());
    List<UserBusiness> userBusiness = userBusinessRepository.findAllByUserUsername(username);
    UserInfoResponse userInfoResponse = toLoginResponseDtoConverter(userWithToken.user);
    userInfoResponse.membership().addAll(userBusiness);
    return userInfoResponse;
  }


  public TokenResponse refresh(String refreshToken) {
    return keycloakService.refreshToken(refreshToken).block();
  }


  public void logout(String userId) {
    Optional<UserDetail> user = usersRepository.findById(UUID.fromString(userId));
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    keycloakService.logout(userId);
  }


  @Transactional
  public void sendConfirmationOtp(String username) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(
        username);
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    String messageText = "MB_App:%s-код для доступа";
    if (PhoneNumberUtils.ensureKzCtnWithCountryCode(username) == null) {
      throw new InternalServerException(ErrorMessage.INVALID_PHONE_NUMBER);
    }
    otpService.generateOtp(username, messageText);
    notificationService.performSMSNotification(username, "", messageText);
    log.info("СМС с проверечным кодом был отправлен.Текст SMS : " + messageText);
  }


  @Transactional
  public OtpCheckResponse checkOtp(String otp, String username) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(username);
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    Otp checked = otpService.checkOtp(username, otp);
    if (checked != null) {
      KUserWithToken userWithToken = getKUserWithToken(user.get());
      userWithToken.user.setRequiredActions(new KAction[]{KAction.UPDATE_PASSWORD});
      keycloakService.updateUser(userWithToken.user, userWithToken.token);
    } else {
      throw new InvalidRequestException(ErrorMessage.OTP_NOT_VERIFIED);
    }
    return new OtpCheckResponse(true, checked.getOtpHash());
  }


  @Transactional(rollbackFor = {InvalidRequestException.class, InternalServerException.class})
  public void deleteTemporalUser(UserRole role) {
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.INCORRECT_PASSWORD);
    }
    List<UserDetail> toDelete = usersRepository.findAllByTemporalIs(Boolean.TRUE);
    toDelete.forEach(userDetail -> {
      if ((userBusinessRepository.findAllByUserUsername(userDetail.getUsername()).stream()
          .anyMatch(userBusiness -> userBusiness.getUserRoles() == role)
          || userBusinessRepository.findAllByUserUsername(userDetail.getUsername()).isEmpty()) &&
          keycloakService.getUsers(token).stream()
              .anyMatch(user -> user.getUsername().equals(userDetail.getUsername()))) {
        keycloakService.deleteUser(userDetail.getId().toString(), token);
        usersRepository.delete(userDetail);
      }
    });
  }

  public Optional<UserDetail> getUserDetail(String username) {
    return usersRepository.findUserDetailByUsername(username);
  }

  private KUserWithToken getKUserWithToken(UserDetail user) {
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.INCORRECT_PASSWORD);
    }
    KUser kUser = keycloakService.getUser(user.getId().toString(), token);
    if (kUser == null) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    return new KUserWithToken(token, kUser);
  }

  private record KUserWithToken(
      TokenResponse token,
      KUser user
  ) {

  }
}
