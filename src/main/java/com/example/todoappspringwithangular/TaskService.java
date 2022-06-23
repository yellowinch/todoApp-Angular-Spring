package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.dto.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    @Transactional
    public Task addTask(Task newTask) {
     return taskRepository.save(newTask);
    }
    @Transactional
    public Task modifyTask(Long id,Task modifiedTask) {
        final LocalDateTime createdTime = findTaskById(id).getCreatedTime();
        return taskRepository.update(id,modifiedTask.getName(),modifiedTask.getCompleted(),createdTime);
    }
    @Transactional
    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task Not Found"));
    }
}
