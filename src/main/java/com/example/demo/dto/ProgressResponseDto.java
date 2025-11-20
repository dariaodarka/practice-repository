package com.example.demo.dto;

import java.util.List;

public record ProgressResponseDto(
        String currentDatetime,     // "2025-11-20 14:30:25"
        List<ProgressItemDto> progress
) {}