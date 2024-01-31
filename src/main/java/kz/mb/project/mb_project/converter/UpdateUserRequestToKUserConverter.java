package kz.mb.project.mb_project.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import kz.mb.project.mb_project.dto.UpdateUserRequest;
import kz.mb.project.mb_project.dto.keycloak.KAction;
import kz.mb.project.mb_project.dto.keycloak.KAttributes;
import kz.mb.project.mb_project.dto.keycloak.KUser;

@Component
public class UpdateUserRequestToKUserConverter implements
    Converter<UpdateUserRequest, KUser> {

  @Override
  public KUser convert(UpdateUserRequest source) {
    List<KAction> actionList = new ArrayList<>();
    if(source.getToPasswordUpdate()){
      actionList.add(KAction.UPDATE_PASSWORD);
    }
    if(source.getToVerifyOtp()){
      actionList.add(KAction.CONFIGURE_TOTP);
    }
    KAction [] actions = actionList.toArray(KAction[]::new);

    return KUser.builder()
        .id(source.getId().toString())
        .username(source.getPhone_number())
        .enabled(source.getToEnable())
        .totp(source.getToVerifyOtp())
        .emailVerified(source.getToVerifyEmail())
        .firstName(source.getFirstname())
        .lastName(source.getLastname())
        .email(source.getEmail())
        .requiredActions(actions)
        .attributes(KAttributes.builder().face_id(source.getFace_id() == null? new String[]{Boolean.FALSE.toString()}:new String[]{source.getFace_id().toString()})
            .build())
        .build();
  }
}
