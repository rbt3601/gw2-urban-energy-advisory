package com.energy.advisory_service.adapter;

import com.energy.advisory_service.exception.ApiException;
import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Locale;

@Component
public class EnergyServiceAdapter {

    public EnergyAnalysis getEnergyAnalysis(SubmitEnergyAssessmentRequest request) {
        String location = request.getProperty().getLocation().toLowerCase(Locale.ROOT);
        String propertyId = request.getProperty().getPropertyId().toLowerCase(Locale.ROOT);
        if (location.contains("no_energy") || propertyId.contains("fail-energy")) {
            throw new ApiException(HttpStatus.BAD_GATEWAY,
                    "Critical energy data unavailable from Smart Meter / Utility Consumption Service");
        }

        OffsetDateTime start = OffsetDateTime.parse(request.getTimeWindow().getStartDateTime());
        OffsetDateTime end = OffsetDateTime.parse(request.getTimeWindow().getEndDateTime());
        long hours = Math.max(1L, java.time.Duration.between(start, end).toHours());
        double multiplier = request.getProperty().getPropertyType().name().equals("SMALL_BUILDING") ? 1.7 : 1.0;
        double totalKWh = Math.round((hours * 0.58 * multiplier) * 100.0) / 100.0;
        double peakKWh = Math.round((Math.max(2.5, totalKWh / Math.max(6.0, hours / 4.0))) * 100.0) / 100.0;
        OffsetDateTime peakStart = start.plusHours(Math.min(hours - 1, Math.max(0, hours / 2)));
        OffsetDateTime peakEnd = peakStart.plusHours(1);

        EnergyAnalysis analysis = new EnergyAnalysis();
        analysis.setSourceName("MockSmartMeterAdapter");
        analysis.setDataFrom(start.toString());
        analysis.setDataTo(end.toString());
        analysis.setTotalKWh(totalKWh);
        analysis.setPeakKWh(peakKWh);
        analysis.setPeakPeriodStart(peakStart);
        analysis.setPeakPeriodEnd(peakEnd);
        analysis.setSimulated(true);
        return analysis;
    }

    public static class EnergyAnalysis {
        private String sourceName;
        private String dataFrom;
        private String dataTo;
        private double totalKWh;
        private double peakKWh;
        private OffsetDateTime peakPeriodStart;
        private OffsetDateTime peakPeriodEnd;
        private boolean simulated;

        public String getSourceName() { return sourceName; }
        public void setSourceName(String sourceName) { this.sourceName = sourceName; }
        public String getDataFrom() { return dataFrom; }
        public void setDataFrom(String dataFrom) { this.dataFrom = dataFrom; }
        public String getDataTo() { return dataTo; }
        public void setDataTo(String dataTo) { this.dataTo = dataTo; }
        public double getTotalKWh() { return totalKWh; }
        public void setTotalKWh(double totalKWh) { this.totalKWh = totalKWh; }
        public double getPeakKWh() { return peakKWh; }
        public void setPeakKWh(double peakKWh) { this.peakKWh = peakKWh; }
        public OffsetDateTime getPeakPeriodStart() { return peakPeriodStart; }
        public void setPeakPeriodStart(OffsetDateTime peakPeriodStart) { this.peakPeriodStart = peakPeriodStart; }
        public OffsetDateTime getPeakPeriodEnd() { return peakPeriodEnd; }
        public void setPeakPeriodEnd(OffsetDateTime peakPeriodEnd) { this.peakPeriodEnd = peakPeriodEnd; }
        public boolean isSimulated() { return simulated; }
        public void setSimulated(boolean simulated) { this.simulated = simulated; }
    }
}
