package kz.mb.project.mb_project.dto.keycloak;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class KTokenRequest implements Serializable {
  private String grant_type;
  private String username;
  private String password;
  private String scope;
}
