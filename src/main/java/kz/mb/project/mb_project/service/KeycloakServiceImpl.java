package kz.mb.project.mb_project.service;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import kz.mb.project.mb_project.config.KeycloakConfiguration;
import kz.mb.project.mb_project.dto.TokenResponse;
import kz.mb.project.mb_project.dto.UserLockedResponse;
import kz.mb.project.mb_project.dto.keycloak.CreateKUser;
import kz.mb.project.mb_project.dto.keycloak.KPassword;
import kz.mb.project.mb_project.dto.keycloak.KUser;
import kz.mb.project.mb_project.exception.AuthorizationException;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.InternalServerException;
import kz.mb.project.mb_project.exception.InvalidRequestException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

  protected final KeycloakConfiguration keycloakConfiguration;
  WebClient webClient;


  @Autowired
  public KeycloakServiceImpl(KeycloakConfiguration keycloakConfiguration) {
    this.keycloakConfiguration = keycloakConfiguration;
    this.webClient = WebClient.create();
  }

  @Override
  public String createUser(CreateKUser user, TokenResponse token) {
    Mono<CreateKUser> userMono = Mono.just(user);
    String url = String.format(keycloakConfiguration.serverURL + keycloakConfiguration.userURL,
        keycloakConfiguration.realm);
    log.info(userMono.block().toString());
    return webClient.post()
        .uri(url)
        .header("Authorization", "Bearer " + token.getAccess_token())
        .contentType(MediaType.APPLICATION_JSON)
        .body(userMono, CreateKUser.class)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          throw new InvalidRequestException(ErrorMessage.USER_CREATE_EXCEPTION);
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          throw new InternalServerException(ErrorMessage.USER_CREATE_EXCEPTION);
        })
        .toEntity(String.class)
        .flatMap(responseEntity -> {
          System.out.println("Status: " + responseEntity.getStatusCode().value());
          System.out.println(
              "Location URI: " + responseEntity.getHeaders().getLocation().toString());
          System.out.println("Created New Employee : " + responseEntity.getBody());
          return Mono.just(responseEntity);
        }).map(responseEntity -> responseEntity.getHeaders().getLocation().toString()).block();
  }

  @Override
  public Mono<TokenResponse> getClientCredentialToken() {
    String url = String.format(keycloakConfiguration.serverURL + keycloakConfiguration.tokenURL,
        keycloakConfiguration.realm);
    String token = keycloakConfiguration.clientID + ":" + keycloakConfiguration.clientSecret;
    String encodedClientData =
        Base64.getEncoder().encodeToString(token.getBytes());
    return webClient.post()
        .uri(url)
        .header("Authorization", "Basic " + encodedClientData)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("grant_type", "client_credentials")
            .with("scope", "openid"))
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          return Mono.just(
              new InvalidRequestException(ErrorMessage.KEYCLOAK_ADMIN_AUTH_ERROR));
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          return Mono.just(
              new InternalServerException(ErrorMessage.KEYCLOAK_ADMIN_AUTH_ERROR));
        })
        .bodyToMono(TokenResponse.class);
  }

  @Override
  public void updateUser(KUser user, TokenResponse token) {
    Mono<KUser> userMono = Mono.just(user);
    String url = String.format(
        keycloakConfiguration.serverURL + keycloakConfiguration.userURL + "/" + user.getId(),
        keycloakConfiguration.realm);

    webClient.put()
        .uri(url)
        .header("Authorization", "Bearer " + token.getAccess_token())
        .contentType(MediaType.APPLICATION_JSON)
        .body(userMono, KUser.class)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          throw new InvalidRequestException(ErrorMessage.USER_UPDATE_EXCEPTION);
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          throw new InternalServerException(ErrorMessage.USER_UPDATE_EXCEPTION);
        })
        .toEntity(Void.class)
        .block();

  }

  @Override
  public void deleteUser(String user_id, TokenResponse token) {
    String url = String.format(
        keycloakConfiguration.serverURL + keycloakConfiguration.userURL + "/" + user_id,
        keycloakConfiguration.realm);

    webClient.delete()
        .uri(url)
        .header("Authorization", "Bearer " + token.getAccess_token())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          throw new InvalidRequestException(ErrorMessage.USER_DELETE_EXCEPTION);
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          throw new InternalServerException(ErrorMessage.USER_DELETE_EXCEPTION);
        })
        .toEntity(Void.class)
        .block();
  }

  @Override
  public void setCredentials(String user_id, String password, TokenResponse token) {
    String url = String.format(
        keycloakConfiguration.serverURL + keycloakConfiguration.userURL + "/" + user_id
            + "/reset-password",
        keycloakConfiguration.realm);

    Mono<KPassword> passwordMono = Mono.just(
        KPassword.builder().temporary(true).type("PASSWORD").value(password).build());
    webClient.put().uri(url)
        .header("Authorization", "Bearer " + token.getAccess_token())
        .contentType(MediaType.APPLICATION_JSON)
        .body(passwordMono, KPassword.class)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          throw new InvalidRequestException(
              ErrorMessage.USER_SET_PASSWORD_EXCEPTION);
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          throw new InternalServerException(
              ErrorMessage.USER_SET_PASSWORD_EXCEPTION);
        })
        .toEntity(Void.class)
        .block();

  }

  @Override
  public KUser getUser(String user_id, TokenResponse token) {
    String url = String.format(
        keycloakConfiguration.serverURL + keycloakConfiguration.userURL + "/" + user_id,
        keycloakConfiguration.realm);
    return webClient.get()
        .uri(url)
        .header("Authorization", "Bearer " + token.getAccess_token())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          throw new InvalidRequestException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          throw new InternalServerException(ErrorMessage.INCORRECT_USER);
        }).bodyToMono(KUser.class).block();
  }

  @Override
  public Mono<TokenResponse> getToken(String username, String password) {
    String url = String.format(
        keycloakConfiguration.serverURL + keycloakConfiguration.tokenURL,
        keycloakConfiguration.realm);
    String token = keycloakConfiguration.clientID + ":" + keycloakConfiguration.clientSecret;
    String encodedClientData =
        Base64.getEncoder().encodeToString(token.getBytes());
    return webClient.post()
        .uri(url)
        .header("Authorization", "Basic " + encodedClientData)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("grant_type", "password")
            .with("scope", "openid")
            .with("username", username)
            .with("password", password))
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          if (response.statusCode().value() == 401) {
            return Mono.just(
                new AuthorizationException(ErrorMessage.INCORRECT_PASSWORD));
          } else {
            return Mono.just(
                new AuthorizationException(ErrorMessage.INVALID_USER));
          }

        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          return Mono.just(
              new InternalServerException(ErrorMessage.AUTHORIZATION_ERROR));
        })
        .bodyToMono(TokenResponse.class);

  }

  @Override
  public Mono<TokenResponse> refreshToken(String refreshToken) {
    String url = String.format(
        keycloakConfiguration.serverURL + keycloakConfiguration.tokenURL,
        keycloakConfiguration.realm);
    return webClient.post()
        .uri(url)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters
            .fromFormData("client_id", keycloakConfiguration.clientID)
            .with("client_secret", keycloakConfiguration.clientSecret)
            .with("refresh_token", refreshToken)
            .with("grant_type", "refresh_token"))
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          return Mono.just(
              new AuthorizationException(ErrorMessage.REFRESH_TOKEN_EXPIRED));
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          return Mono.just(
              new InternalServerException(ErrorMessage.AUTHORIZATION_ERROR));
        })
        .bodyToMono(TokenResponse.class);
  }



  @Override
  public void logout(String userId) {
    Keycloak keycloak = keycloakConfiguration.getInstance();
    UserResource usersResource = keycloak.realm(keycloakConfiguration.realm).users().get(userId);
    usersResource.logout();
  }

  @Override
  public Mono<UserLockedResponse> isUserLocked(String userId, TokenResponse token) {
    String url = String.format(
        keycloakConfiguration.serverURL + keycloakConfiguration.userLockedURL + "/" + userId,
        keycloakConfiguration.realm);
    return webClient.get()
        .uri(url)
        .header("Authorization", "Bearer " + token.getAccess_token())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          throw new InvalidRequestException(ErrorMessage.USER_NOT_FOUND_EXCEPTION);
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          throw new InternalServerException(ErrorMessage.INCORRECT_USER);
        }).bodyToMono(UserLockedResponse.class);

  }


}
