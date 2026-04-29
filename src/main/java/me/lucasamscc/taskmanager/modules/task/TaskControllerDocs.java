package me.lucasamscc.taskmanager.modules.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.lucasamscc.taskmanager.core.domain.TaskStatus;
import me.lucasamscc.taskmanager.core.exception.ErrorResponse;
import me.lucasamscc.taskmanager.modules.task.dto.CreateTaskDTO;
import me.lucasamscc.taskmanager.modules.task.dto.TaskResponseDTO;
import me.lucasamscc.taskmanager.modules.task.dto.UpdateTaskStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@Tag(name = "Tarefas", description = "Gestão das tarefas")
public interface TaskControllerDocs {

    @Operation(summary = "Criar nova tarefa", description = "Regista uma tarefa vinculada a um utilizador.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Utilizador não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<TaskResponseDTO> create(CreateTaskDTO dto);

    @Operation(summary = "Listar tarefas", description = "Retorna todas as tarefas, com filtro opcional por status.")
    ResponseEntity<Page<TaskResponseDTO>> findAll(TaskStatus status, Pageable pageable);

    @Operation(summary = "Procurar por ID", description = "Recupera os detalhes de uma tarefa específica.")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    ResponseEntity<TaskResponseDTO> findById(UUID id);

    @Operation(summary = "Atualizar status", description = "Altera o status da tarefa. Impede a conclusão se existirem subtarefas pendentes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado"),
            @ApiResponse(responseCode = "422", description = "Violação de regra de negócio")
    })
    ResponseEntity<TaskResponseDTO> updateStatus(UUID id, UpdateTaskStatusDTO dto);
}