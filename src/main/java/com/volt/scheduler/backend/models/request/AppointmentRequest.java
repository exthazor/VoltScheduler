package com.volt.scheduler.backend.models.request;

import java.time.LocalDate;

public class AppointmentRequest {

    private Long operatorId; // The ID of the operator
    private LocalDate date; // The date of the appointment
    private String startTime; // The start time of the appointment in "HH:mm" format
    private String endTime; // The end time of the appointment in "HH:mm" format

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
