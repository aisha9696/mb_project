package kz.mb.project.mb_project.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kz.mb.project.mb_project.dto.auth.request.CreateEmployeeRequest;
import kz.mb.project.mb_project.dto.auth.response.UserResponse;
import kz.mb.project.mb_project.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;
  @RequestMapping(
      value = "/create",
      method = RequestMethod.POST
  )
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserResponse> create(
      @RequestBody
      CreateEmployeeRequest createEmployeeRequest) {
    employeeService.create(createEmployeeRequest);
    return ResponseEntity.ok(null);
  }
}
