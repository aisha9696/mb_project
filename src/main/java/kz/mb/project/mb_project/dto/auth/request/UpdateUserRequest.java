package kz.mb.project.mb_project.dto.auth.request;

import java.util.UUID;

import lombok.Builder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Builder
public record UpdateUserRequest(
    UUID id,
    @Pattern(regexp = "(\\+61|0)[0-9]{9}") String phoneNumber,
    String firstname,
    String lastname,
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") String email,
    Boolean faceId,
    Boolean toVerifyEmail,
    Boolean toVerifyOtp,
    Boolean toEnable,
    Boolean toPasswordUpdate,
    Boolean toTemporal
) {
  }