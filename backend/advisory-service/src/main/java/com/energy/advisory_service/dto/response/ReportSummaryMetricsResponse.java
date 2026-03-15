package com.energy.advisory_service.dto.response;

import java.time.OffsetDateTime;

public class ReportSummaryMetricsResponse {

    private double totalKWh;
    private double peakKWh;
    private OffsetDateTime peakPeriodStart;
    private OffsetDateTime peakPeriodEnd;
    private double estimatedKgCO2;

    public double getTotalKWh() {
        return totalKWh;
    }

    public void setTotalKWh(double totalKWh) {
        this.totalKWh = totalKWh;
    }

    public double getPeakKWh() {
        return peakKWh;
    }

    public void setPeakKWh(double peakKWh) {
        this.peakKWh = peakKWh;
    }

    public OffsetDateTime getPeakPeriodStart() {
        return peakPeriodStart;
    }

    public void setPeakPeriodStart(OffsetDateTime peakPeriodStart) {
        this.peakPeriodStart = peakPeriodStart;
    }

    public OffsetDateTime getPeakPeriodEnd() {
        return peakPeriodEnd;
    }

    public void setPeakPeriodEnd(OffsetDateTime peakPeriodEnd) {
        this.peakPeriodEnd = peakPeriodEnd;
    }

    public double getEstimatedKgCO2() {
        return estimatedKgCO2;
    }

    public void setEstimatedKgCO2(double estimatedKgCO2) {
        this.estimatedKgCO2 = estimatedKgCO2;
    }
}