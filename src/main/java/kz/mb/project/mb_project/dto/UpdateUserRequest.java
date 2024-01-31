package kz.mb.project.mb_project.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class UpdateUserRequest {

  private UUID id;
  @Pattern(regexp = "(\\+61|0)[0-9]{9}")
  private String phone_number;
  private String firstname;
  private String lastname;
  @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  private String email;
  @Nullable
  private Boolean face_id;
  private Boolean toVerifyEmail;
  private Boolean toVerifyOtp;
  private Boolean toEnable;
  private Boolean toPasswordUpdate;
  private Boolean toTemporal;

}
