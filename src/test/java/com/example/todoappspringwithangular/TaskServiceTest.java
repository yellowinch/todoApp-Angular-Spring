package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.dto.Task;
import com.example.todoappspringwithangular.repository.TaskRepository;
import com.example.todoappspringwithangular.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;
    @Test
    void should_return_empty_list_when_find_tasks_given_repo_is_empty() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        final List<Task> fetchedTasks = taskService.getTasks();
        assertThat(fetchedTasks).isEmpty();
        verify(taskRepository).findAll();
    }

    @Test
    void should_return_all_tasks_when_find_tasks_given_repo_is_not_empty() {
        final List<Task> tasks = List.of(
                new Task("task 01", false),
                new Task("task 02", true));
        when(taskRepository.findAll()).thenReturn(tasks);
        final List<Task> fetchedTasks = taskService.getTasks();
        assertThat(fetchedTasks)
                .hasSize(2)
                .isEqualTo(tasks);
        verify(taskRepository).findAll();
    }

    @Test
    void should_return_created_task_when_add_task_given_valid_request_body() {
        final var newTask = new Task("task 01", false);
        when(taskRepository.save(newTask)).thenReturn(newTask);
        final Task createdTask = taskService.addTask(newTask);
        assertThat(createdTask).isNotNull()
                .hasFieldOrPropertyWithValue("name",newTask.getName())
                .hasFieldOrPropertyWithValue("completed",newTask.getCompleted());
        verify(taskRepository).save(newTask);
    }

    @Test
    void should_return_updated_task_when_modify_task_given_valid_id() {
        final Task oldTask = new Task(1L,"old task", false,LocalDateTime.now(),LocalDateTime.now());
        taskRepository.save(oldTask);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(oldTask));
        taskService.modifyTask(1L, new Task("new Task",true));
        final Task updatedTask = taskService.findTaskById(oldTask.getId());
        assertThat(updatedTask).isNotNull();
        verify(taskRepository).update("new Task",true,1L);
    }


}