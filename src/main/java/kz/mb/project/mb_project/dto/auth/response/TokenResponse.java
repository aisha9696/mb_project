package kz.mb.project.mb_project.dto.auth.response;

public record TokenResponse(
    String access_token,
    Integer expires_in,
    String refresh_token,
    Integer refresh_expires_in,
    String token_type,
    String id_token,
    String session_state,
    String scope
) {}