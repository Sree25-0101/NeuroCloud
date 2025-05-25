package com.neurocloud.orchestrator.service.impl;

import com.neurocloud.orchestrator.llm.OllamaClient;
import com.neurocloud.orchestrator.service.ResumeOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeOptimizationServiceImpl implements ResumeOptimizationService {

    @Autowired
    private OllamaClient ollamaClient;

    @Override
    public String optimizeResume(String resumeText, String jdText) {

        //Prompt 1

//        String prompt = "Given the following resume:\n\n" + resumeText +
//                "\n\nAnd this job description:\n\n" + jdText +
//                "\n\nRewrite the resume to better match the job description and improve its ATS ranking. Focus on skill alignment, clarity, and relevance.";

        //Prompt 2

//        String prompt = "You are a resume optimization assistant for job seekers.\n\n" +
//                "Below is a resume and a job description. Analyze both, and rewrite the resume to:\n" +
//                "- Highlight experiences and skills relevant to the job\n" +
//                "- Use keywords from the job description\n" +
//                "- Tailor it for ATS (Applicant Tracking System) compatibility\n" +
//                "- Prioritize keeping the original candidate's skills where they align with the job\n" +
//                "- Keep it professional and concise\n\n" +
//                "RESUME:\n" + resumeText + "\n\n" +
//                "JOB DESCRIPTION:\n" + jdText + "\n\n" +
//                "Now return the improved, optimized resume and remember to modify my information and rephrase it to better suite the job description that scores a better score  for ATS: and return with the same sections that are initially there";

        //Prompt 3
        String prompt = "You are an expert resume optimization assistant specializing in tailoring resumes to job descriptions for high ATS (Applicant Tracking System) compatibility.\n\n" +
                "Your task is to:\n" +
                "- Rewrite the candidate’s resume to align with the job description\n" +
                "- Prioritize relevant skills and experience using the job's terminology\n" +
                "- Maintain original content where possible — rephrase, don't fabricate\n" +
                "- Preserve these sections and order: Summary, Skills, Experience, Education\n" +
                "- Use clear formatting: bullet points for skills and experience, professional tone\n" +
                "- Avoid generic fluff; focus on alignment with the job role\n\n" +
                "RESUME:\n" + resumeText + "\n\n" +
                "JOB DESCRIPTION:\n" + jdText + "\n\n" +
                "Return only the fully rewritten, optimized resume with improved alignment to the job description.";


        return ollamaClient.generateResponse(prompt);
    }
}
