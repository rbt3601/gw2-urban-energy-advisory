package com.energy.advisory_service.dto.response;

import java.time.OffsetDateTime;

public class EnergyConsumptionDataResponse {

    private String sourceName;
    private OffsetDateTime dataFrom;
    private OffsetDateTime dataTo;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public OffsetDateTime getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(OffsetDateTime dataFrom) {
        this.dataFrom = dataFrom;
    }

    public OffsetDateTime getDataTo() {
        return dataTo;
    }

    public void setDataTo(OffsetDateTime dataTo) {
        this.dataTo = dataTo;
    }
}