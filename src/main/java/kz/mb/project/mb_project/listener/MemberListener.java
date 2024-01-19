package kz.mb.project.mb_project.listener;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.transaction.annotation.Transactional;

import kz.mb.project.mb_project.dto.SmsResponse;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserRole;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.InternalServerException;
import kz.mb.project.mb_project.exception.InvalidRequestException;
import kz.mb.project.mb_project.exception.NotFoundException;
import kz.mb.project.mb_project.repo.UserBusinessRepository;
import kz.mb.project.mb_project.service.PropertyService;
import kz.mb.project.mb_project.service.SmsService;
import kz.mb.project.mb_project.service.UserService;
import kz.mb.project.mb_project.utils.RandomUtils;

@RepositoryEventHandler(UserBusiness.class)
@Slf4j
public class MemberListener {

  private final SmsService service;
  private final UserBusinessRepository userBusinessRepository;
  private final UserService userService;

  private final PropertyService propertyService;

  private static final String COUNT_OF_EMPLOYEE = "COUNT_OF_EMPLOYEE";

  public MemberListener(SmsService service, UserBusinessRepository userBusinessRepository,
      UserService userService, PropertyService propertyService) {
    this.service = service;
    this.userBusinessRepository = userBusinessRepository;
    this.userService = userService;
    this.propertyService = propertyService;
  }

  /**
   * Данный метод ограничивает создание пользователей в бизнесе
   * */

  @HandleBeforeCreate
  public void handleMemberRestrictionBeforeCreate(UserBusiness member) {
    long count_of_employee = Long.parseLong(propertyService.get(COUNT_OF_EMPLOYEE));
    long countOfCurrentEmployee = userBusinessRepository.findAllByBusiness(
            member.getBusiness()).stream()
        .filter(userBusiness -> (userBusiness.getUserRoles().equals(UserRole.Cacher)
            || userBusiness.getUserRoles().equals(UserRole.Stockman))).count();

    if(countOfCurrentEmployee > count_of_employee){
      throw new InvalidRequestException(ErrorMessage.EMPLOYEE_COUNT_EXCEED_EXCEPTION);
    }
  }

  /**
   * Данный метод отправляет генерированный пароль для employee owner-у бизнеса
   */

  @HandleAfterCreate
  @Transactional
  public void handleMemberAfterCreate(UserBusiness member) {
    log.info("UserBusiness logger is listened ");
    String password = RandomUtils.generateRandomString();
    if (member.getUserRoles() == UserRole.Cacher || member.getUserRoles() == UserRole.Stockman) {
      List<UserBusiness> owners = userBusinessRepository.findByBusinessAndUserRoles(
              member.getBusiness(), UserRole.Owner)
          .orElseThrow(() -> new NotFoundException(ErrorMessage.BUSINESS_NOT_FOUND_EXCEPTION));
      String messageText = String.format("MB_App:%s-пароль для доступа %s",
          password, member.getUser().getUsername());
      if (owners != null) {
        SmsResponse response = service.sendSMS(owners.get(0).getUser().getUsername(), messageText)
            .block();
        if (response == null) {
          throw new InternalServerException(ErrorMessage.SMS_SENDING_ERROR);
        }
        log.info("СМС с проверечным кодом был отправлен.Текст SMS : " + messageText);
      }
      userService.setPassword(member.getUser().getUsername(), password);

    }

  }
}
