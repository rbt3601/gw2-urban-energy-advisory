package com.energy.advisory_service.adapter;

import com.energy.advisory_service.adapter.model.EnergyFetchResult;
import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import org.springframework.stereotype.Component;

@Component
public class EnergyServiceAdapter {

    public EnergyFetchResult fetchEnergyData(SubmitEnergyAssessmentRequest request) {
        String requestId = request.getRequestId() == null ? "" : request.getRequestId().toUpperCase();
        String testMode = request.getTestMode() == null ? "" : request.getTestMode().trim().toLowerCase();

        if ("energy_fail".equals(testMode) || requestId.contains("FAIL_ENERGY")) {
            return new EnergyFetchResult(false, 0.0, 0.0, "Energy service unavailable");
        }

        return new EnergyFetchResult(true, 430.5, 13.2, "Smart meter data fetched successfully");
    }
}