package kz.mb.project.mb_project.entity;

import java.util.UUID;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "fullUserBusiness", types = { UserBusiness.class })
public interface UserBusinessProjection {
  UUID getId();
  UserDetail getUser();
  Business getBusiness();
  UserRole getUserRoles();
}