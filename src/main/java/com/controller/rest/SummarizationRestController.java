package com.controller.rest;


import com.dto.response.TaskSummarizationResponse;
import com.service.SummarizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("api/internal/summarization")
@RequiredArgsConstructor
public class SummarizationRestController {

    private final SummarizationService summarizationService;


    @GetMapping("/{id}")
    public ResponseEntity<TaskSummarizationResponse> getSummarization(@PathVariable("id") UUID userId)
            throws IOException {

        byte[] resource = summarizationService.getSummarization(userId);

        TaskSummarizationResponse taskSummarizationResponse = new TaskSummarizationResponse(userId, resource);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"summary.pdf\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(taskSummarizationResponse);

    }
}