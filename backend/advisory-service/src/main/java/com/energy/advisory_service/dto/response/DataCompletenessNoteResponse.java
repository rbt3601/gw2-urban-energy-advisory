package com.energy.advisory_service.dto.response;

public class DataCompletenessNoteResponse {

    private String missingSource;
    private String severity;
    private String message;

    public String getMissingSource() {
        return missingSource;
    }

    public void setMissingSource(String missingSource) {
        this.missingSource = missingSource;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}