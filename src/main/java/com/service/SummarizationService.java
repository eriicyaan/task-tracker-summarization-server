package com.service;


import com.dto.TaskForSummaryDto;
import com.dto.response.TaskResponse;
import com.entity.TaskStatus;
import com.mapper.TaskResponseMapper;
import com.openai.client.OpenAIClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class SummarizationService {

    @Value("${llm.prompt}")
    private String prompt;


    @Value("${llm.model}")
    private String model;

    @Value("${internal.service.secret}")
    private String secret;


    private final OpenAIService openAIService;
    private final TaskResponseMapper taskResponseMapper;
    private final ObjectMapper objectMapper;



    public String getSummarization(UUID userId) {
        List<TaskForSummaryDto> tasks = getTasks(userId);
        String prompt = createPrompt(tasks);

        String response = openAIService.generateReport(prompt, model);

        log.warn(response);
        return response;
    }


    private List<TaskForSummaryDto> getTasks(UUID id) {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:8081/api/internal/tasks/" + id)
                .defaultHeader("X-Internal-Service-Key", secret)
                .build();


        List<TaskResponse> tasks = restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return tasks.stream()
                .map(taskResponseMapper::map)
                .toList();
    }


    private String createPrompt(List<TaskForSummaryDto> tasks) {
        String jsonTasks = objectMapper.writeValueAsString(tasks);

        return prompt.formatted(jsonTasks);
    }


}
