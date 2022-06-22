package com.example.todoappspringwithangular;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String fetchTest(){
        return "test";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task> fetchTasks(){
        return taskService.getTasks();
    }

}
