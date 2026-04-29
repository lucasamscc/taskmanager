package me.lucasamscc.taskmanager.modules.task.subtask;

import me.lucasamscc.taskmanager.core.domain.TaskStatus;
import me.lucasamscc.taskmanager.modules.task.TaskRepository;
import me.lucasamscc.taskmanager.modules.task.subtask.dto.UpdateSubtaskStatusDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubtaskServiceTest {

    @InjectMocks
    private SubtaskService subtaskService;

    @Mock
    private SubtaskRepository subtaskRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubtaskMapper subtaskMapper;

    @Test
    @DisplayName("Should set completedAt when completing a subtask.")
    void shouldSetCompletedAtWhenStatusIsCompleted() {
        UUID subtaskId = UUID.randomUUID();
        Subtask subtask = Subtask.builder().id(subtaskId).status(TaskStatus.PENDING).build();
        UpdateSubtaskStatusDTO dto = new UpdateSubtaskStatusDTO(TaskStatus.COMPLETED);

        when(subtaskRepository.findById(subtaskId)).thenReturn(Optional.of(subtask));
        when(subtaskRepository.save(any())).thenReturn(subtask);

        subtaskService.updateStatus(subtaskId, dto);

        assertNotNull(subtask.getCompletedAt());
        assertEquals(TaskStatus.COMPLETED, subtask.getStatus());
    }
}