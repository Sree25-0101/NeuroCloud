package com.neurocloud.orchestrator.service.impl;

import com.neurocloud.orchestrator.service.ResumeMatchScoringService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResumeMatchScoringServiceImpl implements ResumeMatchScoringService {

    @Override
    public double calculateMatchScore(String resumeText, String jdText) {
        if (resumeText == null || jdText == null) return 0.0;

        Set<String> resumeWords = new HashSet<>(Arrays.asList(resumeText.toLowerCase().split("\\W+")));
        Set<String> jdWords = new HashSet<>(Arrays.asList(jdText.toLowerCase().split("\\W+")));

        resumeWords.retainAll(jdWords);
        int common = resumeWords.size();
        int total = new HashSet<>(Arrays.asList(jdText.toLowerCase().split("\\W+"))).size();

        return total == 0 ? 0.0 : (common * 100.0) / total;
    }
}
