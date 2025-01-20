package com.example.api.daos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class DiagnosisRecord {
  private Long id;
  private Long userId;
  private UUID sessionId;
  private String primarySymptom;
  private String duration;
  private String severity;
  private String possibleCondition;
  private String confidence;
  private LocalDateTime createdAt;
}