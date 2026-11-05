package com.example.sample.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exam_registration")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(name = "exam_status", nullable = false)
    private int examStatus = 1;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public ExamRegistration(User user, String name, int examStatus) {
        this.user = user;
        this.name = name;
        this.examStatus = examStatus;
        this.isActive = true;
    }
}
