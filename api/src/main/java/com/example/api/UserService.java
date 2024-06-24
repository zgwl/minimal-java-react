package com.example.api;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserMapper userMapper;

  public UserService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public List<User> getAllUsers() {
    return userMapper.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return Optional.ofNullable(userMapper.findById(id));
  }

  public User addUser(User user) {
    userMapper.insert(user);
    return user;
  }

  public Optional<User> updateUser(Long id, User updatedUser) {
    updatedUser.setId(id);
    userMapper.update(updatedUser);
    return Optional.of(updatedUser);
  }

  public void deleteUser(Long id) {
    userMapper.delete(id);
  }
}
