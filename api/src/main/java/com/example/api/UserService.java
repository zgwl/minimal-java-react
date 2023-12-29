package com.example.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final MockDatabase mockDatabase;

  public UserService(@Autowired MockDatabase mockDatabase) {
    this.mockDatabase = mockDatabase;
  }

  public List<User> getAllUsers() {
    return mockDatabase.getUsers();
  }

  public Optional<User> getUserById(Long id) {
    return mockDatabase.getUserById(id);
  }

  public User addUser(User user) {
    mockDatabase.addUser(user);
    return user;
  }

  public Optional<User> updateUser(Long id, User updatedUser) {
    // TODO: Finish this function.
    throw new UnsupportedOperationException();
  }

  public void deleteUser(Long id) {
    // TODO: Finish this function.
    throw new UnsupportedOperationException();
  }
}
