package com.energy.advisory_service.adapter;

import com.energy.advisory_service.adapter.model.WeatherFetchResult;
import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import org.springframework.stereotype.Component;

@Component
public class WeatherServiceAdapter {

    public WeatherFetchResult fetchWeatherData(SubmitEnergyAssessmentRequest request) {
        String requestId = request.getRequestId() == null ? "" : request.getRequestId().toUpperCase();
        String testMode = request.getTestMode() == null ? "" : request.getTestMode().trim().toLowerCase();

        if ("weather_fail".equals(testMode) || requestId.contains("MISS_WEATHER")) {
            return new WeatherFetchResult(false, null);
        }

        return new WeatherFetchResult(true, "Cool and cloudy weather influenced heating demand.");
    }
}