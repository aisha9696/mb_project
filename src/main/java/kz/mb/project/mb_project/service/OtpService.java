package kz.mb.project.mb_project.service;

import kz.mb.project.mb_project.dto.OtpDto;
import kz.mb.project.mb_project.entity.Otp;

public interface OtpService {
  OtpDto generateOtp(
      String phone,
      long otpResendIntervalSecs,
      String messageText
  );

  void checkAndDeleteOtp(String clientId, String otp);

  Otp checkOtp(String clientId, String otp);


  void deleteOtp(Otp otp);
}
