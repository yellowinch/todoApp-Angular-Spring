package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void should_return_multiple_tasks() {
        entityManager.persist(new Task("task 01", true));
        entityManager.persist(new Task("task 02", false));
        final var foundTasks = taskRepository.findAll();
        assertThat(foundTasks).hasSize(2);

    }
}