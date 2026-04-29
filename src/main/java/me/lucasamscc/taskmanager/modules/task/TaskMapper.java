package me.lucasamscc.taskmanager.modules.task;

import me.lucasamscc.taskmanager.modules.task.dto.TaskResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "user.id", target = "userId")
    TaskResponseDTO toDTO(Task task);
}