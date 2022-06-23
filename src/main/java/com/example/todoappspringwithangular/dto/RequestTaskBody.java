package com.example.todoappspringwithangular.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestTaskBody {
    private String name;
    private Boolean completed;
}
