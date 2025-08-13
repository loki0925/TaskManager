package com.shreyash.taskmanager.dto;

import com.shreyash.taskmanager.enums.Priority;
import com.shreyash.taskmanager.enums.TaskStatus;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class TaskRequestDto {
    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private Priority priority;

    @FutureOrPresent
    private LocalDate dueDate;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
