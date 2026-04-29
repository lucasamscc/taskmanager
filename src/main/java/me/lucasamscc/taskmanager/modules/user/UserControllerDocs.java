package me.lucasamscc.taskmanager.modules.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.lucasamscc.taskmanager.modules.user.dto.CreateUserDTO;
import me.lucasamscc.taskmanager.modules.user.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Usuários", description = "Gestão de usuários")
public interface UserControllerDocs {

    @Operation(summary = "Registar usuário", description = "Cria um novo usuário.")
    ResponseEntity<UserResponseDTO> create(CreateUserDTO dto);

    @Operation(summary = "Procurar usuário", description = "Recupera dados do usuário por ID.")
    ResponseEntity<UserResponseDTO> findById(UUID id);
}