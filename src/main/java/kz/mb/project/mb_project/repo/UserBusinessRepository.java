package kz.mb.project.mb_project.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import kz.mb.project.mb_project.entity.Business;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserDetail;
import kz.mb.project.mb_project.entity.UserRole;

@RepositoryRestResource
public interface UserBusinessRepository extends JpaRepository<UserBusiness, UUID> {

  @RestResource(exported = false)
  List<UserBusiness> findAllByUserUsername(String username);

  @RestResource(exported = false)
  List<UserBusiness> findByBusinessAndUserRoles(Business business, UserRole role);
}