package com.example.api.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.api.daos.chat.ChatRecord;
import com.example.api.daos.chat.ChatRequest;
import com.example.api.daos.chat.ChatResponse;
import com.example.api.daos.chat.Message;
import com.example.api.mappers.UserChatMapper;
import com.example.api.utils.CurrentUserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatService {
  private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

  @Value("${openai.api.model}")
  private String model;

  @Value("${openai.api.url}")
  private String url;

  private final RestTemplate restTemplate;
  private final CurrentUserUtil currentUserUtil;
  private final UserChatMapper userChatMapper;
  private final ObjectMapper objectMapper;

  public ChatService(RestTemplate restTemplate, CurrentUserUtil currentUserUtil, UserChatMapper userChatMapper,
      ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.currentUserUtil = currentUserUtil;
    this.userChatMapper = userChatMapper;
    this.objectMapper = objectMapper;
  }

  public String chat(String prompt) {
    Long userId = currentUserUtil.getCurrentUserId();

    ChatRequest chatRequest = this.assembleChatRequest(prompt, userId);
    logger.info("Chat request: {}", chatRequest);

    ChatResponse response = restTemplate.postForObject(url, chatRequest, ChatResponse.class);
    logger.info("Chat response: {}", response);

    if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
      return "I'm sorry, I don't understand.";
    }

    String content = response.getChoices().get(0).getMessage().getContent();

    this.upsertChatRecord(chatRequest.getMessages(), content, userId);

    return content;
  }

  private ChatRequest assembleChatRequest(String prompt, Long userId) {
    ChatRecord chatRecord = userChatMapper.findByUserId(userId);
    logger.info("Chat record: {}", chatRecord);

    List<Message> previousMessages = new ArrayList<>();
    if (chatRecord == null) {
      Message message = new Message();
      message.setRole("system");
      message.setContent("Hello! How can I help you today?");
      previousMessages = List.of(message);
    } else {
      try {
        previousMessages = objectMapper.readValue(chatRecord.getMessages(), new TypeReference<List<Message>>() {
        });
      } catch (IOException e) {
        logger.error("Error parsing messages JSON", e);
      }
    }

    return new ChatRequest(model, prompt, previousMessages);
  }

  private void upsertChatRecord(List<Message> previousMessages, String newContent, Long userId) {
    Message newMessage = new Message();
    newMessage.setRole("assistant");
    newMessage.setContent(newContent);
    previousMessages.add(newMessage);

    try {
      String messagesJson = objectMapper.writeValueAsString(previousMessages);
      userChatMapper.upsertChat(userId, messagesJson);
    } catch (JsonProcessingException e) {
      logger.error("Error converting messages to JSON", e);
    }
  }
}
