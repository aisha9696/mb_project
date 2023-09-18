package kz.mb.project.mb_project.service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kz.mb.project.mb_project.dto.OtpDto;
import kz.mb.project.mb_project.entity.Otp;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.InvalidRequestException;
import kz.mb.project.mb_project.repo.OtpRepository;
import kz.mb.project.mb_project.utils.HashUtils;
import org.apache.commons.lang3.time.DateUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService{
  protected final OtpRepository otpRepository;
  @Override
  public OtpDto generateOtp(String phone, long otpResendIntervalSecs, String messageText) {
    Otp currentOtp = otpRepository.findOtpByPhoneNumber(phone);
    if (currentOtp != null) {
      long otpExpiredIn = getOtpExpiredIn(currentOtp, new Date());
      if (otpExpiredIn > 0) {
        throw new InvalidRequestException(ErrorMessage.OTP_EXPIRED.getMessageRU());
      }
      otpRepository.delete(currentOtp);
    }
    String confirmCode = random();
    log.info("код проверки " + confirmCode);
    Otp otp = new Otp();
    otp.setPhoneNumber(phone);
    otp.setOtpHash(HashUtils.hash(confirmCode));
    otp.setAttemptsAvailable(3);
    otp.setDeletionDate(DateUtils.addSeconds(new Date(), (int) otpResendIntervalSecs));
    otp.setMessageText(
        !StringUtils.isEmpty(messageText) ? String.format(messageText, confirmCode) : ""
    );
    otpRepository.save(otp);
    log.info("otp был сохранен!");
    return new OtpDto(confirmCode, otpResendIntervalSecs);
  }

  @Override
  public void checkAndDeleteOtp(String clientId, String clientAction, String otp) {
    Otp savedOtp = checkOtp(clientId, clientAction, otp);
    deleteOtp(savedOtp);
  }

  @Override
  public Otp checkOtp(String phone, String clientAction, String otp) {
    Otp savedOtp = otpRepository.findOtpByPhoneNumber(phone);
    if (savedOtp == null) {
      throw new InvalidRequestException(ErrorMessage.OTP_EXPIRED.getMessageRU());
    }
    long otpExpiredIn = getOtpExpiredIn(savedOtp, new Date());
    if (otpExpiredIn <= 0) {
      throw new InvalidRequestException(ErrorMessage.OTP_EXPIRED.getMessageRU());
    }
    if (savedOtp.getAttemptsAvailable() < 1) {
      throw new InvalidRequestException(ErrorMessage.OTP_COUNT_EXPIRED.getMessageRU());
    }
    if (!savedOtp.getOtpHash().equals(HashUtils.hash(otp))) {
      savedOtp.setAttemptsAvailable(savedOtp.getAttemptsAvailable()+1);
      otpRepository.save(savedOtp);
      throw new InvalidRequestException(ErrorMessage.INVALID_OTP.getMessageRU());
    }
    log.info("otp успешно был проверен!");
    return savedOtp;
  }

  @Override
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
