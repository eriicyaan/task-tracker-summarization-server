package com.tasktracker.service;


import com.tasktracker.dto.TaskForSummaryDto;
import com.tasktracker.entity.LLMProperties;
import com.tasktracker.handler.exception.UserNotFoundException;
import com.tasktracker.mapper.TaskResponseMapper;
import com.tasktracker.response.TaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class SummarizationService {

    private final LLMProperties llmProperties;

    @Value("${internal.service.secret}")
    private String secret;


    private final OpenAIService openAIService;
    private final TaskResponseMapper taskResponseMapper;
    private final ObjectMapper objectMapper;
    private final PdfService pdfService;


    public byte[] getSummarization(UUID userId) throws IOException {
        List<TaskForSummaryDto> tasks = getTasks(userId);
        String prompt = createPrompt(tasks);

        String text = openAIService.generateReport(prompt, llmProperties.model());

        return  pdfService.createPdf(text);

    }


    private List<TaskForSummaryDto> getTasks(UUID id) {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://task-tracker-backend:8080/api/internal/backend/tasks/" + id)
                .defaultHeader("X-Internal-Service-Key", secret)
                .build();


        List<TaskResponse> tasks = restClient.get()
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    throw new UserNotFoundException("user not found");}
                )
                .body(new ParameterizedTypeReference<>() {
                });

        return tasks.stream()
                .map(taskResponseMapper::map)
                .toList();
    }


    private String createPrompt(List<TaskForSummaryDto> tasks) {
        String jsonTasks = objectMapper.writeValueAsString(tasks);

        return llmProperties.prompt().formatted(jsonTasks);
    }


}
