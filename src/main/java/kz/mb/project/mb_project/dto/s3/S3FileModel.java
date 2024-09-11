package kz.mb.project.mb_project.dto.s3;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class S3FileModel {

  @JsonProperty("id")
  private String id;

  @JsonProperty("storePath")
  private String storePath;

  @JsonProperty("fileName")
  private String fileName;

  @JsonProperty("file")
  private String fileB64;

  @JsonProperty("message")
  private String message;

}