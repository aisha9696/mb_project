package kz.mb.project.mb_project.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import kz.mb.project.mb_project.dto.auth.keycloak.CreateKUser;
import kz.mb.project.mb_project.dto.auth.keycloak.KAction;
import kz.mb.project.mb_project.dto.auth.keycloak.KUser;
import kz.mb.project.mb_project.dto.auth.request.CreateEmployeeRequest;
import kz.mb.project.mb_project.dto.auth.request.CreateUserRequest;
import kz.mb.project.mb_project.dto.auth.request.UpdateUserRequest;
import kz.mb.project.mb_project.dto.auth.response.UserInfoResponse;
import kz.mb.project.mb_project.dto.auth.response.UserResponse;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserDetail;

public class UserMapper {

  public static CreateKUser toCreateKUserConvert(CreateUserRequest request) {
    return CreateKUser.builder()
        .username(request.phoneNumber())
        .enabled(false).totp(false)
        .emailVerified(true)
        .firstName(request.firstname())
        .lastName(request.lastname())
        .email(request.email())
        .requiredActions(new KAction[]{KAction.CONFIGURE_TOTP, KAction.UPDATE_PASSWORD})
        .build();

  }

  public static KUser toKUserConvert(UpdateUserRequest request) {
    List<KAction> actionList = new ArrayList<>();
    if (request.toPasswordUpdate()) {
      actionList.add(KAction.UPDATE_PASSWORD);
    }
    if (request.toVerifyOtp()) {
      actionList.add(KAction.CONFIGURE_TOTP);
    }
    KAction[] actions = actionList.toArray(KAction[]::new);
    return KUser.builder()
        .id(request.id().toString())
        .username(request.phoneNumber())
        .enabled(request.toEnable())
        .totp(request.toVerifyOtp())
        .emailVerified(request.toVerifyEmail())
        .firstName(request.firstname())
        .lastName(request.lastname())
        .email(request.email())
        .requiredActions(actions)
        .build();
  }

  public static UserInfoResponse toLoginResponseDtoConverter(KUser kUser) {
    return UserInfoResponse.builder()
        .id(UUID.fromString(kUser.getId()))
        .email(kUser.getEmail())
        .username(kUser.getUsername())
        .firstName(kUser.getFirstName())
        .lastName(kUser.getLastName())
        .enabled(kUser.getEnabled())
        .membership(new ArrayList<>())
        .passwordUpdated(
            Arrays.asList(kUser.getRequiredActions()).contains(KAction.UPDATE_PASSWORD)
        ).build();
  }

  public static CreateUserRequest toCreateUserRequest(CreateEmployeeRequest employeeRequest) {
    return CreateUserRequest.builder()
        .phoneNumber(employeeRequest.phoneNumber())
        .lastname(employeeRequest.lastname())
        .firstname(employeeRequest.firstname())
        .email(employeeRequest.email())
        .phoneNumber(employeeRequest.phoneNumber())
        .build();
  }

}
