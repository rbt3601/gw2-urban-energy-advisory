package com.energy.advisory_service.adapter;

import com.energy.advisory_service.adapter.model.CarbonFetchResult;
import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import org.springframework.stereotype.Component;

@Component
public class CarbonServiceAdapter {

    public CarbonFetchResult fetchCarbonData(SubmitEnergyAssessmentRequest request, double totalKWh) {
        String requestId = request.getRequestId() == null ? "" : request.getRequestId().toUpperCase();
        String testMode = request.getTestMode() == null ? "" : request.getTestMode().trim().toLowerCase();

        if ("carbon_fail".equals(testMode) || requestId.contains("MISS_CARBON")) {
            return new CarbonFetchResult(false, 0.0, request.getProperty().getLocation());
        }

        double emissionFactor = 0.21;
        return new CarbonFetchResult(true, totalKWh * emissionFactor, request.getProperty().getLocation());
    }
}