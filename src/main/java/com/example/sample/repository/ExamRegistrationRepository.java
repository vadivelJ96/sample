package com.example.sample.repository;

import com.example.sample.dto.ExamRegistrationProjection;
import com.example.sample.entity.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration, Long> {

    @Query(value = """
        SELECT 
            er.id AS id,
            er.name AS name,
            er.exam_status AS examStatus,
            er.is_active AS isActive,
            u.id AS userId,
            u.name AS userName,
            u.email AS userEmail
        FROM exam_registration er
        INNER JOIN users u ON er.user_id = u.id
        WHERE er.user_id = :userId AND er.is_active = true
    """, nativeQuery = true)
    List<ExamRegistrationProjection> findRegistrationsByUserIdNative(@Param("userId") Long userId);

    @Query(value = """
        SELECT 
            er.id AS id,
            er.name AS name,
            er.exam_status AS examStatus,
            er.is_active AS isActive,
            u.id AS userId,
            u.name AS userName,
            u.email AS userEmail
        FROM exam_registration er
        INNER JOIN users u ON er.user_id = u.id
        WHERE er.id = :id
    """, nativeQuery = true)
    Optional<ExamRegistrationProjection> findRegistrationByIdNative(@Param("id") Long id);

    @Query(value = """
        SELECT 
            er.id AS id,
            er.name AS name,
            er.exam_status AS examStatus,
            er.is_active AS isActive,
            u.id AS userId,
            u.name AS userName,
            u.email AS userEmail
        FROM exam_registration er
        INNER JOIN users u ON er.user_id = u.id
        WHERE er.is_active = true
    """, nativeQuery = true)
    List<ExamRegistrationProjection> findAllRegistrationsNative();

    @Query(value = """
        SELECT COUNT(*) > 0 
        FROM exam_registration 
        WHERE user_id = :userId AND name = :name AND is_active = true
    """, nativeQuery = true)
    int existsByUserIdAndNameNative(@Param("userId") Long userId, @Param("name") String name);

    @Modifying
    @Query(value = "UPDATE exam_registration SET is_active = false WHERE id = :id", nativeQuery = true)
    void softDeleteByIdNative(@Param("id") Long id);
}
