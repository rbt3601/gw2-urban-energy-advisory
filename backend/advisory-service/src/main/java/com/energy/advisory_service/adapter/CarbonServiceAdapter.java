package com.energy.advisory_service.adapter;

import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CarbonServiceAdapter {

    private static final double FALLBACK_EMISSION_FACTOR = 0.21;

    public CarbonResult getCarbonIntensityContext(SubmitEnergyAssessmentRequest request, double totalKWh) {
        String location = request.getProperty().getLocation().toLowerCase(Locale.ROOT);
        if (location.contains("no_carbon")) {
            return null;
        }

        double factor = location.contains("green") ? 0.12 : 0.21;
        CarbonResult result = new CarbonResult();
        result.setSourceName("MockCarbonIntensityAdapter");
        result.setRegion(request.getProperty().getLocation());
        result.setDataFrom(request.getTimeWindow().getStartDateTime());
        result.setDataTo(request.getTimeWindow().getEndDateTime());
        result.setEmissionFactor(factor);
        result.setEstimatedKgCO2(round(totalKWh * factor));
        result.setUsedFallback(false);
        return result;
    }

    public double calculateFallbackCarbonEmission(double totalKWh) {
        return round(totalKWh * FALLBACK_EMISSION_FACTOR);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static class CarbonResult {
        private String sourceName;
        private String region;
        private String dataFrom;
        private String dataTo;
        private double emissionFactor;
        private double estimatedKgCO2;
        private boolean usedFallback;

        public String getSourceName() { return sourceName; }
        public void setSourceName(String sourceName) { this.sourceName = sourceName; }
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        public String getDataFrom() { return dataFrom; }
        public void setDataFrom(String dataFrom) { this.dataFrom = dataFrom; }
        public String getDataTo() { return dataTo; }
        public void setDataTo(String dataTo) { this.dataTo = dataTo; }
        public double getEmissionFactor() { return emissionFactor; }
        public void setEmissionFactor(double emissionFactor) { this.emissionFactor = emissionFactor; }
        public double getEstimatedKgCO2() { return estimatedKgCO2; }
        public void setEstimatedKgCO2(double estimatedKgCO2) { this.estimatedKgCO2 = estimatedKgCO2; }
        public boolean isUsedFallback() { return usedFallback; }
        public void setUsedFallback(boolean usedFallback) { this.usedFallback = usedFallback; }
    }
}
