package com.example.api.daos.chat;

import java.util.UUID;

import lombok.Data;

@Data
public class ChatResponseDto {
  private String content;
  private UUID sessionId;
  private boolean diagnosisComplete;

  public ChatResponseDto(String content, UUID sessionId, boolean diagnosisComplete) {
    this.content = content;
    this.sessionId = sessionId;
    this.diagnosisComplete = diagnosisComplete;
  }
}
