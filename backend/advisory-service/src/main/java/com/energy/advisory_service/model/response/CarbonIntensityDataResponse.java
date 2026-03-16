package com.energy.advisory_service.model.response;

public class CarbonIntensityDataResponse {

    private Integer averageCarbonIntensity;
    private String unit;

    public Integer getAverageCarbonIntensity() {
        return averageCarbonIntensity;
    }

    public void setAverageCarbonIntensity(Integer averageCarbonIntensity) {
        this.averageCarbonIntensity = averageCarbonIntensity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}