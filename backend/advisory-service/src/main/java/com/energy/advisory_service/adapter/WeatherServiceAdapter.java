package com.energy.advisory_service.adapter;
import com.energy.advisory_service.model.request.AssessmentRequest;
import org.springframework.stereotype.Component;

@Component
public class WeatherServiceAdapter {
    public String getWeatherSummary(AssessmentRequest request) {
        return "Cold weather increased heating usage during the assessment period.";
    }
}