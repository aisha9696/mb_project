package kz.mb.project.mb_project.dto.auth.keycloak;

import java.io.Serializable;

public enum KAction implements Serializable {
  CONFIGURE_TOTP, UPDATE_PASSWORD;
}
