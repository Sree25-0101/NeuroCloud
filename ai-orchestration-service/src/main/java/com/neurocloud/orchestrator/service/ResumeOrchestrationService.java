package com.neurocloud.orchestrator.service;

import com.neurocloud.orchestrator.dto.TextExtractionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeOrchestrationService {
    TextExtractionResponse processResume(MultipartFile resume, MultipartFile jobDescription);
    String extractTextFromFile(MultipartFile file);
}
