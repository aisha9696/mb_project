package kz.mb.project.mb_project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SmsRequest {
  private String from;
  private String to;
  private String text;
}
