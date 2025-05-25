package com.neurocloud.docanalysis.controller;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/analyze-v2")
public class TextExtractionController {

    private final Tika tika = new Tika();

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractText(@RequestParam("file") MultipartFile file) {
        try {
            String text = tika.parseToString(file.getInputStream());
            return ResponseEntity.ok(text);
        } catch (IOException | TikaException e) {
            return ResponseEntity.status(500).body("Failed to extract text: " + e.getMessage());
        }
    }
}
