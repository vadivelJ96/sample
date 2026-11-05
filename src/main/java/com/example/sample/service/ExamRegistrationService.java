package com.example.sample.service;

import com.example.sample.dto.ExamRegistrationProjection;
import com.example.sample.dto.ExamRegistrationRequestDto;
import com.example.sample.dto.ExamRegistrationResponseDto;
import com.example.sample.entity.ExamRegistration;
import com.example.sample.entity.User;
import com.example.sample.repository.ExamRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamRegistrationService {

    private final ExamRegistrationRepository examRegistrationRepository;
    private final UserService userService;

    public ExamRegistrationService(ExamRegistrationRepository examRegistrationRepository,
                                   UserService userService) {
        this.examRegistrationRepository = examRegistrationRepository;
        this.userService = userService;
    }

    @Transactional
    public ExamRegistrationResponseDto registerExam(ExamRegistrationRequestDto request) {
        User user = userService.findEntityById(request.userId());

        if (examRegistrationRepository.existsByUserIdAndNameNative(user.getId(), request.name()) > 0) {
            throw new IllegalArgumentException("User is already registered for this exam: " + request.name());
        }

        int status = (request.examStatus() != null) ? request.examStatus() : 1;
        ExamRegistration exam = new ExamRegistration(user, request.name(), status);
        ExamRegistration saved = examRegistrationRepository.save(exam);

        return getRegistrationById(saved.getId());
    }

    @Transactional(readOnly = true)
    public List<ExamRegistrationResponseDto> getAllRegistrations() {
        return examRegistrationRepository.findAllRegistrationsNative().stream()
                .map(ExamRegistrationResponseDto::fromProjection)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExamRegistrationResponseDto getRegistrationById(Long id) {
        ExamRegistrationProjection projection = examRegistrationRepository.findRegistrationByIdNative(id)
                .orElseThrow(() -> new IllegalArgumentException("Exam registration not found with id: " + id));
        return ExamRegistrationResponseDto.fromProjection(projection);
    }

    @Transactional
    public ExamRegistrationResponseDto updateRegistration(Long id, ExamRegistrationRequestDto request) {
        ExamRegistration reg = examRegistrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exam registration not found with id: " + id));

        reg.setName(request.name());
        if (request.examStatus() != null) {
            reg.setExamStatus(request.examStatus());
        }

        examRegistrationRepository.save(reg);
        return getRegistrationById(id);
    }

    @Transactional
    public ExamRegistrationResponseDto cancelRegistration(Long id) {
        if (!examRegistrationRepository.existsById(id)) {
            throw new IllegalArgumentException("Exam registration not found with id: " + id);
        }

        examRegistrationRepository.softDeleteByIdNative(id);
        return getRegistrationById(id);
    }
}
