package com.example.api.daos.chat;

import lombok.Data;

@Data
public class DiagnosisDto {
  private Symptoms symptoms;
  private Assessment assessment;
  private boolean diagnosisComplete;
  private String message;

  @Data
  public static class Symptoms {
    private String primary;
    private String duration;
    private String severity;
  }

  @Data
  public static class Assessment {
    private String possibleCondition;
    private String confidence;
  }
}