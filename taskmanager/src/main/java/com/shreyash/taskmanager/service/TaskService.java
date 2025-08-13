package com.shreyash.taskmanager.service;

import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.enums.Priority;
import com.shreyash.taskmanager.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TaskService {
    TaskResponseDto create(TaskRequestDto request);
    TaskResponseDto getById(Long id);
    Page<TaskResponseDto> list(TaskStatus status, Priority priority, LocalDate dueBefore, Pageable pageable);
    TaskResponseDto update(Long id, TaskRequestDto request);
    void delete(Long id);
}
