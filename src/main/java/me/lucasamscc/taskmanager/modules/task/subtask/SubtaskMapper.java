package me.lucasamscc.taskmanager.modules.task.subtask;

import me.lucasamscc.taskmanager.modules.task.subtask.dto.SubtaskResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubtaskMapper {
    @Mapping(source = "task.id", target = "taskId")
    SubtaskResponseDTO toDTO(Subtask subtask);
}