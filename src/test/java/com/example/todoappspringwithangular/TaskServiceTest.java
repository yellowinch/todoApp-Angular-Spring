package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.entity.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

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
}