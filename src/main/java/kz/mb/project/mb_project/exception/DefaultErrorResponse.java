package kz.mb.project.mb_project.exception;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record DefaultErrorResponse(
    String id,
    LocalDateTime dateTime,
    String message

) {

}
