package com.energy.advisory_service.service.impl;

import com.energy.advisory_service.dto.request.SubmitEnergyAssessmentRequest;
import com.energy.advisory_service.dto.response.AssessmentResponse;
import com.energy.advisory_service.dto.response.CarbonIntensityDataResponse;
import com.energy.advisory_service.dto.response.EnergyConsumptionDataResponse;
import com.energy.advisory_service.dto.response.WeatherContextDataResponse;
import com.energy.advisory_service.entity.Assessment;
import com.energy.advisory_service.enums.AssessmentStatus;
import com.energy.advisory_service.repository.AssessmentRepository;
import com.energy.advisory_service.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;

    public AssessmentServiceImpl(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    public AssessmentResponse submitAssessment(SubmitEnergyAssessmentRequest request) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentId(UUID.randomUUID().toString());
        assessment.setRequestId(request.getRequestId());
        assessment.setCreatedAt(request.getCreatedAt().toLocalDateTime());

        assessment.setPropertyId(request.getProperty().getPropertyId());
        assessment.setLocation(request.getProperty().getLocation());
        assessment.setPropertyType(request.getProperty().getPropertyType());

        assessment.setStartDateTime(request.getTimeWindow().getStartDateTime().toLocalDateTime());
        assessment.setEndDateTime(request.getTimeWindow().getEndDateTime().toLocalDateTime());
        assessment.setGranularity(request.getTimeWindow().getGranularity());

        assessment.setStartedAt(LocalDateTime.now(ZoneOffset.UTC));

        String requestId = request.getRequestId() == null ? "" : request.getRequestId().toUpperCase();

        boolean failEnergy = requestId.contains("FAIL_ENERGY");
        boolean missWeather = requestId.contains("MISS_WEATHER");
        boolean missCarbon = requestId.contains("MISS_CARBON");

        if (failEnergy) {
            assessment.setAssessmentStatus(AssessmentStatus.FAILED);
            assessment.setEnergyConsumptionData(null);
            assessment.setWeatherContextData(null);
            assessment.setCarbonIntensityData(null);
        } else {
            assessment.setEnergyConsumptionData("Smart meter data fetched successfully");

            if (missWeather) {
                assessment.setWeatherContextData(null);
            } else {
                assessment.setWeatherContextData("Weather context fetched successfully");
            }

            if (missCarbon) {
                assessment.setCarbonIntensityData(null);
            } else {
                assessment.setCarbonIntensityData("Carbon intensity fetched successfully");
            }

            if (missWeather || missCarbon) {
                assessment.setAssessmentStatus(AssessmentStatus.PARTIAL);
            } else {
                assessment.setAssessmentStatus(AssessmentStatus.COMPLETED);
            }
        }

        assessment.setCompletedAt(LocalDateTime.now(ZoneOffset.UTC));

        Assessment saved = assessmentRepository.save(assessment);
        return mapToResponse(saved);
    }

    @Override
    public AssessmentResponse getAssessmentByAssessmentId(String assessmentId) {
        Assessment saved = assessmentRepository.findByAssessmentId(assessmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found"));

        return mapToResponse(saved);
    }

    private AssessmentResponse mapToResponse(Assessment saved) {
        AssessmentResponse response = new AssessmentResponse();
        response.setAssessmentId(saved.getAssessmentId());
        response.setStartedAt(toOffset(saved.getStartedAt()));
        response.setCompletedAt(toOffset(saved.getCompletedAt()));
        response.setAssessmentStatus(saved.getAssessmentStatus());

        if (saved.getEnergyConsumptionData() != null) {
            EnergyConsumptionDataResponse energy = new EnergyConsumptionDataResponse();
            energy.setSourceName("SmartMeterAPI");
            energy.setDataFrom(toOffset(saved.getStartDateTime()));
            energy.setDataTo(toOffset(saved.getEndDateTime()));
            response.setEnergyConsumptionData(energy);
        }

        if (saved.getWeatherContextData() != null) {
            WeatherContextDataResponse weather = new WeatherContextDataResponse();
            weather.setSourceName("WeatherAPI");
            weather.setDataFrom(toOffset(saved.getStartDateTime()));
            weather.setDataTo(toOffset(saved.getEndDateTime()));
            response.setWeatherContextData(weather);
        }

        if (saved.getCarbonIntensityData() != null) {
            CarbonIntensityDataResponse carbon = new CarbonIntensityDataResponse();
            carbon.setSourceName("CarbonAPI");
            carbon.setRegion(saved.getLocation());
            carbon.setDataFrom(toOffset(saved.getStartDateTime()));
            carbon.setDataTo(toOffset(saved.getEndDateTime()));
            response.setCarbonIntensityData(carbon);
        }

        return response;
    }

    private OffsetDateTime toOffset(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC);
    }
}