package kz.mb.project.mb_project.scheduler;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import kz.mb.project.mb_project.repo.OtpRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpTask {

  private final OtpRepository otpRepository;

  @Scheduled(cron = "${sms.otpschedule}")
  @Transactional
  public void deleteOtp() {
    log.info("cleaning otp");
    otpRepository.cleanupExpiredOtps(new Date());
  }
}
