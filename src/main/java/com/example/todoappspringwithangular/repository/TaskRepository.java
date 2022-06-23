package com.example.todoappspringwithangular.repository;

import com.example.todoappspringwithangular.dto.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query( value = "update tasks set name=:name, completed=:completed, modified_at=CURRENT_TIMESTAMP where id=:id", nativeQuery = true)
    @Modifying(clearAutomatically=true)

     void update(@Param("name") String name,@Param("completed") Boolean completed,@Param("id") Long id);

}
