package com.example.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.daos.DiagnosisRecord;
import com.example.api.services.DiagnosisRecordService;
import com.example.api.utils.CurrentUserUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/diagnosis-records")
@Slf4j
public class DiagnosisRecordController {
  private final DiagnosisRecordService diagnosisRecordService;
  private final CurrentUserUtil currentUserUtil;

  public DiagnosisRecordController(DiagnosisRecordService diagnosisRecordService,
      CurrentUserUtil currentUserUtil) {
    this.diagnosisRecordService = diagnosisRecordService;
    this.currentUserUtil = currentUserUtil;
  }

  @GetMapping
  public ResponseEntity<List<DiagnosisRecord>> getAllRecords() {
    Long userId = currentUserUtil.getCurrentUserId();
    log.info("Fetching diagnosis records for user: {}", userId);
    List<DiagnosisRecord> records = diagnosisRecordService.getAllRecords(userId);
    return ResponseEntity.ok(records);
  }
}