package com.energy.advisory_service.adapter.model;

public class EnergyFetchResult {

    private final boolean available;
    private final double totalKWh;
    private final double peakKWh;
    private final String message;

    public EnergyFetchResult(boolean available, double totalKWh, double peakKWh, String message) {
        this.available = available;
        this.totalKWh = totalKWh;
        this.peakKWh = peakKWh;
        this.message = message;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getTotalKWh() {
        return totalKWh;
    }

    public double getPeakKWh() {
        return peakKWh;
    }

    public String getMessage() {
        return message;
    }
}