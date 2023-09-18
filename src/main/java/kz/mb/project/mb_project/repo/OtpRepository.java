package kz.mb.project.mb_project.repo;


import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kz.mb.project.mb_project.entity.Otp;

@Repository
public interface OtpRepository extends CrudRepository<Otp, Long> {
  Otp findOtpByPhoneNumber(String phone);
  @Modifying
  @Query("DELETE FROM Otp o WHERE o.deletionDate < :dt")
  int cleanupExpiredOtps(@Param("dt") Date dt);
}
