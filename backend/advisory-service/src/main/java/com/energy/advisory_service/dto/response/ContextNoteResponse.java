package com.energy.advisory_service.dto.response;

public class ContextNoteResponse {

    private String noteType;
    private String message;

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}