package kz.mb.project.mb_project.listener;

import java.util.logging.Logger;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import kz.mb.project.mb_project.entity.Business;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserDetail;
import kz.mb.project.mb_project.entity.UserRole;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.InvalidRequestException;
import kz.mb.project.mb_project.exception.NotFoundException;
import kz.mb.project.mb_project.repo.UserBusinessRepository;
import kz.mb.project.mb_project.repo.UsersRepository;
import kz.mb.project.mb_project.service.PropertyService;

@RepositoryEventHandler(Business.class)
public class BusinessListener {

  Logger logger = Logger.getLogger("Class BusinessListener");

  private final UsersRepository usersRepository;

  private final UserBusinessRepository userBusinessRepository;

  private final PropertyService propertyService;


  private final static String COUNT_OF_BUSINESS = "COUNT_OF_BUSINESS";

  public BusinessListener(UsersRepository usersRepository,
      UserBusinessRepository userBusinessRepository, PropertyService propertyService) {
    this.usersRepository = usersRepository;
    this.userBusinessRepository = userBusinessRepository;
    this.propertyService = propertyService;
  }

  /**
   * todo настроить ошибку сообщения  по количеству бизнеса который юзер может создать !!
   * пример : сейчас Не возможно создать больше  бизнеса
   * должно быть: Не возможно создать больше 3 бизнеса"
   * количество должен взять из БД
   * */

  /**
   * Метод, который ограничивает создание бизнеса
   * если у данного пользователя зарегистрировано больше чем задано бизнеса
   * то метод должен ограничивать создание
   * */
  @HandleBeforeCreate
  public void handleRestrictionOfBusiness(Business business) {
    logger.info("Inside Business Before Create....");
    if (business.getCreatedByUser() != null) {
      UserDetail owner = usersRepository.findUserDetailByUsername(business.getCreatedByUser())
          .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION));
      String countOfBusiness = propertyService.get(COUNT_OF_BUSINESS);
      long countOfBusinessLong = Long.parseLong(countOfBusiness);
      long countBusinessOfCurrentOwner = userBusinessRepository.findAllByUserUsername(
              owner.getUsername()).stream()
          .filter(userBusiness -> userBusiness.getUserRoles().equals(UserRole.Owner)).count();
      if(countBusinessOfCurrentOwner > countOfBusinessLong){
        throw new InvalidRequestException(ErrorMessage.BUSINESS_COUNT_EXCEED_EXCEPTION);
      }
    }
  }

  /**
   * Метод, который создает связь между пользователем и бизнесе с помощью роли Owner автоматический
   * */
  @HandleAfterCreate
  public void handleMemberAfterCreate(Business business) {
    logger.info("Inside Business After Create....");
    if (business.getCreatedByUser() != null) {
      UserBusiness userBusiness = new UserBusiness();
      userBusiness.setBusiness(business);
      userBusiness.setUser(
          usersRepository.findUserDetailByUsername(business.getCreatedByUser())
              .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION)));
      userBusiness.setUserRoles(UserRole.Owner);
      userBusinessRepository.save(userBusiness);
    }
  }
}
