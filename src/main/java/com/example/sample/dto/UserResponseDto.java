package com.example.sample.dto;

import com.example.sample.entity.User;

public record UserResponseDto(
        Long id,
        String name,
        String email
) {
    public static UserResponseDto fromEntity(User user) {
        if (user == null) return null;
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
