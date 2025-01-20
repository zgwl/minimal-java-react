package com.example.api.services;

import org.springframework.stereotype.Service;

import com.example.api.daos.chat.DiagnosisDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiagnosisService {
  private final ObjectMapper objectMapper;

  public DiagnosisService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public DiagnosisDto validateAndParseResponse(String content) {
    try {
      DiagnosisDto diagnosis = objectMapper.readValue(content, DiagnosisDto.class);
      if (isValidDiagnosis(diagnosis)) {
        return diagnosis;
      }
      throw new IllegalStateException("Invalid diagnosis format");
    } catch (JsonProcessingException e) {
      log.error("Failed to parse diagnosis response", e);
      throw new IllegalStateException("Invalid JSON response");
    }
  }

  private boolean isValidDiagnosis(DiagnosisDto diagnosis) {
    return diagnosis != null
        && diagnosis.getSymptoms() != null
        && diagnosis.getAssessment() != null
        && diagnosis.getMessage() != null;
  }
}