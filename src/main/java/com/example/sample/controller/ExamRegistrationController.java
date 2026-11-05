package com.example.sample.controller;

import com.example.sample.dto.ExamRegistrationRequestDto;
import com.example.sample.dto.ExamRegistrationResponseDto;
import com.example.sample.service.ExamRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exam-registrations")
public class ExamRegistrationController {

    private final ExamRegistrationService examRegistrationService;

    public ExamRegistrationController(ExamRegistrationService examRegistrationService) {
        this.examRegistrationService = examRegistrationService;
    }

    @PostMapping
    public ResponseEntity<ExamRegistrationResponseDto> registerExam(@Valid @RequestBody ExamRegistrationRequestDto request) {
        return new ResponseEntity<>(examRegistrationService.registerExam(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExamRegistrationResponseDto>> getAllRegistrations() {
        return ResponseEntity.ok(examRegistrationService.getAllRegistrations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamRegistrationResponseDto> getRegistrationById(@PathVariable Long id) {
        return ResponseEntity.ok(examRegistrationService.getRegistrationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamRegistrationResponseDto> updateRegistration(
            @PathVariable Long id,
            @Valid @RequestBody ExamRegistrationRequestDto request) {
        return ResponseEntity.ok(examRegistrationService.updateRegistration(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExamRegistrationResponseDto> cancelRegistration(@PathVariable Long id) {
        return ResponseEntity.ok(examRegistrationService.cancelRegistration(id));
    }
}
