package com.neurocloud.orchestrator.service.impl;

import com.neurocloud.orchestrator.service.ResumeEnhancementService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ResumeEnhancementServiceImpl implements ResumeEnhancementService {

    @Override
    public String enhanceResume(String resumeText, String jobDescriptionText) {
        // Very basic placeholder logic — match keywords from JD in resume
        if (resumeText == null || jobDescriptionText == null) return resumeText;

        // Collect JD keywords
        java.util.Set<String> jdSkills = new java.util.HashSet<>(
                java.util.Arrays.asList(jobDescriptionText.toLowerCase().split("\\W+"))
        );

        // Enhance resume by highlighting matching words
        StringBuilder highlightedResume = new StringBuilder();
        for (String word : resumeText.split("\\s+")) {
            if (jdSkills.contains(word.toLowerCase())) {
                highlightedResume.append("**").append(word).append("** ");
            } else {
                highlightedResume.append(word).append(" ");
            }
        }

        return "Optimized Resume:\n" + highlightedResume.toString().trim();
    }

}
