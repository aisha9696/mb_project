package kz.mb.project.mb_project.dto.auth.response;

public record UserLockedResponse(
    Integer numFailures,
    Boolean disabled,
    String lastIPFailure,
    Long lastFailure
) {}