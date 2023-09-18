package kz.mb.project.mb_project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsRequest {
  private String from;
  private String to;
  private String text;
}
