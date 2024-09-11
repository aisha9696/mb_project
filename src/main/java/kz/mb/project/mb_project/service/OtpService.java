package kz.mb.project.mb_project.service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kz.mb.project.mb_project.dto.OtpDto;
import kz.mb.project.mb_project.entity.Otp;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.InvalidRequestException;
import kz.mb.project.mb_project.repository.OtpRepository;
import kz.mb.project.mb_project.utils.HashUtils;
import org.apache.commons.lang3.time.DateUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

  protected final OtpRepository otpRepository;

  @Value("${sms.opt-schedule}")
  protected String otpResendIntervalSecs;

  public Otp getOpt(String phone) {
    return otpRepository.findOtpByPhoneNumber(phone);
  }

  public Otp setOtp(Otp otp) {
    return otpRepository.save(otp);
  }

  public OtpDto generateOtp(String phone, String messageText) {
    Otp currentOtp = otpRepository.findOtpByPhoneNumber(phone);
    if (currentOtp != null) {
      long otpExpiredIn = getOtpExpiredIn(currentOtp, new Date());
      if (otpExpiredIn > 0) {
        throw new InvalidRequestException(ErrorMessage.OTP_EXPIRED);
      }
      otpRepository.delete(currentOtp);
    }
    String confirmCode = random();
    log.info("код проверки " + confirmCode);
    Otp otp = new Otp();
    otp.setPhoneNumber(phone);
    otp.setOtpHash(HashUtils.hash(confirmCode));
    otp.setAttemptsAvailable(3);
    otp.setDeletionDate(
        DateUtils.addMilliseconds(new Date(), Integer.parseInt(otpResendIntervalSecs)));
    otp.setMessageText(!messageText.isEmpty() ? String.format(messageText, confirmCode) : "");
    otp.setVerified(false);
    otpRepository.save(otp);
    log.info("otp был сохранен!");
    return new OtpDto(confirmCode, Long.parseLong(otpResendIntervalSecs));
  }

  public void checkAndDeleteOtp(String clientId, String otp) {
    Otp savedOtp = checkOtp(clientId, otp);
    deleteOtp(savedOtp);
  }

  public Otp checkOtp(String phone, String otp) {
    Otp savedOtp = otpRepository.findOtpByPhoneNumber(phone);
    if (savedOtp == null) {
      throw new InvalidRequestException(ErrorMessage.OTP_NOT_FOUND);
    }
    long otpExpiredIn = getOtpExpiredIn(savedOtp, new Date());
    if (otpExpiredIn <= 0) {
      throw new InvalidRequestException(ErrorMessage.OTP_EXPIRED);
    }
    if (savedOtp.getAttemptsAvailable() < 1) {
      throw new InvalidRequestException(ErrorMessage.OTP_COUNT_EXPIRED);
    }
    if (!savedOtp.getOtpHash().equals(HashUtils.hash(otp))) {
      savedOtp.setAttemptsAvailable(savedOtp.getAttemptsAvailable() + 1);
      otpRepository.save(savedOtp);
      throw new InvalidRequestException(ErrorMessage.INVALID_OTP);
    }
    log.info("otp успешно был проверен!");
    return savedOtp;
  }

  public void deleteOtp(Otp otp) {
    if (otp != null) {
      otpRepository.delete(otp);
    }
  }

  public static String random() {
    int n = 100000 + ThreadLocalRandom.current().nextInt(900000);
    return String.valueOf(n);
  }

  private static long getOtpExpiredIn(Otp otp, Date now) {
    return otp.getDeletionDate().getTime() - now.getTime();
  }
}
