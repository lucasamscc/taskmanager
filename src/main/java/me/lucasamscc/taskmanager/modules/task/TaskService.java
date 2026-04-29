package me.lucasamscc.taskmanager.modules.task;

import lombok.RequiredArgsConstructor;
import me.lucasamscc.taskmanager.core.domain.TaskStatus;
import me.lucasamscc.taskmanager.core.exception.BusinessRuleException;
import me.lucasamscc.taskmanager.core.exception.ResourceNotFoundException;
import me.lucasamscc.taskmanager.modules.task.subtask.SubtaskRepository;
import me.lucasamscc.taskmanager.modules.task.dto.CreateTaskDTO;
import me.lucasamscc.taskmanager.modules.task.dto.TaskResponseDTO;
import me.lucasamscc.taskmanager.modules.task.dto.UpdateTaskStatusDTO;
import me.lucasamscc.taskmanager.modules.user.User;
import me.lucasamscc.taskmanager.modules.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResponseDTO create(CreateTaskDTO dto) {
        User user = userService.getUserEntityById(dto.userId());

        Task task = Task.builder()
                .title(dto.title())
                .description(dto.description())
                .status(TaskStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> findAll(TaskStatus status, Pageable pageable) {
        Page<Task> tasks = (status != null)
                ? taskRepository.findByStatus(status, pageable)
                : taskRepository.findAll(pageable);

        return tasks.map(taskMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO findById(UUID id) {
        Task task = getTaskEntity(id);
        return taskMapper.toDTO(task);
    }

    private Task getTaskEntity(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Transactional
    public TaskResponseDTO updateStatus(UUID taskId, UpdateTaskStatusDTO dto) {
        Task task = getTaskEntity(taskId);

        TaskStatus newStatus = dto.status();
        task.setStatus(newStatus);

        if (newStatus == TaskStatus.COMPLETED) {

            boolean hasPendingSubtasks =
                    subtaskRepository.existsByTaskIdAndStatusNot(taskId, TaskStatus.COMPLETED);

            if (hasPendingSubtasks) {
                throw new BusinessRuleException("Cannot complete task with pending subtasks");
            }
            task.setCompletedAt(LocalDateTime.now());
        } else {
            task.setCompletedAt(null);
        }

        return taskMapper.toDTO(taskRepository.save(task));
    }
}