package kz.mb.project.mb_project.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class CreateUserRequest {
  @Pattern(regexp = "(\\+61|0)[0-9]{9}")
  String phone_number;
  String firstname;
  String lastname;
  @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  String email;
  @Nullable
  Boolean face_id;

}
