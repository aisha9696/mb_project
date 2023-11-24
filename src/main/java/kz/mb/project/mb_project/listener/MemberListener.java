package kz.mb.project.mb_project.listener;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import kz.mb.project.mb_project.dto.SmsResponse;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserRole;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.InternalServerException;
import kz.mb.project.mb_project.repo.UserBusinessRepository;
import kz.mb.project.mb_project.service.SmsService;
import kz.mb.project.mb_project.service.UserService;
import kz.mb.project.mb_project.utils.RandomUtils;

@RepositoryEventHandler(UserBusiness.class)
@Slf4j
public class MemberListener {

  private final SmsService service;
  private final UserBusinessRepository userBusinessRepository;
  private final UserService userService;

  public MemberListener(SmsService service, UserBusinessRepository userBusinessRepository,
      UserService userService) {
    this.service = service;
    this.userBusinessRepository = userBusinessRepository;
    this.userService = userService;
  }


  @HandleAfterCreate
  public void handleMemberAfterCreate(UserBusiness member) {
    log.info("UserBusiness logger is listened ");
    String password = RandomUtils.generateRandomString();
    if (member.getUserRoles() == UserRole.Cacher || member.getUserRoles() == UserRole.Stockman) {
      List<UserBusiness> owners = userBusinessRepository.findByBusinessAndUserRoles(
          member.getBusiness(), UserRole.Owner);

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
