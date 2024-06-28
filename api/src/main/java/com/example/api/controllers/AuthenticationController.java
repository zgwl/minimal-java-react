package com.example.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.daos.RegisterUserDao;
import com.example.api.daos.User;
import com.example.api.services.AuthenticationService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/signup")
  public ResponseEntity<User> register(@RequestBody RegisterUserDao registerUserDto) {
    logger.info("Signup request received with the following data: {}", registerUserDto);
    User registeredUser = authenticationService.signup(registerUserDto);

    return ResponseEntity.ok(registeredUser);
  }
}
