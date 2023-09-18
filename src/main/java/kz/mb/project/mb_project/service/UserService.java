package kz.mb.project.mb_project.service;

import kz.mb.project.mb_project.dto.CreateUserRequest;
import kz.mb.project.mb_project.dto.LoginRequest;
import kz.mb.project.mb_project.dto.LoginResponseDto;
import kz.mb.project.mb_project.dto.TokenResponse;

public interface UserService {

  void createUser(CreateUserRequest createUserRequest);

  TokenResponse signIn(LoginRequest loginRequest);

  void updatePassword(String username, String password);


  LoginResponseDto userInfo(String username);

  TokenResponse refresh(String refreshToken);

  void logout(String userId);

  void sendConfirmationOtp(String username);
}
