package com.energy.advisory_service.adapter;

import org.springframework.stereotype.Component;

@Component
public class CarbonServiceAdapter {

    public double calculateCarbonEmission(double totalKWh) {
        double emissionFactor = 0.21;
        return totalKWh * emissionFactor;
    }
}