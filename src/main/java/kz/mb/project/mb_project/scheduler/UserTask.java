package kz.mb.project.mb_project.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kz.mb.project.mb_project.entity.UserRole;
import kz.mb.project.mb_project.service.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserTask {
  private final UserService service;

  @Scheduled(cron = "${user.temporal.owner}")
  @Transactional
  public void deleteTemporalOwner() {
    log.info("cleaning temporal owners");
    service.deleteTemporalUser(UserRole.Owner);
  }

  @Scheduled(cron = "${user.temporal.other}")
  @Transactional
  public void deleteTemporalUsers() {
    log.info("cleaning temporal users");
    service.deleteTemporalUser(UserRole.Cacher);
    service.deleteTemporalUser(UserRole.Stockman);
  }
}
