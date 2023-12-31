package kz.mb.project.mb_project.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@Configuration
@Getter
public class KeycloakConfiguration {
  @Value("${keycloak.auth-server-url}")
  public String serverURL;
  @Value("${keycloak.user_url}")
  public String userURL;
  @Value("${keycloak.token_url}")
  public String tokenURL;
  @Value("${keycloak.realm}")
  public String realm;
  @Value("${keycloak.resource}")
  public String clientID;
  @Value("${keycloak.credentials.secret}")
  public String clientSecret;
  @Value("${keycloak.authorization_url}")
  public String authenticationURL;
  @Value("${keycloak.user_locked_url}")
  public String userLockedURL;

  private static Keycloak keycloak = null;

  public Keycloak getInstance() {
    if (keycloak == null) {

      return KeycloakBuilder.builder()
          .realm(realm)
          .serverUrl(serverURL)
          .clientId(clientID)
          .clientSecret(clientSecret)
          .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
          .build();
    }
    return keycloak;
  }
}