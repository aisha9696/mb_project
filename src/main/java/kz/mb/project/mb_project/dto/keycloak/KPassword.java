package kz.mb.project.mb_project.dto.keycloak;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KPassword {
  private Boolean temporary;
  private String value;
  private String type;
}
