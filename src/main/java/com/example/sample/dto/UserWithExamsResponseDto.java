package com.example.sample.dto;

import java.util.List;

public record UserWithExamsResponseDto(
        Long userId,
        String userName,
        String userEmail,
        List<RegisteredExamDto> registeredExams
) {
    public record RegisteredExamDto(
            Long registrationId,
            String examName,
            int examStatus,
            Boolean isActive
    ) {
    }
}

