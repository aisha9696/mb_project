package kz.mb.project.mb_project.dto.auth.request;

public record LoginRequest(
    String username,
    String password
) { }
