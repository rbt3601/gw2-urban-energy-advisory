package com.energy.advisory_service.entity;

import com.energy.advisory_service.enums.AssessmentStatus;
import com.energy.advisory_service.enums.Granularity;
import com.energy.advisory_service.enums.PropertyType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String assessmentId;

    @Column(nullable = false)
    private String requestId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String propertyId;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Granularity granularity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentStatus assessmentStatus;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    @Column(length = 2000)
    private String energyConsumptionData;

    @Column(length = 2000)
    private String weatherContextData;

    @Column(length = 2000)
    private String carbonIntensityData;

    @Column(length = 2000)
    private String errorMessage;

    @Column(length = 2000)
    private String dataCompletenessNotes;

    public Long getId() {
        return id;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public void setGranularity(Granularity granularity) {
        this.granularity = granularity;
    }

    public AssessmentStatus getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(AssessmentStatus assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getEnergyConsumptionData() {
        return energyConsumptionData;
    }

    public void setEnergyConsumptionData(String energyConsumptionData) {
        this.energyConsumptionData = energyConsumptionData;
    }

    public String getWeatherContextData() {
        return weatherContextData;
    }

    public void setWeatherContextData(String weatherContextData) {
        this.weatherContextData = weatherContextData;
    }

    public String getCarbonIntensityData() {
        return carbonIntensityData;
    }

    public void setCarbonIntensityData(String carbonIntensityData) {
        this.carbonIntensityData = carbonIntensityData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDataCompletenessNotes() {
        return dataCompletenessNotes;
    }

    public void setDataCompletenessNotes(String dataCompletenessNotes) {
        this.dataCompletenessNotes = dataCompletenessNotes;
    }
}