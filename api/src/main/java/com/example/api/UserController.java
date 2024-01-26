package com.example.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/users")
  public ResponseEntity<?> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    Optional<User> optionUser = userService.getUserById(id);

    if (optionUser.isPresent()) {
      return ResponseEntity.ok(optionUser.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/users")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    User createdUser = userService.addUser(user);
    return ResponseEntity.ok(createdUser);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
    Optional<User> user = userService.updateUser(id, updatedUser);
    return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok().build();
  }
}
