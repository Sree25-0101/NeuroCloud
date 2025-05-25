package com.neurocloud.orchestrator.dto;

public class ResumeAnalysisResponse {
    private String resumeText;
    private String jobDescriptionText;
    private String optimizedResume;

    public ResumeAnalysisResponse(String resumeText, String jobDescriptionText, String optimizedResume) {
        this.resumeText = resumeText;
        this.jobDescriptionText = jobDescriptionText;
        this.optimizedResume = optimizedResume;
    }

    public String getResumeText() {
        return resumeText;
    }

    public String getJobDescriptionText() {
        return jobDescriptionText;
    }

    public String getOptimizedResume() {
        return optimizedResume;
    }
}