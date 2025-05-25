package com.neurocloud.orchestrator.service;

public interface ResumeMatchScoringService {
    double calculateMatchScore(String resumeText, String jdText);
}
