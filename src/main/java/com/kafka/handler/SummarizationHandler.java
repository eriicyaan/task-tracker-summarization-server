package com.kafka.handler;


import com.kafka.rpc.summarization.SchedulerSummarizationRequest;
import com.kafka.rpc.summarization.SchedulerSummarizationResponse;
import com.service.SummarizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@KafkaListener(topics = "schedular-summarization-request-topic", groupId = "schedular-summarization-request-group")
@RequiredArgsConstructor
public class SummarizationHandler {

    private final SummarizationService summarizationService;

    @KafkaHandler
    public SchedulerSummarizationResponse handle(SchedulerSummarizationRequest request) throws IOException {
        UUID userId = request.getId();

        InputStreamResource resource = summarizationService.getSummarization(userId);

        return new SchedulerSummarizationResponse(userId, resource);

    }
}
