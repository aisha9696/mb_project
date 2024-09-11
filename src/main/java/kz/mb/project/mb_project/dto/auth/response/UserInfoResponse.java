package kz.mb.project.mb_project.dto.auth.response;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

import kz.mb.project.mb_project.entity.UserBusiness;

@Builder
public record UserInfoResponse(
    UUID id,
    String username,
    String firstName,
    String lastName,
    String email,
    Boolean faceId,
    List<UserBusiness> membership,
    Boolean passwordUpdated,
    Boolean enabled


) {}