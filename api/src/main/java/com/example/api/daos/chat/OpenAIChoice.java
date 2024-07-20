package com.example.api.daos.chat;

import lombok.Data;

@Data
public class OpenAIChoice {
  private int index;
  private OpenAIMessage message;
}
