package com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record TaskForSummaryDto(String header,
                                String body,
                                String status,
                                @JsonInclude(NON_NULL) Instant completedAt) {
}
