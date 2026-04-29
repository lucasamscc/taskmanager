package me.lucasamscc.taskmanager.modules.task.subtask;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.CreateSubtaskDTO;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.SubtaskResponseDTO;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.UpdateSubtaskStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Subtarefas", description = "Gestão das subtarefas")
public interface SubtaskControllerDocs {

    @Operation(summary = "Criar subtarefa", description = "Adiciona uma subtarefa a uma tarefa existente.")
    ResponseEntity<SubtaskResponseDTO> create(UUID taskId, CreateSubtaskDTO dto);

    @Operation(summary = "Listar subtarefas da tarefa", description = "Retorna todos os itens de uma tarefa específica.")
    ResponseEntity<Page<SubtaskResponseDTO>> findByTask(UUID taskId, Pageable pageable);

    @Operation(summary = "Atualizar status da subtarefa", description = "Atualiza o status de uma subtarefa específica.")
    ResponseEntity<SubtaskResponseDTO> updateStatus(UUID id, UpdateSubtaskStatusDTO dto);
}