package com.example.api.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.api.mappers.UserMapper;

@Component
public class CurrentUserUtil {

  UserMapper userMapper;

  public CurrentUserUtil(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public String getCurrentUserEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      return userDetails.getUsername();
    }
    return null;
  }

  public Long getCurrentUserId() {
    String email = getCurrentUserEmail();
    if (email == null) {
      return null;
    }

    return userMapper.findByEmail(email).getId();
  }

}
