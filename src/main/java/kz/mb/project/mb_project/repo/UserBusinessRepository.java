package kz.mb.project.mb_project.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import kz.mb.project.mb_project.entity.UserBusiness;

@RepositoryRestResource
public interface UserBusinessRepository extends JpaRepository<UserBusiness, UUID> {
  @RestResource(exported = false)
  List<UserBusiness> findAllByUserUsername(String username);
}