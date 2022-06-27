package com.example.todoappspringwithangular.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    @NotNull
    private String name;
    @NotNull
    private Boolean completed;
}
