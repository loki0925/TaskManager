package com.shreyash.taskmanager.repository;

import com.shreyash.taskmanager.entity.Task;
import com.shreyash.taskmanager.enums.Priority;
import com.shreyash.taskmanager.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    Page<Task> findByPriority(Priority priority, Pageable pageable);
    Page<Task> findByDueDateBefore(LocalDate date, Pageable pageable);
}
