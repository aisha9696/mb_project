package kz.mb.project.mb_project.listener;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.transaction.annotation.Transactional;

import kz.mb.project.mb_project.dto.UpdateUserRequest;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserRole;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.FoundException;
import kz.mb.project.mb_project.exception.InvalidRequestException;
import kz.mb.project.mb_project.exception.NotFoundException;
import kz.mb.project.mb_project.repo.UserBusinessRepository;
import kz.mb.project.mb_project.service.NotificationService;
import kz.mb.project.mb_project.service.PropertyService;
import kz.mb.project.mb_project.service.UserService;
import kz.mb.project.mb_project.utils.RandomUtils;

@RepositoryEventHandler(UserBusiness.class)
@Slf4j
public class MemberListener {

  private final NotificationService notificationService;
  private final UserBusinessRepository userBusinessRepository;
  private final UserService userService;

  private final PropertyService propertyService;

  private static final String COUNT_OF_EMPLOYEE = "COUNT_OF_EMPLOYEE";

  public MemberListener(NotificationService notificationService,
      UserBusinessRepository userBusinessRepository,
      UserService userService, PropertyService propertyService) {
    this.notificationService = notificationService;
    this.userBusinessRepository = userBusinessRepository;
    this.userService = userService;
    this.propertyService = propertyService;
  }

  /**
   * Данный метод ограничивает создание пользователей в бизнесе
   */

  /**
   * Данный метод отправляет генерированный пароль для employee owner-у бизнеса
   */

  @HandleBeforeCreate
  @Transactional(rollbackFor = {FoundException.class, InvalidRequestException.class,})
  public void handleMemberAfterCreate(UserBusiness member) {
    long count_of_employee = Long.parseLong(propertyService.get(COUNT_OF_EMPLOYEE));
    List<UserBusiness> members = userBusinessRepository.findAllByBusiness(
        member.getBusiness());
    if (members.stream().anyMatch(dbMember -> dbMember.getUser().equals(member.getUser()))) {
      throw new FoundException(ErrorMessage.MEMBER_FOUND_EXCEPTION);
    }
    long countOfCurrentEmployee = members.stream()
        .filter(userBusiness -> (userBusiness.getUserRoles().equals(UserRole.Cacher)
            || userBusiness.getUserRoles().equals(UserRole.Stockman))).count();

    if (countOfCurrentEmployee > count_of_employee) {
      throw new InvalidRequestException(ErrorMessage.EMPLOYEE_COUNT_EXCEED_EXCEPTION);
    }
    log.info("UserBusiness logger is listened ");
    String password = RandomUtils.generateRandomString();
    if (member.getUserRoles() == UserRole.Cacher || member.getUserRoles() == UserRole.Stockman) {
      List<UserBusiness> owners = userBusinessRepository.findByBusinessAndUserRoles(
              member.getBusiness(), UserRole.Owner)
          .orElseThrow(() -> new NotFoundException(ErrorMessage.BUSINESS_NOT_FOUND_EXCEPTION));
      String messageText = String.format("MB_App:%s-пароль для доступа %s",
          password, member.getUser().getUsername());
      if (owners != null) {

        notificationService.performSMSNotification(owners.get(0).getUser().getUsername(), "",
            messageText);
        // notificationService.performEmailNotification(owners.get(0).getUser().getEmail(), "Пароли для пользователя", messageText);
        log.info("СМС с проверечным кодом был отправлен.Текст SMS : " + messageText);
      }
      UpdateUserRequest toUpdate = UpdateUserRequest.builder().id(member.getUser().getId())
          .email(member.getUser().getEmail()).firstname(member.getUser().getFirstName())
          .lastname(member.getUser().getLastName()).toTemporal(member.getUser().getTemporal())
          .toEnable(true).phone_number(member.getUser().getUsername())
          .toVerifyEmail(false).toVerifyOtp(false).toPasswordUpdate(false).build();
      userService.updateUser(toUpdate);
      userService.setPassword(member.getUser().getUsername(), password);

    }

  }
}
