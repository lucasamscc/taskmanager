package me.lucasamscc.taskmanager.modules.task.subtask.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSubtaskDTO(
        @NotBlank(message = "Title is required")
        String title,
        String description
) {}
