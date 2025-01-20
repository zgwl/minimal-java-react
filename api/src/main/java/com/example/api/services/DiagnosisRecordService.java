package com.example.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.api.daos.DiagnosisRecord;
import com.example.api.daos.chat.DiagnosisDto;
import com.example.api.mappers.DiagnosisRecordMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiagnosisRecordService {
  private final DiagnosisRecordMapper diagnosisRecordMapper;

  public DiagnosisRecordService(DiagnosisRecordMapper diagnosisRecordMapper) {
    this.diagnosisRecordMapper = diagnosisRecordMapper;
  }

  public List<DiagnosisRecord> getAllRecords(Long userId) {
    return diagnosisRecordMapper.findAllByUserId(userId);
  }

  public void saveRecord(Long userId, UUID sessionId, DiagnosisDto diagnosis) {
    DiagnosisRecord diagnosisRecord = new DiagnosisRecord();
    diagnosisRecord.setUserId(userId);
    diagnosisRecord.setSessionId(sessionId);
    diagnosisRecord.setPrimarySymptom(diagnosis.getSymptoms().getPrimary());
    diagnosisRecord.setDuration(diagnosis.getSymptoms().getDuration());
    diagnosisRecord.setSeverity(diagnosis.getSymptoms().getSeverity());
    diagnosisRecord.setPossibleCondition(diagnosis.getAssessment().getPossibleCondition());
    diagnosisRecord.setConfidence(diagnosis.getAssessment().getConfidence());

    diagnosisRecordMapper.insert(diagnosisRecord);
    log.info("Saved diagnosis record for user {} with session {}", userId, sessionId);
  }

  public boolean isSessionComplete(Long userId, UUID sessionId) {
    DiagnosisRecord record = diagnosisRecordMapper.findByUserIdAndSessionId(userId, sessionId);
    return record != null;
  }
}