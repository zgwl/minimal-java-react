package com.example.api.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.daos.chat.ChatResponseDto;
import com.example.api.services.OpenAIChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final OpenAIChatService openAIChatService;

  public ChatController(OpenAIChatService openAIChatService) {
    this.openAIChatService = openAIChatService;
  }

  @GetMapping
  public ResponseEntity<ChatResponseDto> chat(@RequestParam String prompt,
      @RequestParam(required = false) UUID sessionId) {
    ChatResponseDto response = openAIChatService.chat(prompt, sessionId);
    return ResponseEntity.ok(response);
  }
}
