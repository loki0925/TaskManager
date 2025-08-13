package com.shreyash.taskmanager.repository;

import com.shreyash.taskmanager.entity.Task;
import com.shreyash.taskmanager.enums.Priority;
import com.shreyash.taskmanager.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    private TaskRepository repository;

    @Test
    void saveAndFind() {
        Task t = new Task();
        t.setTitle("Repo test");
        t.setDescription("Desc");
        t.setStatus(TaskStatus.PENDING);
        t.setPriority(Priority.LOW);
        t.setDueDate(LocalDate.now().plusDays(2));
        repository.save(t);

        Page<Task> page = repository.findAll(PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThan(0);
    }
}
