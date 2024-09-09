package com.example.api.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.example.api.daos.chat.ChatRecord;
import com.example.api.daos.chat.ChatResponseDto;
import com.example.api.daos.chat.OpenAIChatRequest;
import com.example.api.daos.chat.OpenAIChatResponse;
import com.example.api.daos.chat.OpenAIMessage;
import com.example.api.mappers.UserChatMapper;
import com.example.api.utils.CurrentUserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenAIChatService {
  private static final Logger logger = LoggerFactory.getLogger(OpenAIChatService.class);

  @Value("${openai.api.model}")
  private String model;

  @Value("${openai.api.url}")
  private String url;

  private final RestTemplate restTemplate;
  private final CurrentUserUtil currentUserUtil;
  private final UserChatMapper userChatMapper;
  private final ObjectMapper objectMapper;
  private final ResourceLoader resourceLoader;

  private String systemPrompt;

  public OpenAIChatService(RestTemplate restTemplate, CurrentUserUtil currentUserUtil, UserChatMapper userChatMapper,
      ObjectMapper objectMapper, ResourceLoader resourceLoader) {
    this.restTemplate = restTemplate;
    this.currentUserUtil = currentUserUtil;
    this.userChatMapper = userChatMapper;
    this.objectMapper = objectMapper;
    this.resourceLoader = resourceLoader;
    this.systemPrompt = loadSystemPrompt();
  }

  public ChatResponseDto chat(String prompt, UUID sessionId) {
    Long userId = currentUserUtil.getCurrentUserId();

    if (sessionId == null) {
      sessionId = UUID.randomUUID();
    }

    OpenAIChatRequest chatRequest = this.assembleChatRequest(prompt, userId, sessionId);
    logger.info("Chat request: {}", chatRequest);

    OpenAIChatResponse response = restTemplate.postForObject(url, chatRequest, OpenAIChatResponse.class);
    logger.info("Chat response: {}", response);

    String content = "I'm sorry, I don't understand.";
    if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
      content = response.getChoices().get(0).getMessage().getContent();
    }

    this.upsertChatRecord(chatRequest.getMessages(), content, userId, sessionId);

    return new ChatResponseDto(content, sessionId);
  }

  private OpenAIChatRequest assembleChatRequest(String prompt, Long userId, UUID sessionId) {
    ChatRecord chatRecord = userChatMapper.findByUserIdAndSessionId(userId, sessionId);
    logger.info("Chat record: {}", chatRecord);

    List<OpenAIMessage> previousMessages = new ArrayList<>();
    if (chatRecord == null) {
      OpenAIMessage message = new OpenAIMessage();
      message.setRole("system");
      message.setContent(systemPrompt);
      previousMessages = List.of(message);
    } else {
      try {
        previousMessages = objectMapper.readValue(chatRecord.getMessages(), new TypeReference<List<OpenAIMessage>>() {
        });
      } catch (IOException e) {
        logger.error("Error parsing messages JSON", e);
      }
    }

    return new OpenAIChatRequest(model, prompt, previousMessages);
  }

  private void upsertChatRecord(List<OpenAIMessage> messages, String newContent, Long userId, UUID sessionId) {
    OpenAIMessage newMessage = new OpenAIMessage();
    newMessage.setRole("assistant");
    newMessage.setContent(newContent);
    messages.add(newMessage);

    try {
      String messagesJson = objectMapper.writeValueAsString(messages);
      ChatRecord existingRecord = userChatMapper.findByUserIdAndSessionId(userId, sessionId);
      if (existingRecord == null) {
        userChatMapper.insertChat(userId, sessionId, messagesJson);
      } else {
        userChatMapper.updateChat(userId, sessionId, messagesJson);
      }
    } catch (JsonProcessingException e) {
      logger.error("Error converting messages to JSON", e);
    }
  }

  private String loadSystemPrompt() {
    try {
      Resource resource = resourceLoader.getResource("classpath:system_prompt.txt");
      if (resource.exists()) {
        String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        logger.info("Manually loaded system prompt: {}", content);
        return content;
      } else {
        throw new RuntimeException("system_prompt.txt not found in classpath.");
      }
    } catch (IOException e) {
      throw new RuntimeException("Error loading system prompt", e);
    }
  }
}
