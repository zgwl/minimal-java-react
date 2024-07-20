package com.example.api.daos.chat;

import lombok.Data;

@Data
public class OpenAIMessage {
  private String role;
  private String content;
}
