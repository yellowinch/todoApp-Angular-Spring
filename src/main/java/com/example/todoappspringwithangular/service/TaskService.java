package com.example.todoappspringwithangular.service;

import com.example.todoappspringwithangular.dto.Task;
import com.example.todoappspringwithangular.dto.TaskDto;
import com.example.todoappspringwithangular.exception.NotFoundException;
import com.example.todoappspringwithangular.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task addTask(TaskDto createdTask) {
        return taskRepository.save(Task.builder().name(createdTask.getName()).completed(false).build());
    }

    @Transactional
    public void modifyTask(Long id, TaskDto modifiedTask) {
        if (findTaskById(id) != null) {
            taskRepository.update(modifiedTask.getName(), modifiedTask.getCompleted(), id);
        } else {
            throw new NotFoundException("Task not Found");
        }
    }

    @Transactional
    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteTaskById(Long id) {
        if (findTaskById(id) != null) {
            taskRepository.deleteById(id);
        } else {
            throw new NotFoundException("Task not Found");
        }
        findTaskById(id);
    }
}
