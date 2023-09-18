package kz.mb.project.mb_project.dto;

import lombok.Data;


@Data
public class TokenResponse {
  private String access_token;
  private Integer expires_in;
  private String refresh_token;
  private Integer refresh_expires_in;
  private String token_type;
  private String id_token;
  private String session_state;
  private String scope;

}
