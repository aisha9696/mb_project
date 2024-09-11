package kz.mb.project.mb_project.dto.auth.request;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import kz.mb.project.mb_project.entity.Business;
import kz.mb.project.mb_project.entity.UserRole;

public record CreateEmployeeRequest(
    @Pattern(regexp = "^7(70[0-9]|71[0-9]|727|74[7]|77[57])\\d{7}$") String phoneNumber,
    String firstname,
    String lastname,
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") String email,
    UUID businessId,
    UserRole userRoles
) {

}
