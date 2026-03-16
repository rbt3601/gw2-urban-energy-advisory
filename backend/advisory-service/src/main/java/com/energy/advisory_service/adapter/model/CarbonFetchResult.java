package com.energy.advisory_service.adapter.model;

public class CarbonFetchResult {

    private final boolean available;
    private final double estimatedKgCO2;
    private final String region;

    public CarbonFetchResult(boolean available, double estimatedKgCO2, String region) {
        this.available = available;
        this.estimatedKgCO2 = estimatedKgCO2;
        this.region = region;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getEstimatedKgCO2() {
        return estimatedKgCO2;
    }

    public String getRegion() {
        return region;
    }
}