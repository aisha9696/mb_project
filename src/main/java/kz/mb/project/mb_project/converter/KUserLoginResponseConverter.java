package kz.mb.project.mb_project.converter;

import java.util.Arrays;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import kz.mb.project.mb_project.dto.LoginResponseDto;
import kz.mb.project.mb_project.dto.keycloak.KAction;
import kz.mb.project.mb_project.dto.keycloak.KUser;

@Component
public class KUserLoginResponseConverter implements Converter<KUser, LoginResponseDto> {

  @Override
  public LoginResponseDto convert(KUser kUser) {
    LoginResponseDto responseDto = new LoginResponseDto();
    responseDto.setId(UUID.fromString(kUser.getId()));
    responseDto.setEmail(kUser.getEmail());
    responseDto.setUsername(kUser.getUsername());
    responseDto.setFirstName(kUser.getFirstName());
    responseDto.setLastName(kUser.getLastName());
    responseDto.setEnabled(kUser.getEnabled());
    responseDto.setFace_id(kUser.getAttributes().getFace_id()[0].equals("true"));
    responseDto.setPassword_updated(!Arrays.stream(kUser.getRequiredActions())
        .allMatch(action -> action.equals(KAction.UPDATE_PASSWORD)));

    return responseDto;
  }
}
