package kz.mb.project.mb_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends LanguageException{

  public NotFoundException(ErrorMessage message) {
    super(message);
  }
}
