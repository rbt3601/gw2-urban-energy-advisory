package com.energy.advisory_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String reportId;

    @Column(nullable = false)
    private String assessmentId;

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    @Column(nullable = false)
    private String reportVersion;

    @Column(length = 2000)
    private String summaryMetrics;

    @Column(length = 2000)
    private String recommendations;

    @Column(length = 2000)
    private String contextNotes;

    @Column(length = 2000)
    private String dataCompletenessNotes;

    public Long getId() {
        return id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getReportVersion() {
        return reportVersion;
    }

    public void setReportVersion(String reportVersion) {
        this.reportVersion = reportVersion;
    }

    public String getSummaryMetrics() {
        return summaryMetrics;
    }

    public void setSummaryMetrics(String summaryMetrics) {
        this.summaryMetrics = summaryMetrics;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getContextNotes() {
        return contextNotes;
    }

    public void setContextNotes(String contextNotes) {
        this.contextNotes = contextNotes;
    }

    public String getDataCompletenessNotes() {
        return dataCompletenessNotes;
    }

    public void setDataCompletenessNotes(String dataCompletenessNotes) {
        this.dataCompletenessNotes = dataCompletenessNotes;
    }
}