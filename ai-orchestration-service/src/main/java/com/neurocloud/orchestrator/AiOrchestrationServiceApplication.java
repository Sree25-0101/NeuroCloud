package com.neurocloud.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AiOrchestrationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiOrchestrationServiceApplication.class, args);
    }
}