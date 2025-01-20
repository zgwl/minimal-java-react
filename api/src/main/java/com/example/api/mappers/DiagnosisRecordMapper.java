package com.example.api.mappers;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.example.api.daos.DiagnosisRecord;
import com.example.api.typehandlers.UUIDTypeHandler;

@Mapper
public interface DiagnosisRecordMapper {
  @Select("SELECT id, user_id AS userId, " +
      "CAST(session_id AS varchar) AS sessionId, " +
      "primary_symptom AS primarySymptom, duration, severity, " +
      "possible_condition AS possibleCondition, confidence, created_at AS createdAt " +
      "FROM diagnosis_records WHERE user_id = #{userId} ORDER BY created_at DESC")
  @Result(column = "sessionId", property = "sessionId", typeHandler = UUIDTypeHandler.class)
  List<DiagnosisRecord> findAllByUserId(@Param("userId") Long userId);

  @Insert("INSERT INTO diagnosis_records (user_id, session_id, primary_symptom, duration, severity, possible_condition, confidence) "
      +
      "VALUES (#{record.userId}, #{record.sessionId, typeHandler=com.example.api.typehandlers.UUIDTypeHandler}, " +
      "#{record.primarySymptom}, #{record.duration}, #{record.severity}, #{record.possibleCondition}, #{record.confidence})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(@Param("record") DiagnosisRecord record);

  @Select("SELECT * FROM diagnosis_records WHERE user_id = #{userId} AND session_id = #{sessionId}")
  @Result(column = "sessionId", property = "sessionId", typeHandler = UUIDTypeHandler.class)
  DiagnosisRecord findByUserIdAndSessionId(@Param("userId") Long userId, @Param("sessionId") UUID sessionId);
}