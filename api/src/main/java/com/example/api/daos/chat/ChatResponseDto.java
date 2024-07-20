package com.example.api.daos.chat;

import java.util.UUID;

import lombok.Data;

@Data
public class ChatResponseDto {
  private String content;
  private UUID sessionId;

  public ChatResponseDto(String content, UUID sessionId) {
    this.content = content;
    this.sessionId = sessionId;
  }
}
