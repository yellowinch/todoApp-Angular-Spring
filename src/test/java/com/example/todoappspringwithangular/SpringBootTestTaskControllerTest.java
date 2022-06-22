package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureJsonTesters
class SpringBootTestTaskControllerTest {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private JacksonTester<List<Task>> json;
    @Test
    void should_return_empty_task_list () {
        final var responseEntity = restTemplate.getForEntity("/tasks", List.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(responseEntity.getBody()).isEmpty();
    }

    @Test
    void should_return_all_tasks_when_request_get_task_given_repo_is_not_empty() throws IOException {
        final List<Task> tasks = List.of(new Task("task 01", false),
                new Task("task 02", true));
        taskRepository.saveAll(tasks);
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity("/tasks", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        final var fetchedTasks = responseEntity.getBody();
        assertThat(json.parseObject(fetchedTasks)).isEqualTo(tasks);
    }
}
