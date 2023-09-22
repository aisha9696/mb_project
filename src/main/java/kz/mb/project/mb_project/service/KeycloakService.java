package kz.mb.project.mb_project.service;

import reactor.core.publisher.Mono;
import kz.mb.project.mb_project.dto.TokenResponse;
import kz.mb.project.mb_project.dto.keycloak.CreateKUser;
import kz.mb.project.mb_project.dto.keycloak.KUser;

public interface KeycloakService {

  String createUser(CreateKUser user, TokenResponse token);

  Mono<TokenResponse> getClientCredentialToken();

  void updateUser(KUser user, TokenResponse token);

  void deleteUser(String user_id, TokenResponse token);

  void setCredentials(String user_id, String password, TokenResponse token);

  KUser getUser(String user_id, TokenResponse token);

  Mono<TokenResponse> getToken(String username, String password);

  Mono<TokenResponse> refreshToken(String refreshToken);

  void logout(String userId);
}
