package kz.mb.project.mb_project.dto.auth.request;

import lombok.Builder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Builder
public record CreateUserRequest(
    @Pattern(regexp = "^7(70[0-9]|71[0-9]|727|74[7]|77[57])\\d{7}$") String phoneNumber,
    String firstname,
    String lastname,
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") String email
) {

}
