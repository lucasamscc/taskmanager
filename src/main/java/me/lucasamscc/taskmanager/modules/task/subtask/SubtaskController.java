package me.lucasamscc.taskmanager.modules.task.subtask;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.CreateSubtaskDTO;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.SubtaskResponseDTO;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.UpdateSubtaskStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubtaskController implements SubtaskControllerDocs{

    private final SubtaskService subtaskService;

    @Override
    @PostMapping("/tasks/{taskId}/subtasks")
    public ResponseEntity<SubtaskResponseDTO> create(
            @PathVariable UUID taskId,
            @RequestBody @Valid CreateSubtaskDTO dto
    ) {
        SubtaskResponseDTO response = subtaskService.create(taskId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/tasks/{taskId}/subtasks")
    public ResponseEntity<Page<SubtaskResponseDTO>> findByTask(
            @PathVariable UUID taskId,
            Pageable pageable
    ) {
        Page<SubtaskResponseDTO> subtasks = subtaskService.findByTaskId(taskId, pageable);
        return ResponseEntity.ok(subtasks);
    }

    @Override
    @PatchMapping("/subtasks/{id}/status")
    public ResponseEntity<SubtaskResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateSubtaskStatusDTO dto
    ) {
        SubtaskResponseDTO response = subtaskService.updateStatus(id, dto);
        return ResponseEntity.ok(response);
    }
}