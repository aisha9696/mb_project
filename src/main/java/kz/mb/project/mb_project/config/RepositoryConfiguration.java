package kz.mb.project.mb_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kz.mb.project.mb_project.listener.BusinessListener;
import kz.mb.project.mb_project.listener.MemberListener;
import kz.mb.project.mb_project.repo.UserBusinessRepository;
import kz.mb.project.mb_project.repo.UsersRepository;
import kz.mb.project.mb_project.service.PropertyService;
import kz.mb.project.mb_project.service.SmsService;
import kz.mb.project.mb_project.service.UserService;

@Configuration
public class RepositoryConfiguration {

  private final UsersRepository usersRepository;
  private final UserBusinessRepository repository;
  private final SmsService smsService;
  private final UserService userService;
  private final PropertyService propertyService;



  public RepositoryConfiguration(UsersRepository usersRepository, UserBusinessRepository repository,
      SmsService smsService,
      UserService userService,
      PropertyService propertyService) {
    super();
    this.usersRepository = usersRepository;
    this.repository = repository;
    this.smsService = smsService;
    this.userService = userService;
    this.propertyService = propertyService;
  }

  @Bean
  BusinessListener businessListener() {
    return new BusinessListener(usersRepository, repository, propertyService
    );
  }

  @Bean
  MemberListener memberListener() {
    return new MemberListener(smsService, repository, userService, propertyService);
  }
}
