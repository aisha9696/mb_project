package kz.mb.project.mb_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private int statusCode;
  private String message;

  public ErrorResponse(String message)
  {
    super();
    this.message = message;
  }
}
