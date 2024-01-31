package kz.mb.project.mb_project.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ProjectException extends LanguageException {


  public ProjectException(ErrorMessage message) {
    super(message);
  }
}

