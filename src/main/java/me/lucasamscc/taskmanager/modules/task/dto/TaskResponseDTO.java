package me.lucasamscc.taskmanager.modules.task.dto;

import me.lucasamscc.taskmanager.core.domain.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        UUID userId
) {}
