package com.controller.rest;


import com.service.SummarizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/summarization")
@RequiredArgsConstructor
public class SummarizationRestController {

    private final SummarizationService summarizationService;


    @GetMapping("/{id}")
    public String getSummarization(@PathVariable("id") UUID userId) {
        return summarizationService.getSummarization(userId);
    }
}