package me.lucasamscc.taskmanager.modules.task.subtask.dto;

import jakarta.validation.constraints.NotNull;
import me.lucasamscc.taskmanager.core.domain.TaskStatus;

public record UpdateSubtaskStatusDTO(
        @NotNull TaskStatus status) {
}
