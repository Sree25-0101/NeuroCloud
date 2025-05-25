package com.neurocloud.orchestrator.dto;

public class MatchScoreResponse {
    private String resumeText;
    private String jobDescriptionText;
    private double matchScore;

    // Constructors
    public MatchScoreResponse() {}

    public MatchScoreResponse(String resumeText, String jobDescriptionText, double matchScore) {
        this.resumeText = resumeText;
        this.jobDescriptionText = jobDescriptionText;
        this.matchScore = matchScore;
    }

    // Getters and Setters

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

    public double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }
}
