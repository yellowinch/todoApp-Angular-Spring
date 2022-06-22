package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public Task addTask(Task newTask) {
     return taskRepository.save(newTask);
    }
}
