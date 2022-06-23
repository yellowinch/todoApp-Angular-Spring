package com.example.todoappspringwithangular.controller;

import com.example.todoappspringwithangular.entity.RequestTaskBody;
import com.example.todoappspringwithangular.entity.Task;
import com.example.todoappspringwithangular.TaskService;
import com.example.todoappspringwithangular.entity.RequestTaskName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Task addTask(@RequestBody RequestTaskName requestTaskName){
        return taskService.addTask(new Task(requestTaskName.getName(),false));
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(
            @PathVariable(value = "id")Long id,
            @Valid @RequestBody RequestTaskBody taskBody){
        return taskService.modifyTask(id,new Task(taskBody.getName(), taskBody.getCompleted()));
    }

}
