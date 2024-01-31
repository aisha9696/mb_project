package kz.mb.project.mb_project.dto.keycloak;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateKUser {
  private String username;
  private Boolean enabled;
  private Boolean totp;
  private Boolean emailVerified;
  private String firstName;
  private String lastName;
  private String email;
  private KAction[] requiredActions;
  private KAttributes attributes;
}
