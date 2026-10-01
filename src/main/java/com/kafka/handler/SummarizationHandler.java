package com.kafka.handler;


import com.kafka.rpc.summarization.SchedulerSummarizationRequest;
import com.kafka.rpc.summarization.SchedulerSummarizationResponse;
import com.service.SummarizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@KafkaListener(topics = "schedular-summarization-request-topic", groupId = "summarization-group")
@RequiredArgsConstructor
public class SummarizationHandler {

    private final SummarizationService summarizationService;

    @KafkaHandler
    @SendTo
    public SchedulerSummarizationResponse handle(SchedulerSummarizationRequest request) throws IOException {
        log.info("RECEIVE REQUEST {}", request);

        UUID userId = request.getId();

        byte[] resource = summarizationService.getSummarization(userId);
        log.info("RECEIVE REPORT");

        return new SchedulerSummarizationResponse(userId, resource);

    }
}
