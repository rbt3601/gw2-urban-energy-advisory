package com.energy.advisory_service.adapter;

import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class WeatherServiceAdapter {

    public WeatherResult getWeatherContext(SubmitEnergyAssessmentRequest request) {
        String location = request.getProperty().getLocation().toLowerCase(Locale.ROOT);
        if (location.contains("no_weather")) {
            return null;
        }

        WeatherResult result = new WeatherResult();
        result.setSourceName("MockWeatherAdapter");
        result.setDataFrom(request.getTimeWindow().getStartDateTime());
        result.setDataTo(request.getTimeWindow().getEndDateTime());
        result.setSummary(location.contains("cold")
                ? "Cold weather increased heating demand during the selected time window."
                : "Weather conditions were moderate with no exceptional heating or cooling demand.");
        return result;
    }

    public static class WeatherResult {
        private String sourceName;
        private String dataFrom;
        private String dataTo;
        private String summary;

        public String getSourceName() { return sourceName; }
        public void setSourceName(String sourceName) { this.sourceName = sourceName; }
        public String getDataFrom() { return dataFrom; }
        public void setDataFrom(String dataFrom) { this.dataFrom = dataFrom; }
        public String getDataTo() { return dataTo; }
        public void setDataTo(String dataTo) { this.dataTo = dataTo; }
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
    }
}
