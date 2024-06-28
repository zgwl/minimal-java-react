package com.example.api.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.daos.RegisterUserDao;
import com.example.api.daos.User;

@Service
public class AuthenticationService {

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  public User signup(RegisterUserDao input) {
    User user = new User();
    user.setEmail(input.getEmail());
    user.setPassword(passwordEncoder.encode(input.getPassword()));
    user.setName(input.getName());

    return userService.addUser(user);
  }
}