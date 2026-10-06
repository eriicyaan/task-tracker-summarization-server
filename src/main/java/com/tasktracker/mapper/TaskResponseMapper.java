package com.tasktracker.mapper;

import com.tasktracker.dto.TaskForSummaryDto;
import com.tasktracker.dto.response.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskResponseMapper implements Mapper<TaskResponse, TaskForSummaryDto>{

    @Override
    public TaskForSummaryDto map(TaskResponse object) {
        return new TaskForSummaryDto(
                object.header(),
                object.body(),
                object.status(),
                object.completedAt()
                );
    }
}
