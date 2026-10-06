package com.tasktracker.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "llm")
public record LLMProperties(String model,
                            String prompt,
                            String apiToken,
                            String baseUrl) {
}
