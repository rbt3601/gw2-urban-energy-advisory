package com.energy.advisory_service.model.response;

public class EnergyConsumptionDataResponse {

    private Double averageConsumption;
    private Double peakConsumption;
    private String unit;

    public Double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(Double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }

    public Double getPeakConsumption() {
        return peakConsumption;
    }

    public void setPeakConsumption(Double peakConsumption) {
        this.peakConsumption = peakConsumption;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}