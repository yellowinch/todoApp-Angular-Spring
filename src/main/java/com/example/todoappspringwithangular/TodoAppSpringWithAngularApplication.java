package com.example.todoappspringwithangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodoAppSpringWithAngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoAppSpringWithAngularApplication.class, args);
    }

}
