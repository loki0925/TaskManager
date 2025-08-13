package com.shreyash.taskmanager.controller;

import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.enums.Priority;
import com.shreyash.taskmanager.enums.TaskStatus;
import com.shreyash.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void createTask_Valid_Returns201() throws Exception {
        TaskRequestDto req = new TaskRequestDto();
        req.setTitle("Complete Spring Boot Assignment");
        req.setDescription("Build a task management API");
        req.setStatus(TaskStatus.PENDING);
        req.setPriority(Priority.HIGH);
        req.setDueDate(LocalDate.now().plusDays(1));

        Mockito.when(taskService.create(Mockito.any())).thenAnswer(inv -> {
            TaskRequestDto r = inv.getArgument(0, TaskRequestDto.class);
            TaskResponseDto resp = new com.shreyash.taskmanager.dto.TaskResponseDto();
            resp.setId(1L);
            resp.setTitle(r.getTitle());
            resp.setDescription(r.getDescription());
            resp.setStatus(r.getStatus());
            resp.setPriority(r.getPriority());
            resp.setDueDate(r.getDueDate());
            return resp;
        });

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createTask_Invalid_Returns400() throws Exception {
        String json = "{\"title\":\"Hi\",\"description\":\"\",\"status\":\"INVALID_STATUS\"}";
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTask_NotFound_Returns404() throws Exception {
        Mockito.when(taskService.getById(999L)).thenThrow(new com.shreyash.taskmanager.exception.TaskNotFoundException(999L));
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound());
    }
}
