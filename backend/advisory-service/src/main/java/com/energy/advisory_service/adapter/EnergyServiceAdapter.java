package com.energy.advisory_service.adapter;

import com.energy.advisory_service.model.request.AssessmentRequest;
import com.energy.advisory_service.model.request.SubmitAssessmentRequest;
import org.springframework.stereotype.Component;

@Component
public class EnergyServiceAdapter {

    public double getTotalEnergyUsage(SubmitAssessmentRequest request) {
        return 430.5;
    }

    public double getPeakEnergyUsage(SubmitAssessmentRequest request) {
        return 13.2;
    }

    public double getTotalEnergyUsage(AssessmentRequest request) {
        return 430.5;
    }

    public double getPeakEnergyUsage(AssessmentRequest request) {
        return 13.2;
    }
}