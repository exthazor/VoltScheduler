package com.volt.scheduler.backend.models.request;

import java.time.LocalDate;

public class AppointmentRequest {

    private Long operatorId;
    private LocalDate date;
    private String startTime; // "HH:mm" format
    private String endTime; // "HH:mm" format

    // Getters and Setters
    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
