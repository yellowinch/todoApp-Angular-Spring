package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.dto.Task;
import com.example.todoappspringwithangular.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

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

    @Test
    void should_update_task_when_modify_task_given_valid_id_and_requested_parameters() {
        final Task task = new Task("task 01",false);
        entityManager.persist(task);
        taskRepository.update("new Task",true,task.getId());
        final Task updatedTask = taskRepository.getById(task.getId());
        assertThat(updatedTask).isNotNull()
                .hasFieldOrPropertyWithValue("name","new Task")
                .hasFieldOrPropertyWithValue("completed",true);

    }

    @Test
    void should_remove_task_when_delete_task_given_valid_id() {
        final Task task= new Task("old task", false);
        entityManager.persist(task);
        final List<Task> beforeRemoveTask = taskRepository.findAll();
        assertThat(beforeRemoveTask).isNotNull()
                        .hasSize(1);
        taskRepository.deleteById(task.getId());
        final List<Task> afterRemoveTask = taskRepository.findAll();
        assertThat(afterRemoveTask).isEmpty();
    }
}