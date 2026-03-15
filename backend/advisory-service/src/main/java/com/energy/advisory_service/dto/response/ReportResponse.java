package com.energy.advisory_service.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public class ReportResponse {

    private String reportId;
    private OffsetDateTime generatedAt;
    private String reportVersion;
    private ReportSummaryMetricsResponse summaryMetrics;
    private List<RecommendationResponse> recommendations;
    private List<ContextNoteResponse> contextNotes;
    private List<DataCompletenessNoteResponse> dataCompletenessNotes;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public OffsetDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(OffsetDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getReportVersion() {
        return reportVersion;
    }

    public void setReportVersion(String reportVersion) {
        this.reportVersion = reportVersion;
    }

    public ReportSummaryMetricsResponse getSummaryMetrics() {
        return summaryMetrics;
    }

    public void setSummaryMetrics(ReportSummaryMetricsResponse summaryMetrics) {
        this.summaryMetrics = summaryMetrics;
    }

    public List<RecommendationResponse> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationResponse> recommendations) {
        this.recommendations = recommendations;
    }

    public List<ContextNoteResponse> getContextNotes() {
        return contextNotes;
    }

    public void setContextNotes(List<ContextNoteResponse> contextNotes) {
        this.contextNotes = contextNotes;
    }

    public List<DataCompletenessNoteResponse> getDataCompletenessNotes() {
        return dataCompletenessNotes;
    }

    public void setDataCompletenessNotes(List<DataCompletenessNoteResponse> dataCompletenessNotes) {
        this.dataCompletenessNotes = dataCompletenessNotes;
    }
}