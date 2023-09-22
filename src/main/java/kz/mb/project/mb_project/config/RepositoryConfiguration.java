package kz.mb.project.mb_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kz.mb.project.mb_project.listener.BusinessListener;
import kz.mb.project.mb_project.listener.MemberListener;
import kz.mb.project.mb_project.repo.UserBusinessRepository;
import kz.mb.project.mb_project.service.SmsService;
import kz.mb.project.mb_project.service.UserService;

@Configuration
public class RepositoryConfiguration {

  private final UserBusinessRepository repository;
  private final SmsService smsService;
  private final UserService userService;

  public RepositoryConfiguration(UserBusinessRepository repository, SmsService smsService,
      UserService userService) {
    super();
    this.repository = repository;
    this.smsService = smsService;
    this.userService = userService;
  }

  @Bean
  BusinessListener businessListener() {
    return new BusinessListener(repository);
  }

  @Bean
  MemberListener memberListener() {
    return new MemberListener(smsService,repository, userService);
  }
}
