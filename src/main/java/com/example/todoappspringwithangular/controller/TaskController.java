package com.example.todoappspringwithangular.controller;

import com.example.todoappspringwithangular.entity.Task;
import com.example.todoappspringwithangular.TaskService;
import com.example.todoappspringwithangular.entity.TaskName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task> fetchTasks(){
        return taskService.getTasks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody TaskName taskName){
        return taskService.addTask(new Task(taskName.getName(),false));
    }

}
