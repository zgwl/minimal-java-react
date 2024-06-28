package com.example.api.daos;

import lombok.Data;

@Data
public class RegisterUserDto {
  private String email;
  private String password;
  private String name;
}
