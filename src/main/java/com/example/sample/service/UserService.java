package com.example.sample.service;

import com.example.sample.dto.ExamRegistrationProjection;
import com.example.sample.dto.UserRequestDto;
import com.example.sample.dto.UserResponseDto;
import com.example.sample.dto.UserWithExamsResponseDto;
import com.example.sample.entity.User;
import com.example.sample.repository.ExamRegistrationRepository;
import com.example.sample.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ExamRegistrationRepository examRegistrationRepository;

    public UserService(UserRepository userRepository,
                       ExamRegistrationRepository examRegistrationRepository) {
        this.userRepository = userRepository;
        this.examRegistrationRepository = examRegistrationRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAllUsersNative().stream()
                .map(UserResponseDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        return UserResponseDto.fromEntity(findEntityById(id));
    }

    @Transactional
    public UserResponseDto create(UserRequestDto request) {
        if (userRepository.existsByEmailNative(request.email()) > 0) {
            throw new IllegalArgumentException("User with email already exists: " + request.email());
        }

        User user = new User(request.name(), request.email());
        return UserResponseDto.fromEntity(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto request) {
        User user = findEntityById(id);

        if (!user.getEmail().equalsIgnoreCase(request.email()) && userRepository.existsByEmailNative(request.email()) > 0) {
            throw new IllegalArgumentException("Email already taken: " + request.email());
        }

        user.setName(request.name());
        user.setEmail(request.email());

        return UserResponseDto.fromEntity(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        User user = findEntityById(id);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserWithExamsResponseDto getUserWithRegisteredExams(Long userId) {
        User user = findEntityById(userId);
        List<ExamRegistrationProjection> registrations = examRegistrationRepository.findRegistrationsByUserIdNative(userId);

        List<UserWithExamsResponseDto.RegisteredExamDto> examDtos = registrations.stream()
                .map(er -> new UserWithExamsResponseDto.RegisteredExamDto(
                        er.getId(),
                        er.getName(),
                        er.getExamStatus() != null ? er.getExamStatus() : 1,
                        er.getIsActive()
                ))
                .toList();

        return new UserWithExamsResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                examDtos
        );
    }

    public User findEntityById(Long id) {
        return userRepository.findByIdNative(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }
}
