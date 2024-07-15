package com.example.api.daos.chat;

import java.util.List;

import lombok.Data;

@Data
public class ChatRequest {
  private String model;
  private List<Message> messages;

  public ChatRequest(String model, String prompt) {
    Message message = new Message();
    message.setRole("user");
    message.setContent(prompt);

    this.model = model;
    this.messages = List.of(message);
  }
}
