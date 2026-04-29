package me.lucasamscc.taskmanager.modules.user;

import me.lucasamscc.taskmanager.modules.user.dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(User user);
}