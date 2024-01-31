package kz.mb.project.mb_project.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kz.mb.project.mb_project.entity.UserDetail;

@RepositoryRestResource
public interface UsersRepository extends JpaRepository<UserDetail, UUID> {

  Optional<UserDetail> findUserDetailByUsername(String username);

  Optional<UserDetail> findUserDetailByEmail(String email);

  List<UserDetail> findAllByTemporalIs(Boolean bool);


}
