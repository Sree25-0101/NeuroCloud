package com.neurocloud.orchestrator.service.impl;

import com.neurocloud.orchestrator.dto.TextExtractionResponse;
import com.neurocloud.orchestrator.service.ResumeEnhancementService;
import com.neurocloud.orchestrator.service.ResumeOrchestrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ResumeOrchestrationServiceImpl implements ResumeOrchestrationService {

    private static final Logger log = LoggerFactory.getLogger(ResumeOrchestrationServiceImpl.class);

    private final String DOC_ANALYSIS_URL = "http://document-analysis-service/api/analyze-v2/extract-text";

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ResumeEnhancementService enhancementService;

    public String extractTextFromFile(MultipartFile file) {
        try {
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename(); // Required by WebClient to set file name
                }
            };

            String extractedText = webClientBuilder.build()
                    .post()
                    .uri(DOC_ANALYSIS_URL)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("file", resource))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // for now blocking, can be made reactive later

            log.info("🧾 Extracted text for file " , file.getOriginalFilename() );
            log.info(extractedText);

            return extractedText;

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract text from file via document-analysis-service", e);
        }
    }

    @Override
    public TextExtractionResponse processResume(MultipartFile resume, MultipartFile jobDescription) {
        String resumeText = extractTextFromFile(resume);

        String jdText = null;
        if (jobDescription != null && !jobDescription.isEmpty()) {
            jdText = extractTextFromFile(jobDescription);
        }

        String enhancedResume = enhancementService.enhanceResume(resumeText, jdText);

        // Optionally return enhanced resume in DTO
        return new TextExtractionResponse(resumeText, jdText, enhancedResume);
    }
}
