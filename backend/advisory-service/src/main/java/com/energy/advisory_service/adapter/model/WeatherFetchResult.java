package com.energy.advisory_service.adapter.model;

public class WeatherFetchResult {

    private final boolean available;
    private final String summary;

    public WeatherFetchResult(boolean available, String summary) {
        this.available = available;
        this.summary = summary;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getSummary() {
        return summary;
    }
}