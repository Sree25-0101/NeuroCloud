package com.neurocloud.docanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DocumentAnalysisServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentAnalysisServiceApplication.class, args);
    }
}