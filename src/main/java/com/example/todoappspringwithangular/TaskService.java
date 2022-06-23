package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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

    public Task modifyTask(Long id,Task modifiedTask) {
        final LocalDateTime createdTime = findTaskById(id).getCreatedTime();
        return taskRepository.update(id,modifiedTask.getName(),modifiedTask.getCompleted(),createdTime);
    }
    Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task Not Found"));
    }
}
