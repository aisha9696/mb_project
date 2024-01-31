package kz.mb.project.mb_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T>{
  private T body;

  private String message;

  private int status;

  public ApiResponse(String message, int status) {
    this.message = message;
    this.status = status;
  }

  public String getMessage() {
    return message;
  }
}
