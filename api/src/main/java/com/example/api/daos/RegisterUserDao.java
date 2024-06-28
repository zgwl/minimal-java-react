package com.example.api.daos;

import lombok.Data;

@Data
public class RegisterUserDao {
  private String email;
  private String password;
  private String name;
}
