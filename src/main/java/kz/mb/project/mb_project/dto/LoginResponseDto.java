package kz.mb.project.mb_project.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import kz.mb.project.mb_project.entity.File;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserDetail;

@Getter
@Setter
public class LoginResponseDto {

  private UUID id;

  private String username;

  private File photo;

  private String firstName;

  private String lastName;

  private String email;

  private Boolean face_id;

  private List<UserBusiness> membership;

  private Boolean password_updated;

  private Boolean enabled;

  private UserDetail detail;

}
