package kz.mb.project.mb_project.dto.s3;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class S3ObjectModel {

  @JsonProperty("id")
  private String id;

  @JsonProperty("object_key")
  private String objectKey;

  @JsonProperty("Message")
  private String message;

  @JsonProperty("Result")
  private String result;

}