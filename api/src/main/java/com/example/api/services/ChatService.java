package com.example.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.api.daos.chat.ChatRequest;
import com.example.api.daos.chat.ChatResponse;

@Service
public class ChatService {
  private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

  @Value("${openai.api.model}")
  private String model;

  @Value("${openai.api.url}")
  private String url;

  private final RestTemplate restTemplate;

  public ChatService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String chat(String prompt) {
    ChatRequest chatRequest = new ChatRequest(model, prompt);

    ChatResponse response = restTemplate.postForObject(url, chatRequest, ChatResponse.class);

    logger.info("Chat response: {}", response);

    if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
      return "I'm sorry, I don't understand.";
    }

    return response.getChoices().get(0).getMessage().getContent();
  }
}
