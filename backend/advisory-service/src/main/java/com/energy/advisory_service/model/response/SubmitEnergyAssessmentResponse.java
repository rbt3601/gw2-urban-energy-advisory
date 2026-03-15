package com.energy.advisory_service.model.response;

import com.energy.advisory_service.enums.AssessmentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitEnergyAssessmentResponse {

    private String assessmentId;
    private String startedAt;
    private String completedAt;
    private AssessmentStatus assessmentStatus;
    private DatasetWindow energyConsumptionData;
    private DatasetWindow weatherContextData;
    private CarbonDatasetWindow carbonIntensityData;
    private String errorMessage;

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public AssessmentStatus getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(AssessmentStatus assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public DatasetWindow getEnergyConsumptionData() {
        return energyConsumptionData;
    }

    public void setEnergyConsumptionData(DatasetWindow energyConsumptionData) {
        this.energyConsumptionData = energyConsumptionData;
    }

    public DatasetWindow getWeatherContextData() {
        return weatherContextData;
    }

    public void setWeatherContextData(DatasetWindow weatherContextData) {
        this.weatherContextData = weatherContextData;
    }

    public CarbonDatasetWindow getCarbonIntensityData() {
        return carbonIntensityData;
    }

    public void setCarbonIntensityData(CarbonDatasetWindow carbonIntensityData) {
        this.carbonIntensityData = carbonIntensityData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static class DatasetWindow {
        private String sourceName;
        private String dataFrom;
        private String dataTo;

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public String getDataFrom() {
            return dataFrom;
        }

        public void setDataFrom(String dataFrom) {
            this.dataFrom = dataFrom;
        }

        public String getDataTo() {
            return dataTo;
        }

        public void setDataTo(String dataTo) {
            this.dataTo = dataTo;
        }
    }

    public static class CarbonDatasetWindow extends DatasetWindow {
        private String region;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }
}
