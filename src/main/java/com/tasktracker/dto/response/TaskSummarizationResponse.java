package com.tasktracker.dto.response;

import java.util.UUID;

public record TaskSummarizationResponse(UUID userId,
                                        byte[] resource) {
}
