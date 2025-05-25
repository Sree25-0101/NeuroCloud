package com.neurocloud.docanalysis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/analyze")
public class DocumentAnalysisController {

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractText(@RequestParam("file") MultipartFile file) {
        // TODO: Use Apache Tika or PDFBox to extract text
        String extractedText = "Simulated extracted text from " + file.getOriginalFilename();

        return ResponseEntity.ok(extractedText);
    }
}

