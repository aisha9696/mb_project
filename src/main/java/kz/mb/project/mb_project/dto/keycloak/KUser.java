package kz.mb.project.mb_project.dto.keycloak;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Builder
public class KUser implements Serializable {
  private String id;
  private Timestamp createdTimestamp;
  private String username;
  private Boolean enabled;
  private Boolean totp;
  private Boolean emailVerified;
  private String firstName;
  private String lastName;
  private String email;
  private KAttributes attributes;
  private String[] disableableCredentialTypes;
  private KAction[] requiredActions;
  private Integer notBefore;
  private KAccess access;

  @JsonCreator
  public KUser(
      @JsonProperty("id") String id,
      @JsonProperty("createdTimestamp") Timestamp createdTimestamp,
      @JsonProperty("username") String username,
      @JsonProperty("enabled") Boolean enabled,
      @JsonProperty("totp") Boolean totp,
      @JsonProperty("emailVerified")  Boolean emailVerified,
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName,
      @JsonProperty("email") String email,
      @JsonProperty("attributes") KAttributes attributes,
      @JsonProperty("disableableCredentialTypes") String[] disableableCredentialTypes,
      @JsonProperty("requiredActions") KAction[] requiredActions,
      @JsonProperty("notBefore") Integer notBefore,
      @JsonProperty("access") KAccess access) {
    this.id = id;
    this.createdTimestamp = createdTimestamp;
    this.username = username;
    this.enabled = enabled;
    this.totp = totp;
    this.emailVerified = emailVerified;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.attributes = attributes;
    this.disableableCredentialTypes = disableableCredentialTypes;
    this.requiredActions = requiredActions;
    this.notBefore = notBefore;
    this.access = access;
  }
}
/**
 * {
 *     "id": "abf80cf5-c9d6-4eaa-92d8-2321bd7f8602",
 *     "createdTimestamp": 1694605071385,
 *     "username": "77002445566",
 *     "enabled": true,
 *     "totp": false,
 *     "emailVerified": false,
 *     "firstName": "string",
 *     "lastName": "string",
 *     "email": "string@gmail.com",
 *     "attributes": {
 *         "face_id": [
 *             "false"
 *         ]
 *     },
 *     "disableableCredentialTypes": [],
 *     "requiredActions": [
 *         "CONFIGURE_TOTP",
 *         "UPDATE_PASSWORD"
 *     ],
 *     "notBefore": 0,
 *     "access": {
 *         "manageGroupMembership": true,
 *         "view": true,
 *         "mapRoles": true,
 *         "impersonate": true,
 *         "manage": true
 *     }
 * }
 *
 * */