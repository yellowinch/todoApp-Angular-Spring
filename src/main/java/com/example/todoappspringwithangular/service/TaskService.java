package com.example.todoappspringwithangular.service;

import com.example.todoappspringwithangular.repository.TaskRepository;
import com.example.todoappspringwithangular.dto.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
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
    public void modifyTask(Long id,Task modifiedTask) {
         findTaskById(id);
        taskRepository.update(modifiedTask.getName(),modifiedTask.getCompleted(),id);
    }
    @Transactional
    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task Not Found"));
    }
    @Transactional
    public void deleteTaskById(Long id) {
         findTaskById(id);
         taskRepository.deleteById(id);
    }
}
