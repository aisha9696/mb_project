package kz.mb.project.mb_project.dto.auth.response;


public record OtpResponse(
    Boolean validated,
    String hash
) {

}
