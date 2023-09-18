package kz.mb.project.mb_project.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.mb.project.mb_project.entity.UserDetail;

@Repository
public interface UsersRepository extends JpaRepository<UserDetail, UUID> {

  Optional<UserDetail> findUserDetailByUsername(String username);
}
