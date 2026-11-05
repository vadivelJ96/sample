package com.example.sample.dto;

import com.example.sample.entity.ExamRegistration;

public record ExamRegistrationResponseDto(
        Long id,
        String name,
        int examStatus,
        Boolean isActive,
        Long userId,
        String userName,
        String userEmail
) {
    public static ExamRegistrationResponseDto fromEntity(ExamRegistration reg) {
        if (reg == null) return null;
        return new ExamRegistrationResponseDto(
                reg.getId(),
                reg.getName(),
                reg.getExamStatus(),
                reg.getIsActive(),
                reg.getUser().getId(),
                reg.getUser().getName(),
                reg.getUser().getEmail()
        );
    }

    public static ExamRegistrationResponseDto fromProjection(ExamRegistrationProjection p) {
        if (p == null) return null;
        return new ExamRegistrationResponseDto(
                p.getId(),
                p.getName(),
                p.getExamStatus() != null ? p.getExamStatus() : 1,
                p.getIsActive(),
                p.getUserId(),
                p.getUserName(),
                p.getUserEmail()
        );
    }
}
