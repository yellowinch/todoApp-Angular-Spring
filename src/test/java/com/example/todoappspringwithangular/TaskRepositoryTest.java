package com.example.todoappspringwithangular;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void should_get_all_tasks() {
        Task taskOne = Task.builder()
                .id(1L)
                .name("task1")
                .completed(false)
                .build();
        Task taskTwo = Task.builder()
                .id(2L)
                .name("task2")
                .completed(false)
                .build();
        taskRepository.save(taskOne);
        taskRepository.save(taskTwo);
        Iterable<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(2);

    }
}