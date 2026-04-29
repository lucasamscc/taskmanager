package me.lucasamscc.taskmanager.modules.task.subtask;

import lombok.RequiredArgsConstructor;
import me.lucasamscc.taskmanager.core.domain.TaskStatus;
import me.lucasamscc.taskmanager.core.exception.ResourceNotFoundException;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.CreateSubtaskDTO;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.SubtaskResponseDTO;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.UpdateSubtaskStatusDTO;
import me.lucasamscc.taskmanager.modules.task.TaskRepository;
import me.lucasamscc.taskmanager.modules.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;
    private final SubtaskMapper subtaskMapper;

    private Subtask getSubtaskEntity(UUID id) {
        return subtaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subtask not found"));
    }

    @Transactional(readOnly = true)
    public Page<SubtaskResponseDTO> findByTaskId(UUID taskId, Pageable pageable) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found");
        }
        Page<Subtask> subtasks = subtaskRepository.findByTaskId(taskId, pageable);
        return subtasks.map(subtaskMapper::toDTO);
    }

    @Transactional
    public SubtaskResponseDTO create(UUID taskId, CreateSubtaskDTO dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Subtask subtask = Subtask.builder()
                .title(dto.title())
                .description(dto.description())
                .status(TaskStatus.PENDING)
                .task(task)
                .createdAt(LocalDateTime.now())
                .completedAt(null)
                .build();

        return subtaskMapper.toDTO(subtaskRepository.save(subtask));
    }

    @Transactional
    public SubtaskResponseDTO updateStatus(UUID subtaskId, UpdateSubtaskStatusDTO dto) {
        Subtask subtask = getSubtaskEntity(subtaskId);

        TaskStatus newSubtaskStatus = dto.status();

        if (subtask.getStatus() == newSubtaskStatus) {
            return subtaskMapper.toDTO(subtask);
        }

        subtask.setStatus(newSubtaskStatus);

        if (newSubtaskStatus == TaskStatus.COMPLETED) {
            subtask.setCompletedAt(LocalDateTime.now());
        } else {
            subtask.setCompletedAt(null);
        }

        return subtaskMapper.toDTO(subtaskRepository.save(subtask));
    }
}