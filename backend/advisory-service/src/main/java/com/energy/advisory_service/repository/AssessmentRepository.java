package com.energy.advisory_service.repository;

import com.energy.advisory_service.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    Optional<Assessment> findByAssessmentId(String assessmentId);
}