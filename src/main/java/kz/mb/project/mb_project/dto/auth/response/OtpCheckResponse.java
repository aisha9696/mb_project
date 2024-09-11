package kz.mb.project.mb_project.dto.auth.response;

public record OtpCheckResponse(
    Boolean checked,
    String hash
) {

}
