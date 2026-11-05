package com.example.sample.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExamRegistrationRequestDto(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotBlank(message = "Exam name is required")
        String name,

        Integer examStatus
) {
}
