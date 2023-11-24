package kz.mb.project.mb_project.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.mb.project.mb_project.converter.KUserLoginResponseConverter;
import kz.mb.project.mb_project.dto.CreateUserRequest;
import kz.mb.project.mb_project.dto.LoginRequest;
import kz.mb.project.mb_project.dto.LoginResponseDto;
import kz.mb.project.mb_project.dto.OtpDto;
import kz.mb.project.mb_project.dto.SmsResponse;
import kz.mb.project.mb_project.dto.SuccessMessage;
import kz.mb.project.mb_project.dto.TokenResponse;
import kz.mb.project.mb_project.dto.UserLockedResponse;
import kz.mb.project.mb_project.dto.keycloak.CreateKUser;
import kz.mb.project.mb_project.dto.keycloak.KAction;
import kz.mb.project.mb_project.dto.keycloak.KUser;
import kz.mb.project.mb_project.entity.Business;
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
import kz.mb.project.mb_project.repo.BusinessRepository;
import kz.mb.project.mb_project.repo.UserBusinessRepository;
import kz.mb.project.mb_project.repo.UsersRepository;
import kz.mb.project.mb_project.utils.PhoneNumberUtils;
import kz.mb.project.mb_project.utils.RandomUtils;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  protected final UsersRepository usersRepository;
  protected final ConversionService conversionService;
  protected final KeycloakService keycloakService;
  protected final KUserLoginResponseConverter kUserLoginResponseConverter;
  protected final UserBusinessRepository userBusinessRepository;
  protected final SmsService smsService;
  protected final OtpService otpService;
  protected final BusinessRepository businessRepository;

  public UserServiceImpl(
      @Autowired
      UsersRepository usersRepository,
      @Autowired
      @Qualifier("mvcConversionService")
      ConversionService conversionService,
      KeycloakService keycloakService,
      KUserLoginResponseConverter kUserLoginResponseConverter,
      UserBusinessRepository userBusinessRepository, SmsService smsService, OtpService otpService,
      BusinessRepository businessRepository) {
    this.usersRepository = usersRepository;
    this.conversionService = conversionService;
    this.keycloakService = keycloakService;
    this.kUserLoginResponseConverter = kUserLoginResponseConverter;
    this.userBusinessRepository = userBusinessRepository;
    this.smsService = smsService;
    this.otpService = otpService;
    this.businessRepository = businessRepository;
  }

  @Override
  @Transactional
  public void createUser(CreateUserRequest createUserRequest) {
    usersRepository.findUserDetailByUsername(createUserRequest.getPhone_number())
        .ifPresent(userDetail -> {
          throw new FoundException(ErrorMessage.USER_FOUND_EXCEPTION);
        });
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.AUTHORIZATION_ERROR);
    }
    CreateKUser user = conversionService.convert(createUserRequest, CreateKUser.class);
    String uid = keycloakService.createUser(user, token);
    if (uid == null) {
      throw new InternalServerException(ErrorMessage.USER_CREATE_EXCEPTION);
    }
    String[] uidParts = uid.split("/");
    uid = uidParts[uidParts.length - 1];
    UserDetail detail = UserDetail.builder().username(createUserRequest.getPhone_number())
        .id(UUID.fromString(uid)).temporal(true).build();
    usersRepository.save(detail);
  }

  @Override
  @Transactional
  public TokenResponse signIn(LoginRequest loginRequest) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(
        loginRequest.getUsername());
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.AUTHORIZATION_ERROR);
    }
    UserLockedResponse locked = keycloakService.isUserLocked(user.get().getId().toString(), token)
        .block();
    if (locked != null && locked.getDisabled()) {
      KUser kUser = KUser.builder().id(user.get().getId().toString())
          .requiredActions(new KAction[]{KAction.UPDATE_PASSWORD, KAction.CONFIGURE_TOTP})
          .totp(false).build();
      keycloakService.updateUser(kUser, token);
      throw new ForbiddenException(ErrorMessage.USER_LOCKED);
    }
    return keycloakService.getToken(loginRequest.getUsername(), loginRequest.getPassword()).block();
  }

  @Override
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

  @Override
  public LoginResponseDto userInfo(String username) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(
        username);
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.INCORRECT_PASSWORD);
    }
    KUser kUser = keycloakService.getUser(user.get().getId().toString(), token);
    if (kUser == null) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    List<UserBusiness> userBusiness = userBusinessRepository.findAllByUserUsername(username);
    LoginResponseDto loginResponseDto = kUserLoginResponseConverter.convert(kUser);
    loginResponseDto.setPhoto(user.get().getPhoto());
    loginResponseDto.setMembership(userBusiness);
    loginResponseDto.setDetail(user.get());
    return loginResponseDto;
  }

  @Override
  public TokenResponse refresh(String refreshToken) {
    return keycloakService.refreshToken(refreshToken).block();
  }

  @Override
  public void logout(String userId) {
    Optional<UserDetail> user = usersRepository.findById(UUID.fromString(userId));
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    keycloakService.logout(userId);
  }

  @Override
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
    OtpDto otpDto = otpService.generateOtp(username, 180, messageText);
    messageText = String.format(messageText, otpDto.getOtp());
    SmsResponse response = smsService.sendSMS(username, messageText).block();
    if (response == null) {
      throw new InternalServerException(ErrorMessage.SMS_SENDING_ERROR);
    }
    log.info("СМС с проверечным кодом был отправлен.Текст SMS : " + messageText);
  }

  @Override
  @Transactional
  public Boolean checkOtp(String otp, String username) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(username);
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    boolean checked = otpService.checkOtp(username, otp) != null;
    if (checked) {
      TokenResponse token = keycloakService.getClientCredentialToken().block();
      if (token == null) {
        throw new NotAuthorizedException(ErrorMessage.INCORRECT_PASSWORD);
      }
      KUser kUser = keycloakService.getUser(user.get().getId().toString(), token);
      if (kUser == null) {
        throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
      }
      kUser.setRequiredActions(new KAction[]{KAction.UPDATE_PASSWORD});
      keycloakService.updateUser(kUser, token);
      otpService.checkAndDeleteOtp(username, otp);
    }
    return checked;
  }

  @Override
  @Transactional
  public void deleteTemporalUser(UserRole role) {
    TokenResponse token = keycloakService.getClientCredentialToken().block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.INCORRECT_PASSWORD);
    }
    usersRepository.findAllByTemporalIsTrue().forEach(userDetail -> {
      if (userBusinessRepository.findAllByUserUsername(userDetail.getUsername()).stream()
          .anyMatch(userBusiness -> userBusiness.getUserRoles() == role)) {
        keycloakService.deleteUser(userDetail.getId().toString(), token);
        usersRepository.delete(userDetail);
      }
    });
  }

  @Override
  @Transactional
  public void resetPassword(String username, String oldPassword, String password) {
    Optional<UserDetail> user = usersRepository.findUserDetailByUsername(
        username);
    if (user.isEmpty()) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
    }
    TokenResponse token = keycloakService.getToken(username, oldPassword).block();
    if (token == null) {
      throw new NotAuthorizedException(ErrorMessage.AUTHORIZATION_ERROR);
    } else {
      keycloakService.setCredentials(user.get().getId().toString(), password, token);
      KUser kUser = KUser.builder().id(user.get().getId().toString())
          .requiredActions(new KAction[]{KAction.UPDATE_PASSWORD, KAction.CONFIGURE_TOTP})
          .totp(false).enabled(false).build();
      keycloakService.updateUser(kUser, token);
      keycloakService.logout(user.get().getId().toString());
    }
  }


}
