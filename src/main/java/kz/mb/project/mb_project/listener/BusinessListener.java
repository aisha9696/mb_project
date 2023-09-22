package kz.mb.project.mb_project.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import kz.mb.project.mb_project.entity.Business;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.entity.UserRole;
import kz.mb.project.mb_project.repo.UserBusinessRepository;

@RepositoryEventHandler(Business.class)
public class BusinessListener {
  Logger logger = Logger.getLogger("Class BusinessListener");


  private final UserBusinessRepository userBusinessRepository;

  public BusinessListener(UserBusinessRepository userBusinessRepository) {
    this.userBusinessRepository = userBusinessRepository;
  }

  @HandleAfterCreate
  public void handleMemberAfterCreate(Business business){
    logger.info("Inside Business After Create....");
    if(business.getCreatedBy() != null){
      UserBusiness userBusiness = new UserBusiness();
      userBusiness.setBusiness(business);
      userBusiness.setUser(business.getCreatedBy());
      userBusiness.setUserRoles(UserRole.Owner);
      userBusinessRepository.save(userBusiness);
    }
  }
}
