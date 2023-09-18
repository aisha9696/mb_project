package kz.mb.project.mb_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthorizationException extends RuntimeException {

  public AuthorizationException(String message) {
    super(message);
  }
}
