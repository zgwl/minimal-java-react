package com.example.api.daos.chat;

import java.util.List;

import lombok.Data;

@Data
public class OpenAIChatResponse {
  private long created;
  private List<OpenAIChoice> choices;
}
