package com.example.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.daos.User;
import com.example.api.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public ResponseEntity<?> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    Optional<User> optionUser = userService.getUserById(id);

    if (optionUser.isPresent()) {
      logger.info("User found: {}", optionUser.get());
      return ResponseEntity.ok(optionUser.get());
    } else {
      logger.warn("User with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/users")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    User createdUser = userService.addUser(user);
    logger.info("User created: {}", createdUser);
    return ResponseEntity.ok(createdUser);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
    Optional<User> user = userService.updateUser(id, updatedUser);
    return user.map(u -> {
      logger.info("User updated: {}", u);
      return ResponseEntity.ok(u);
    }).orElseGet(() -> {
      logger.warn("User with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    });
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    logger.info("User with ID: {} deleted", id);
    return ResponseEntity.ok().build();
  }
}
