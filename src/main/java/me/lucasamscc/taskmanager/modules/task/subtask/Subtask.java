package me.lucasamscc.taskmanager.modules.task.subtask;

import jakarta.persistence.*;
import lombok.*;
import me.lucasamscc.taskmanager.core.domain.TaskStatus;
import me.lucasamscc.taskmanager.modules.task.Task;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "subtasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
