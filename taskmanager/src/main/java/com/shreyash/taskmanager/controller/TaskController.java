package com.shreyash.taskmanager.controller;

import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.enums.Priority;
import com.shreyash.taskmanager.enums.TaskStatus;
import com.shreyash.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/apitasks")
@Validated
public class TaskController {
    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponseDto> create(@Valid @RequestBody TaskRequestDto request) {
        TaskResponseDto created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/allTasks")
    public ResponseEntity<TaskResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("list")
    public ResponseEntity<Page<TaskResponseDto>> list(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) String dueBefore,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        String[] sortParts = sort.split(",");
        Sort.Direction dir = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortParts[0]));
        LocalDate due = (dueBefore != null && !dueBefore.isEmpty()) ? LocalDate.parse(dueBefore) : null;
        return ResponseEntity.ok(service.list(status, priority, due, pageable));
    }

    @PutMapping("/update")
    public ResponseEntity<TaskResponseDto> update(@PathVariable Long id, @Valid @RequestBody TaskRequestDto request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
