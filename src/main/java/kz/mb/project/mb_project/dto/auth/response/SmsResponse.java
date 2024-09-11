package kz.mb.project.mb_project.dto.auth.response;


import java.io.Serializable;


public record SmsResponse(
    String bulkId,
    String messageId,
    String extraId,
    String to,
    String sender,
    String text,
    String sentAt,
    String doneAt,
    Integer smsCount,
    Integer priority,
    String callbackData,
    String status,
    Integer mnc,
    String err
) implements Serializable {}