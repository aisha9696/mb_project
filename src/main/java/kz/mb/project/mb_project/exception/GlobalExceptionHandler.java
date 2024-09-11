package kz.mb.project.mb_project.exception;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {


  @ExceptionHandler({AuthorizationException.class})
  protected ResponseEntity<DefaultErrorResponse> handleAuthorization(
      AuthorizationException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler({ForbiddenException.class})
  protected ResponseEntity<DefaultErrorResponse> handleForbidden(
      ForbiddenException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);

  }

  @ExceptionHandler({FoundException.class})
  protected ResponseEntity<DefaultErrorResponse> handleFound(
      FoundException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler({InternalServerException.class})
  protected ResponseEntity<DefaultErrorResponse> handleISE(
      InternalServerException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.internalServerError().body(errorResponse);
  }

  @ExceptionHandler({InvalidRequestException.class})
  protected ResponseEntity<DefaultErrorResponse> handleInvalidRequest(
      InvalidRequestException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler({LanguageException.class})
  protected ResponseEntity<DefaultErrorResponse> handleLanguage(
      LanguageException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler({NotAuthorizedException.class})
  protected ResponseEntity<DefaultErrorResponse> handleUnauthorized(
      NotAuthorizedException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler({ProjectException.class})
  protected ResponseEntity<DefaultErrorResponse> handleProjectException(
      ProjectException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler({SmsException.class})
  protected ResponseEntity<DefaultErrorResponse> handleSmsException(
      SmsException exception) {
    var errorResponse = DefaultErrorResponse.builder()
        .message(exception.getMessage())
        .dateTime(LocalDateTime.now()).build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
