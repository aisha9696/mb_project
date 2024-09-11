package kz.mb.project.mb_project.dto.auth.keycloak;

import lombok.Builder;

@Builder
public record CreateKUser(
    String username,
    Boolean enabled,
    Boolean totp,
    Boolean emailVerified,
    String firstName,
    String lastName,
    String email,
    KAction[] requiredActions
) {
  // Constructors, accessors (getters), equals, and hashCode methods are automatically generated
}
