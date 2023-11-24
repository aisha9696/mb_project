package kz.mb.project.mb_project.service;

import java.util.UUID;

import kz.mb.project.mb_project.dto.CreateUserRequest;
import kz.mb.project.mb_project.dto.LoginRequest;
import kz.mb.project.mb_project.dto.LoginResponseDto;
import kz.mb.project.mb_project.dto.TokenResponse;
import kz.mb.project.mb_project.entity.UserRole;

public interface UserService {

  void createUser(CreateUserRequest createUserRequest);

  TokenResponse signIn(LoginRequest loginRequest);

  void setPassword(String username, String password);

  LoginResponseDto userInfo(String username);

  TokenResponse refresh(String refreshToken);

  void logout(String userId);

  void sendConfirmationOtp(String username);

  Boolean checkOtp(String otp, String username);

  void deleteTemporalUser(UserRole role);


  void resetPassword(String username, String oldPassword, String password);
}
