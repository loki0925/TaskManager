package com.shreyash.taskmanager.service;

import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.entity.Task;
import com.shreyash.taskmanager.enums.Priority;
import com.shreyash.taskmanager.enums.TaskStatus;
import com.shreyash.taskmanager.exception.TaskNotFoundException;
import com.shreyash.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    private TaskResponseDto toDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        return dto;
    }

    private void apply(Task task, TaskRequestDto request) {
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
    }

    @Override
    public TaskResponseDto create(TaskRequestDto request) {
        Task task = new Task();
        apply(task, request);
        Task saved = repository.save(task);
        return toDto(saved);
    }

    @Override
    public TaskResponseDto getById(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return toDto(task);
    }

    @Override
    public Page<TaskResponseDto> list(TaskStatus status, Priority priority, LocalDate dueBefore, Pageable pageable) {
        Page<Task> page;
        if (status != null) {
            page = repository.findByStatus(status, pageable);
        } else if (priority != null) {
            page = repository.findByPriority(priority, pageable);
        } else if (dueBefore != null) {
            page = repository.findByDueDateBefore(dueBefore, pageable);
        } else {
            page = repository.findAll(pageable);
        }
        return page.map(this::toDto);
    }

    @Override
    public TaskResponseDto update(Long id, TaskRequestDto request) {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        apply(task, request);
        Task saved = repository.save(task);
        return toDto(saved);
    }

    @Override
    public void delete(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        repository.delete(task);
    }
}
