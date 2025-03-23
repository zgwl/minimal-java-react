package com.example.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth-check")
public class AuthCheckController {

  @GetMapping
  public ResponseEntity<Void> checkAuth() {
    // If we get here, the user is authenticated (JWT filter passed)
    return ResponseEntity.ok().build();
  }
}