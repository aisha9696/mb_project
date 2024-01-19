package kz.mb.project.mb_project.dto.keycloak;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Builder
public class KAttributes implements Serializable {

  private String[] face_id;

  @JsonCreator
  public KAttributes(
      @JsonProperty("face_id")
      String[] face_id) {
    this.face_id = face_id;
  }
}
