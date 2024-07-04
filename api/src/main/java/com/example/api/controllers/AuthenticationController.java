package com.example.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.daos.LoginResponse;
import com.example.api.daos.LoginUserDao;
import com.example.api.daos.RegisterUserDao;
import com.example.api.daos.User;
import com.example.api.services.AuthenticationService;
import com.example.api.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  private final AuthenticationService authenticationService;

  private final JwtService jwtService;

  public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
    this.authenticationService = authenticationService;
    this.jwtService = jwtService;
  }

  @PostMapping("/signup")
  public ResponseEntity<User> register(@RequestBody RegisterUserDao registerUserDto) {
    logger.info("Signup request received with the following data: {}", registerUserDto);
    User registeredUser = authenticationService.signup(registerUserDto);

    return ResponseEntity.ok(registeredUser);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDao loginUserDto) {
    logger.info("Login request received with the following data: {}", loginUserDto);
    User authenticatedUser = authenticationService.authenticate(loginUserDto);
    String jwtToken = jwtService.generateToken(authenticatedUser);

    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setToken(jwtToken);
    loginResponse.setExpiresIn(jwtService.getExpirationTime());

    return ResponseEntity.ok(loginResponse);
  }
}
