package kz.mb.project.mb_project.service;

import static kz.mb.project.mb_project.mapper.UserMapper.toCreateUserRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kz.mb.project.mb_project.dto.auth.request.CreateEmployeeRequest;
import kz.mb.project.mb_project.entity.UserBusiness;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.FoundException;
import kz.mb.project.mb_project.exception.InvalidRequestException;
import kz.mb.project.mb_project.exception.NotFoundException;
import kz.mb.project.mb_project.repository.BusinessRepository;
import kz.mb.project.mb_project.repository.UserBusinessRepository;
import kz.mb.project.mb_project.service.auth.UserService;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final UserService userService;
  private final UserBusinessRepository userBusinessRepository;
  private final BusinessRepository businessRepository;

  public UserBusiness create(CreateEmployeeRequest createEmployeeRequest) {
    var userRequest = toCreateUserRequest(createEmployeeRequest);
    var created = userService.create(userRequest);
    if (created.membership().stream()
        .anyMatch(empl -> empl.getBusiness().getId().equals(createEmployeeRequest.businessId())
            && createEmployeeRequest.userRoles().compareTo(empl.getUserRoles()) == 0)) {
      throw new InvalidRequestException(ErrorMessage.MEMBER_FOUND_EXCEPTION);
    }
    var savedUser = userService.getUserDetail(createEmployeeRequest.phoneNumber());
    if(savedUser.isPresent()) {throw new FoundException(ErrorMessage.USER_FOUND_EXCEPTION);}
    var business = businessRepository.findById(createEmployeeRequest.businessId())
        .orElseThrow(() -> new NotFoundException(ErrorMessage.BUSINESS_NOT_FOUND_EXCEPTION));
    UserBusiness userBusiness = UserBusiness.builder().user(savedUser.get()).business(business)
        .userRoles(createEmployeeRequest.userRoles()).build();
    return userBusinessRepository.save(userBusiness);
  }
}
