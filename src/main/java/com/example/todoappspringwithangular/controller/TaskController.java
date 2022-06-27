package com.example.todoappspringwithangular.controller;

import com.example.todoappspringwithangular.dto.TaskDto;
import com.example.todoappspringwithangular.dto.Task;
import com.example.todoappspringwithangular.service.TaskService;
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
    public Task addTask(@RequestBody TaskDto task){
        return taskService.addTask(task);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(
            @PathVariable(value = "id")Long id,
            @Valid @RequestBody TaskDto task){
         taskService.modifyTask(id,task);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTask(@PathVariable(value = "id") Long id){
        taskService.deleteTaskById(id);
    }


}
