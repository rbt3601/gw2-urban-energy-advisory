package com.energy.advisory_service.model.response;

import java.time.OffsetDateTime;
import java.util.List;

public class AdvisoryReportResponse {

    private String reportId;
    private OffsetDateTime generatedAt;
    private String reportVersion;
    private SummaryMetrics summaryMetrics;
    private List<Recommendation> recommendations;
    private List<ContextNote> contextNotes;
    private List<DataCompletenessNote> dataCompletenessNotes;

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

    public SummaryMetrics getSummaryMetrics() {
        return summaryMetrics;
    }

    public void setSummaryMetrics(SummaryMetrics summaryMetrics) {
        this.summaryMetrics = summaryMetrics;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public List<ContextNote> getContextNotes() {
        return contextNotes;
    }

    public void setContextNotes(List<ContextNote> contextNotes) {
        this.contextNotes = contextNotes;
    }

    public List<DataCompletenessNote> getDataCompletenessNotes() {
        return dataCompletenessNotes;
    }

    public void setDataCompletenessNotes(List<DataCompletenessNote> dataCompletenessNotes) {
        this.dataCompletenessNotes = dataCompletenessNotes;
    }

    public static class SummaryMetrics {
        private double totalKWh;
        private double peakKWh;
        private OffsetDateTime peakPeriodStart;
        private OffsetDateTime peakPeriodEnd;
        private double estimatedKgCO2;

        public double getTotalKWh() {
            return totalKWh;
        }

        public void setTotalKWh(double totalKWh) {
            this.totalKWh = totalKWh;
        }

        public double getPeakKWh() {
            return peakKWh;
        }

        public void setPeakKWh(double peakKWh) {
            this.peakKWh = peakKWh;
        }

        public OffsetDateTime getPeakPeriodStart() {
            return peakPeriodStart;
        }

        public void setPeakPeriodStart(OffsetDateTime peakPeriodStart) {
            this.peakPeriodStart = peakPeriodStart;
        }

        public OffsetDateTime getPeakPeriodEnd() {
            return peakPeriodEnd;
        }

        public void setPeakPeriodEnd(OffsetDateTime peakPeriodEnd) {
            this.peakPeriodEnd = peakPeriodEnd;
        }

        public double getEstimatedKgCO2() {
            return estimatedKgCO2;
        }

        public void setEstimatedKgCO2(double estimatedKgCO2) {
            this.estimatedKgCO2 = estimatedKgCO2;
        }
    }

    public static class Recommendation {
        private String recommendationId;
        private String category;
        private String actionText;
        private String expectedImpact;
        private String priority;

        public String getRecommendationId() {
            return recommendationId;
        }

        public void setRecommendationId(String recommendationId) {
            this.recommendationId = recommendationId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getActionText() {
            return actionText;
        }

        public void setActionText(String actionText) {
            this.actionText = actionText;
        }

        public String getExpectedImpact() {
            return expectedImpact;
        }

        public void setExpectedImpact(String expectedImpact) {
            this.expectedImpact = expectedImpact;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }
    }

    public static class ContextNote {
        private String noteType;
        private String message;

        public String getNoteType() {
            return noteType;
        }

        public void setNoteType(String noteType) {
            this.noteType = noteType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class DataCompletenessNote {
        private String missingSource;
        private String severity;
        private String message;

        public String getMissingSource() {
            return missingSource;
        }

        public void setMissingSource(String missingSource) {
            this.missingSource = missingSource;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}