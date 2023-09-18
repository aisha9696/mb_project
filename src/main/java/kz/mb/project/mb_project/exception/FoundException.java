package kz.mb.project.mb_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FoundException extends RuntimeException{

  public FoundException(String message) {
    super(message);
  }
}
