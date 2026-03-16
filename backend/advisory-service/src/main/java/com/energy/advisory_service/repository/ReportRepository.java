package com.energy.advisory_service.repository;

import com.energy.advisory_service.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByReportId(String reportId);

    Optional<Report> findByAssessmentId(String assessmentId);
}