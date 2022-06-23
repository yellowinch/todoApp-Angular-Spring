package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.dto.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@Rollback
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

    @Test
    void should_return_saved_task_successfully_when_add_task_given_requested_parameter() {
        final Task newTask = new Task("task 01",false);
        entityManager.persist(newTask);
        final Task savedTask = taskRepository.save(newTask);
        assertThat(savedTask).isEqualTo(newTask)
                .hasFieldOrPropertyWithValue("name","task 01")
                .hasFieldOrPropertyWithValue("completed",false);
    }
}