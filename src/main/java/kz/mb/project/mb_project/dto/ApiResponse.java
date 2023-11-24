package kz.mb.project.mb_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
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
