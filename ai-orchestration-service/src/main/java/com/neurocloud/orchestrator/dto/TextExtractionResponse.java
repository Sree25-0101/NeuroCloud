package com.neurocloud.orchestrator.dto;

public class TextExtractionResponse {
    private String resumeText;
    private String jobDescriptionText;
    private String enhancedResume; // NEW

    public TextExtractionResponse() {}

    public TextExtractionResponse(String resumeText, String jobDescriptionText) {
        this.resumeText = resumeText;
        this.jobDescriptionText = jobDescriptionText;
    }

    public TextExtractionResponse(String resumeText, String jobDescriptionText, String enhancedResume) {
        this.resumeText = resumeText;
        this.jobDescriptionText = jobDescriptionText;
        this.enhancedResume = enhancedResume;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }

    public String getJobDescriptionText() {
        return jobDescriptionText;
    }

    public void setJobDescriptionText(String jobDescriptionText) {
        this.jobDescriptionText = jobDescriptionText;
    }

    public String getEnhancedResume() {
        return enhancedResume;
    }

    public void setEnhancedResume(String enhancedResume) {
        this.enhancedResume = enhancedResume;
    }
}
