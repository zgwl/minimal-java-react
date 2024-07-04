package com.example.api.daos;

import lombok.Data;

@Data
public class LoginResponse {
  private String token;
  private long expiresIn;
}
