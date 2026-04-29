package me.lucasamscc.taskmanager.modules.task.subtask.dto;


import me.lucasamscc.taskmanager.core.domain.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubtaskResponseDTO(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        UUID taskId

) {}