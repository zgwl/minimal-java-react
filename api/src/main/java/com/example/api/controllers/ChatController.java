package com.example.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.services.ChatService;

@RestController
@RequestMapping("/api")
public class ChatController {

  private final ChatService chatService;

  public ChatController(ChatService openAIService) {
    this.chatService = openAIService;
  }

  @GetMapping("/chat")
  public ResponseEntity<String> chat(@RequestParam String prompt) {
    String response = chatService.chat(prompt);

    return ResponseEntity.ok(response);
  }
}
