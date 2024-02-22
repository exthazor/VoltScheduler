package com.volt.scheduler.backend.models.response;

public class EmptyResponse implements CommonResponse {
    private final String message;

    public EmptyResponse(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
