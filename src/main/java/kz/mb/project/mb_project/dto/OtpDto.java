package kz.mb.project.mb_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс представление для отправки смс
 */
@Data
@AllArgsConstructor
public class OtpDto {

  /**
   * Текст смс
   */
  private String otp;
  /**
   * Миллисекунда для обратной отправки
   */
  private Long resendIntervalSecs;
}
