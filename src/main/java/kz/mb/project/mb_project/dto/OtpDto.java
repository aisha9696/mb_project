package kz.mb.project.mb_project.dto;

/**
 * Класс представление для отправки смс
 */
public record OtpDto(
    /**
     * Текст смс
     */
    String otp,
    /**
     * Миллисекунда для обратной отправки
     */
    Long resendIntervalSecs
) {}
