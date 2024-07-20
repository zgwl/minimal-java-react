package com.example.api.daos.chat;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OpenAIChatRequest {
  private String model;
  private List<OpenAIMessage> messages;

  public OpenAIChatRequest(String model, String prompt, List<OpenAIMessage> previousMessages) {
    OpenAIMessage message = new OpenAIMessage();
    message.setRole("user");
    message.setContent(prompt);

    // Create a new mutable list and add previous messages
    this.messages = new ArrayList<>(previousMessages);
    this.messages.add(message);
    this.model = model;
  }
}
