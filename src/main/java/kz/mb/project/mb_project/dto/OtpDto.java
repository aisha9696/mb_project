package kz.mb.project.mb_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс представление для отправки смс
 */
@Getter
@Setter
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
