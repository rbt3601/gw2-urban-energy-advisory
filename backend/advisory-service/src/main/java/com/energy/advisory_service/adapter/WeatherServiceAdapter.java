package com.energy.advisory_service.adapter;

import com.energy.advisory_service.model.request.AssessmentRequest;
import com.energy.advisory_service.model.request.SubmitAssessmentRequest;
import org.springframework.stereotype.Component;

@Component
public class WeatherServiceAdapter {

    public String getWeatherSummary(SubmitAssessmentRequest request) {
        return "Cold weather increased heating usage during the assessment period.";
    }

    public String getWeatherSummary(AssessmentRequest request) {
        return "Cold weather increased heating usage during the assessment period.";
    }
}