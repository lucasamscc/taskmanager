package me.lucasamscc.taskmanager.modules.task.dto;

import jakarta.validation.constraints.NotNull;
import me.lucasamscc.taskmanager.core.domain.TaskStatus;

public record UpdateTaskStatusDTO(
        @NotNull(message = "Status is required") TaskStatus status) {
}
