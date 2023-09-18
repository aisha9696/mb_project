package kz.mb.project.mb_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends RuntimeException {

  public NotAuthorizedException(String message) {
    super(message);
  }
}
