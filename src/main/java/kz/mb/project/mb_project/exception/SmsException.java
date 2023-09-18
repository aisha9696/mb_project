package kz.mb.project.mb_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class SmsException extends RuntimeException{

  public SmsException(String message) {
    super(message);
  }
}