package me.lucasamscc.taskmanager.modules.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.lucasamscc.taskmanager.core.domain.TaskStatus;
import me.lucasamscc.taskmanager.modules.task.dto.CreateTaskDTO;
import me.lucasamscc.taskmanager.modules.task.dto.TaskResponseDTO;
import me.lucasamscc.taskmanager.modules.task.dto.UpdateTaskStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController implements TaskControllerDocs{

    private final TaskService taskService;

    @Override
    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@RequestBody @Valid CreateTaskDTO dto) {
        TaskResponseDTO response = taskService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> findAll(@RequestParam(required = false) TaskStatus status, Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.findAll(status, pageable);
        return ResponseEntity.ok(tasks);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable UUID id) {
        TaskResponseDTO response = taskService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTaskStatusDTO dto
    ) {
        TaskResponseDTO response = taskService.updateStatus(id, dto);
        return ResponseEntity.ok(response);
    }
}