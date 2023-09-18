package kz.mb.project.mb_project.dto.keycloak;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KTokenRequest implements Serializable {
  private String grant_type;
  private String username;
  private String password;
  private String scope;
}
