package com.example.todoappspringwithangular;

import com.example.todoappspringwithangular.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query(value = "update tasks set name=?2, completed=?3 where id=?1 ", nativeQuery = true)
    @Modifying
    default Task update(Long id, String name, Boolean completed,LocalDateTime createdTime) {
        return new Task(id, name, completed,createdTime,LocalDateTime.now());
    }

}
