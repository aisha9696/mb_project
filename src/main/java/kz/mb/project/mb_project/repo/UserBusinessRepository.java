package kz.mb.project.mb_project.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import kz.mb.project.mb_project.entity.Business;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserBusinessProjection;
import kz.mb.project.mb_project.entity.UserRole;

@RepositoryRestResource(excerptProjection = UserBusinessProjection.class)
public interface UserBusinessRepository extends JpaRepository<UserBusiness, UUID> {

  @RestResource(exported = false)
  List<UserBusiness> findAllByUserUsername(String username);

  Page<UserBusiness> findAllByBusinessId(UUID bussinessID, Pageable pageable);

  @RestResource(exported = false)
  Optional<List<UserBusiness>> findByBusinessAndUserRoles(Business business, UserRole role);
}