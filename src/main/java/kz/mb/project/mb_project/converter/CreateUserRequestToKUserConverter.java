package kz.mb.project.mb_project.converter;

import kz.mb.project.mb_project.dto.CreateUserRequest;
import kz.mb.project.mb_project.dto.keycloak.CreateKUser;
import kz.mb.project.mb_project.dto.keycloak.KAction;
import kz.mb.project.mb_project.dto.keycloak.KAttributes;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateUserRequestToKUserConverter implements
    Converter<CreateUserRequest, CreateKUser> {

  @Override
  public CreateKUser convert(CreateUserRequest source) {
    return CreateKUser.builder()
        .username(source.getPhone_number())
        .enabled(false)
        .totp(false)
        .emailVerified(true)
        .firstName(source.getFirstname())
        .lastName(source.getLastname())
        .email(source.getEmail())
        .requiredActions(new KAction[]{KAction.CONFIGURE_TOTP, KAction.UPDATE_PASSWORD})
        .attributes(KAttributes.builder().face_id(source.getFace_id() == null? new String[]{Boolean.FALSE.toString()}:new String[]{source.getFace_id().toString()})
            .build())
        .build();
  }
}
