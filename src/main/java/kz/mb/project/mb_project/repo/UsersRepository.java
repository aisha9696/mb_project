package kz.mb.project.mb_project.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import kz.mb.project.mb_project.entity.UserDetail;

@RepositoryRestResource(exported = false)
public interface UsersRepository extends JpaRepository<UserDetail, UUID> {

  Optional<UserDetail> findUserDetailByUsername(String username);

  List<UserDetail> findAllByTemporalIsTrue();
}
