package kz.mb.project.mb_project.dto;

import lombok.Getter;

@Getter
public enum SuccessMessage {
  OTP_CHECKED("Otp успешно был проверен!","Otp жарамды!");

  private final String messageRU;
  private final String messageKZ;

  SuccessMessage(String messageRU, String messageKZ) {
    this.messageRU = messageRU;
    this.messageKZ = messageKZ;
  }
}
