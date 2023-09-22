package kz.mb.project.mb_project.dto.keycloak;

import java.io.Serializable;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class KAccess implements Serializable {
  private boolean manageGroupMembership;
  private boolean view;
  private boolean mapRoles;
  private boolean impersonate;
  private boolean manage;
  @JsonCreator
  public KAccess(@JsonProperty("manageGroupMembership") boolean manageGroupMembership,
      @JsonProperty("view") boolean view,
      @JsonProperty("mapRoles") boolean mapRoles,
      @JsonProperty("impersonate") boolean impersonate,
      @JsonProperty("manage") boolean manage) {
    this.manageGroupMembership = manageGroupMembership;
    this.view = view;
    this.mapRoles = mapRoles;
    this.impersonate = impersonate;
    this.manage = manage;
  }
}
