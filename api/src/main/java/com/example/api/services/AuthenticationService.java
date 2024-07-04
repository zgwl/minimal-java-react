package com.example.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.daos.LoginUserDao;
import com.example.api.daos.RegisterUserDao;
import com.example.api.daos.User;

@Service
public class AuthenticationService {

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserService userService,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public User signup(RegisterUserDao input) {
    User user = new User();
    user.setEmail(input.getEmail());
    user.setPassword(passwordEncoder.encode(input.getPassword()));
    user.setName(input.getName());

    return userService.addUser(user);
  }

  public User authenticate(LoginUserDao input) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        input.getEmail(), input.getPassword());
    authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    return userService.getUserByEmail(input.getEmail()).orElseThrow();
  }
}