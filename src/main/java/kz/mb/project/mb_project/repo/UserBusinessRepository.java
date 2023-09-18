package kz.mb.project.mb_project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kz.mb.project.mb_project.entity.UserBusiness;

@RepositoryRestResource
public interface UserBusinessRepository extends JpaRepository<UserBusiness, Long> {
  List<UserBusiness> findAllByUserUsername(String username);
}