package me.lucasamscc.taskmanager.modules.task.subtask;

import me.lucasamscc.taskmanager.core.domain.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<Subtask, UUID> {

    Page<Subtask> findByTaskId(UUID taskId, Pageable pageable);
    boolean existsByTaskIdAndStatusNot(UUID taskId, TaskStatus status);
}
