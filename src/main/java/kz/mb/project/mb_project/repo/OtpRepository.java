package kz.mb.project.mb_project.repo;


import java.util.Date;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import kz.mb.project.mb_project.entity.Otp;

@RepositoryRestResource(exported = false)
public interface OtpRepository extends CrudRepository<Otp, UUID> {
  Otp findOtpByPhoneNumber(String phone);
  @Modifying
  @Query("DELETE FROM Otp o WHERE o.deletionDate < :dt")
  void cleanupExpiredOtps(@Param("dt") Date dt);
}
