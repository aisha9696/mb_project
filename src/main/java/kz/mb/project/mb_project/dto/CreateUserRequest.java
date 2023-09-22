package kz.mb.project.mb_project.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Data
public class CreateUserRequest {
  @Pattern(regexp = "(\\+61|0)[0-9]{9}")
  String phone_number;
  String password;
  String firstname;
  String lastname;
  @Email
  String email;
  Boolean face_id;
}
