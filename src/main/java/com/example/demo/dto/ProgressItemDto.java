package com.example.demo.dto;

import java.util.UUID;

public record ProgressItemDto(
        UUID dateId,
        String date,                 // "2025-12-15"
        boolean isAvailable,
        Boolean isPassed,            // null / true / false
        Boolean isPassedSuccessed,   // null / true / false (опечатка в БД, но оставляем)
        UUID couponEmissionId
) {}