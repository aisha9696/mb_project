package kz.mb.project.mb_project.config;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kz.mb.project.mb_project.listener.BusinessListener;
import kz.mb.project.mb_project.listener.MemberListener;
import kz.mb.project.mb_project.repository.UserBusinessRepository;
import kz.mb.project.mb_project.repository.UsersRepository;
import kz.mb.project.mb_project.service.NotificationService;
import kz.mb.project.mb_project.service.PropertyService;
import kz.mb.project.mb_project.service.auth.UserService;

@Configuration
@AllArgsConstructor
public class RepositoryConfiguration {

  private final UsersRepository usersRepository;
  private final UserBusinessRepository repository;
  private final UserService userService;
  private final PropertyService propertyService;
  private final NotificationService notificationService;


  @Bean
  BusinessListener businessListener() {
    return new BusinessListener(usersRepository, repository, propertyService
    );
  }

  @Bean
  MemberListener memberListener() {
    return new MemberListener(notificationService, repository, userService, propertyService);
  }
}
