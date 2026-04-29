package me.lucasamscc.taskmanager.modules.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTaskDTO(
        @NotBlank(message = "Title is required")
        String title,
        String description,
        @NotNull(message = "UserId is required")
        UUID userId
) {}
