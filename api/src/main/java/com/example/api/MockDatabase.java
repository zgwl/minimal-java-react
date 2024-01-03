package com.example.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

@Component
public class MockDatabase {
  private final List<User> users = new ArrayList<>();
  private final AtomicLong counter = new AtomicLong();

  public List<User> getUsers() {
    return users;
  }

  public User addUser(User user) {
    user.setId(counter.incrementAndGet());
    users.add(user);
    return user;
  }

  public Optional<User> getUserById(Long id) {
    return users.stream()
        .filter(user -> user.getId().equals(id))
        .findFirst();
  }

  public Optional<User> updateUser(Long id, User updatedUser) {
    return getUserById(id).map(user -> {
      user.setName(updatedUser.getName());
      user.setEmail(updatedUser.getEmail());
      return user;
    });
  }

  public boolean deleteUser(Long id) {
    return users.removeIf(user -> user.getId().equals(id));
  }
}
