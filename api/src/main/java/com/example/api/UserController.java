package com.example.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        logger.info("*****************************");
        logger.info("Received request to get all users");
        logger.info("*****************************");

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        logger.info("*****************************");
        logger.info("Received request to get user with ID: {}", id);
        logger.info("*****************************");

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
        logger.info("*****************************");
        logger.info("Received request to create user: {}", user);
        logger.info("*****************************");

        User createdUser = userService.addUser(user);
        logger.info("User created: {}", createdUser);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        logger.info("*****************************");
        logger.info("Received request to update user with ID: {}", id);
        logger.info("*****************************");

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
        logger.info("*****************************");
        logger.info("Received request to delete user with ID: {}", id);
        logger.info("*****************************");
        
        userService.deleteUser(id);
        logger.info("User with ID: {} deleted", id);
        return ResponseEntity.ok().build();
    }
}
