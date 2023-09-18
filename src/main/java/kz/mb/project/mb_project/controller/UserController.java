package kz.mb.project.mb_project.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kz.mb.project.mb_project.dto.CreateUserRequest;
import kz.mb.project.mb_project.dto.LoginRequest;
import kz.mb.project.mb_project.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService usersService;


  @RequestMapping(
      value = "/public/create",
      method = RequestMethod.POST
  )
  @ResponseStatus(HttpStatus.CREATED)
  public void create(
      @RequestBody
      CreateUserRequest createUserRequest) {
    usersService.createUser(createUserRequest);
  }

  @RequestMapping(
      value = "/public/reset-password",
      method = RequestMethod.PUT
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void resetPassword(
      @RequestParam
      String username, String password) {
    usersService.updatePassword(username, password);
  }

  @RequestMapping(
      value = "/public/token",
      method = RequestMethod.POST
  )
  public ResponseEntity<?> token(
      @RequestBody
      LoginRequest loginRequest) {
    return ResponseEntity.ok(usersService.signIn(loginRequest));
  }

  @RequestMapping(
      value = "/public/user-info/{username}",
      method = RequestMethod.GET
  )
  public ResponseEntity<?> userInfo(
      @PathVariable
      String username) {
    return ResponseEntity.ok(usersService.userInfo(username));
  }

  @RequestMapping(value = "/public/refresh_token", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> refreshToken(@RequestParam String refresh_token) {
    return ResponseEntity.ok(usersService.refresh(refresh_token));
  }


  @RequestMapping(value = "/public/logout/{user_id}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public void logout(@PathVariable String user_id) {
    usersService.logout(user_id);
  }

  @RequestMapping(value = "/public/send-confirmation-otp/{username}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public void validate(@PathVariable String username) {
    usersService.sendConfirmationOtp(username);
  }
}

