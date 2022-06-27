package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.dto.Task;
import com.example.todoappspringwithangular.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
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

    @AfterEach
    void clearAllTasks() {
        taskRepository.deleteAll();
    }

    @Test
    void should_return_empty_task_list() {
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

    @Test
    void should_return_created_task_when_add_task_given_valid_request_body() {
        final Task newTask = new Task("task 01", false);
        final ResponseEntity<Task> responseEntity = restTemplate.postForEntity("/tasks", newTask, Task.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final Task createdTask = responseEntity.getBody();
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getName()).isEqualTo(newTask.getName());
        assertThat(createdTask.getCompleted()).isEqualTo(newTask.getCompleted());
    }

    @Test
    void should_updated_task_when_modify_task_given_valid_id_and_request_body() {
        final var task = new Task("task 01", false);
        taskRepository.save(task);
        String url = "/tasks/" + task.getId();
        String reqJsonStr = "{\"name\":\"new task\", \"completed\":true}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(reqJsonStr, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    void should_remove_task_when_delete_task_given_valid_task_id() {
        final var task1 = new Task("task 01", true);
        final var task2 = new Task("task 02", false);
        final var tasks = List.of(task1, task2);
        taskRepository.saveAll(tasks);
        String url = "/tasks/" + task1.getId();
        System.out.println("id" + task1.getId());
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    void should_throw_not_found_exception_message_delete_task_given_valid_task_id() {
        final var task1 = new Task("task 01", true);
        final var task2 = new Task("task 02", false);
        final var tasks = List.of(task1, task2);
        taskRepository.saveAll(tasks);
        String url = "/tasks/11";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).contains("Not Found");
    }

    @Test
    void should_throw_bad_request_exception_when_update_task_given_invalid_request_parameter() {
        final var task = new Task("task 01", false);
        taskRepository.save(task);
        String url = "/tasks/" + task.getId();

        String reqJsonStr = "{\"name1\":\"new task\", \"completed\":true}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(reqJsonStr, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).contains("Bad Request");
    }

    @Test
    void should_throw_not_found_exception_when_update_task_given_invalid_request_parameter() {
        final var task = new Task("task 01", false);
        taskRepository.save(task);
        String url = "/tasks/11";

        String reqJsonStr = "{\"name\":\"new task\", \"completed\":true}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(reqJsonStr, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).contains("Not Found");
    }

}
