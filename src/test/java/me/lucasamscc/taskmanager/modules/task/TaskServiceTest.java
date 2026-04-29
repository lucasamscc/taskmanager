package me.lucasamscc.taskmanager.modules.task;

import me.lucasamscc.taskmanager.core.domain.TaskStatus;
import me.lucasamscc.taskmanager.core.exception.BusinessRuleException;
import me.lucasamscc.taskmanager.core.exception.ResourceNotFoundException;
import me.lucasamscc.taskmanager.modules.task.dto.TaskResponseDTO;
import me.lucasamscc.taskmanager.modules.task.dto.UpdateTaskStatusDTO;
import me.lucasamscc.taskmanager.modules.task.subtask.SubtaskRepository;
import me.lucasamscc.taskmanager.modules.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubtaskRepository subtaskRepository;

    @Mock
    private UserService userService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    @DisplayName("Should throw an exception when attempting to complete a task with pending subtasks.")
    void shouldThrowExceptionWhenCompletingTaskWithPendingSubtasks() {
        UUID taskId = UUID.randomUUID();
        Task task = Task.builder().id(taskId).status(TaskStatus.PENDING).build();
        UpdateTaskStatusDTO dto = new UpdateTaskStatusDTO(TaskStatus.COMPLETED);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        when(subtaskRepository.existsByTaskIdAndStatusNot(taskId, TaskStatus.COMPLETED)).thenReturn(true);

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            taskService.updateStatus(taskId, dto);
        });

        assertEquals("Cannot complete task with pending subtasks", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should successfully complete the task when there are no pending subtasks.")
    void shouldCompleteTaskSuccessfully() {
        UUID taskId = UUID.randomUUID();
        Task task = Task.builder().id(taskId).status(TaskStatus.PENDING).build();
        UpdateTaskStatusDTO dto = new UpdateTaskStatusDTO(TaskStatus.COMPLETED);

        TaskResponseDTO expectedResponse = new TaskResponseDTO(taskId, "Test", "Test", TaskStatus.COMPLETED, null, null, UUID.randomUUID());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        when(subtaskRepository.existsByTaskIdAndStatusNot(taskId, TaskStatus.COMPLETED)).thenReturn(false);

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDTO(any(Task.class))).thenReturn(expectedResponse);

        TaskResponseDTO result = taskService.updateStatus(taskId, dto);

        assertNotNull(result);
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
        assertNotNull(task.getCompletedAt());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("Should throw a ResourceNotFoundException when the requested task does not exist.")
    void shouldThrowExceptionWhenTaskNotFound() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.findById(taskId);
        });
    }

    @Test
    @DisplayName("Should return a page of tasks when queried")
    void shouldReturnPageOfTasks() {
        UUID taskId = UUID.randomUUID();
        Task task = Task.builder().id(taskId).title("Test").status(TaskStatus.PENDING).build();
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test", "Desc", TaskStatus.PENDING, null, null, UUID.randomUUID());

        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(pageable)).thenReturn(taskPage);
        when(taskMapper.toDTO(task)).thenReturn(dto);

        Page<TaskResponseDTO> result = taskService.findAll(null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(taskRepository, times(1)).findAll(pageable);
    }
}