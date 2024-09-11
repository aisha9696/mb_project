package kz.mb.project.mb_project.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kz.mb.project.mb_project.dto.auth.request.CreateUserRequest;
import kz.mb.project.mb_project.dto.auth.request.LoginRequest;
import kz.mb.project.mb_project.dto.auth.response.UserInfoResponse;
import kz.mb.project.mb_project.dto.auth.response.OtpCheckResponse;
import kz.mb.project.mb_project.dto.auth.response.TokenResponse;
import kz.mb.project.mb_project.dto.auth.response.UserResponse;
import kz.mb.project.mb_project.entity.UserRole;
import kz.mb.project.mb_project.service.auth.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService usersService;

  // todo

  /**
   * сделать не void UserResponse
   */
  @RequestMapping(
      value = "/public/create",
      method = RequestMethod.POST
  )
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserInfoResponse> create(
      @RequestBody
      CreateUserRequest createUserRequest) {
    var user = usersService.create(createUserRequest);
    return ResponseEntity.ok(user);
  }

  /**
   * todo
   */

  @RequestMapping(
      value = "/public/set-password",
      method = RequestMethod.PUT
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Boolean> setPassword(
      @RequestParam
      String username, String password, String opt_hash) {
    usersService.setPasswordAfterSmsVerification(username, password, opt_hash);
    return ResponseEntity.ok(true);
  }

  @RequestMapping(
      value = "/public/login",
      method = RequestMethod.POST
  )
  public ResponseEntity<TokenResponse> token(
      @RequestBody
      LoginRequest loginRequest) {
    return ResponseEntity.ok(usersService.signIn(loginRequest));
  }

  @RequestMapping(
      value = "/user-info/{username}",
      method = RequestMethod.GET
  )
  public ResponseEntity<UserInfoResponse> userInfo(
      @PathVariable
      String username) {
    return ResponseEntity.ok(usersService.userInfo(username));
  }

  @RequestMapping(
      value = "/public/refresh_token",
      method = RequestMethod.GET
  )
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<TokenResponse> refreshToken(
      @RequestParam
      String refresh_token) {
    return ResponseEntity.ok(usersService.refresh(refresh_token));
  }


  @RequestMapping(
      value = "/public/logout/{user_id}",
      method = RequestMethod.GET
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> logout(
      @PathVariable
      String user_id) {
    usersService.logout(user_id);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(
      value = "/public/send-confirmation-otp/{username}",
      method = RequestMethod.GET
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> validate(
      @PathVariable
      String username) {
    usersService.sendConfirmationOtp(username);
    return ResponseEntity.noContent().build();
  }


  @GetMapping(value = "/public/check-otp")
  public ResponseEntity<OtpCheckResponse> checkRegistrationOtp(
      @RequestParam
      String otp,
      @RequestParam
      String username) {

    return ResponseEntity.ok(usersService.checkOtp(otp, username));
  }

  @DeleteMapping("/public/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deleteUser() {
    usersService.deleteTemporalUser(UserRole.Cacher);
    usersService.deleteTemporalUser(UserRole.Stockman);
    return ResponseEntity.noContent().build();
  }

}

