package com.example.api.daos.chat;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ChatRequest {
  private String model;
  private List<Message> messages;

  public ChatRequest(String model, String prompt, List<Message> previousMessages) {
    Message message = new Message();
    message.setRole("user");
    message.setContent(prompt);

    // Create a new mutable list and add previous messages
    this.messages = new ArrayList<>(previousMessages);
    this.messages.add(message);
    this.model = model;
  }
}
