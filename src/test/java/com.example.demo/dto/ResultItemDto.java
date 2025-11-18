package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ResultItemDto {
    private UUID id;
    private String name;
    private List<String> winners;
}