package com.neurocloud.orchestrator.controller;

import com.neurocloud.orchestrator.dto.MatchScoreResponse;
import com.neurocloud.orchestrator.dto.ResumeAnalysisResponse;
import com.neurocloud.orchestrator.service.ResumeEnhancementService;
import com.neurocloud.orchestrator.service.ResumeMatchScoringService;
import com.neurocloud.orchestrator.service.ResumeOptimizationService;
import com.neurocloud.orchestrator.service.ResumeOrchestrationService;
import com.neurocloud.orchestrator.dto.TextExtractionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orchestrate")
public class ResumeOrchestrationController {

    @Autowired
    private ResumeOrchestrationService resumeOrchestrationService;

    @Autowired
    private ResumeEnhancementService resumeEnhancementService;

    @Autowired
    private ResumeOptimizationService resumeOptimizationService; //for calling ollama -> llama 3 model

    @Autowired
    private ResumeMatchScoringService resumeMatchScoringService;

    @PostMapping("/analyze-resume")
    public ResponseEntity<ResumeAnalysisResponse> analyzeResume(
            @RequestPart("resume") MultipartFile resume,
            @RequestPart(value = "jobDescription", required = false) MultipartFile jobDescription) {

        // Step 1: Extract text from resume & JD
        TextExtractionResponse extractedTexts = resumeOrchestrationService.processResume(resume, jobDescription);

        // Step 2: Enhance resume based on JD content (if present)
        String optimizedResume = resumeEnhancementService.enhanceResume(
                extractedTexts.getResumeText(),
                extractedTexts.getJobDescriptionText()
        );

        // Step 3: Return structured response
        ResumeAnalysisResponse response = new ResumeAnalysisResponse(
                extractedTexts.getResumeText(),
                extractedTexts.getJobDescriptionText(),
                optimizedResume
        );

        return ResponseEntity.ok(response);
    }

    //Calling the Ollama -> llama 3 model
    @PostMapping("/optimize-resume")
    public ResponseEntity<?> optimizeResume(
            @RequestPart("resume") MultipartFile resume,
            @RequestPart("jobDescription") MultipartFile jd) {

        String resumeText = resumeOrchestrationService.extractTextFromFile(resume); // or your method
        String jdText = resumeOrchestrationService.extractTextFromFile(jd);

        double scorePreChange = resumeMatchScoringService.calculateMatchScore(resumeText, jdText);

        String optimized = resumeOptimizationService.optimizeResume(resumeText, jdText);

        double scorePostChange = resumeMatchScoringService.calculateMatchScore(optimized, jdText);

        Map<String, String> result = new HashMap<>();
        result.put("PreChangeScore:",String.valueOf(scorePreChange));
        result.put("optimizedResume", optimized);
        result.put("PostChangeScore:",String.valueOf(scorePostChange));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/analyze/match-score")
    public ResponseEntity<MatchScoreResponse> getMatchScore(
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("jobDescription") MultipartFile jobDescription) {

        String resumeText = resumeOrchestrationService.extractTextFromFile(resume);
        String jdText = resumeOrchestrationService.extractTextFromFile(jobDescription);

        double score = resumeMatchScoringService.calculateMatchScore(resumeText, jdText);

        MatchScoreResponse response = new MatchScoreResponse(resumeText, jdText, score);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

}

