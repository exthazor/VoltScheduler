package com.volt.scheduler.backend.models.request;

import java.time.LocalDate;

public class RescheduleRequest {

    private Long appointmentId; // Old Appointment ID
    private LocalDate date; // The date for the new appointment
    private String newStartTime; // New start time in 24-hour format, e.g., "14:00"
    private String newEndTime; // New end time in 24-hour format, e.g., "15:00"

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    // Getters and setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNewStartTime() {
        return newStartTime;
    }

    public void setNewStartTime(String newStartTime) {
        this.newStartTime = newStartTime;
    }

    public String getNewEndTime() {
        return newEndTime;
    }

    public void setNewEndTime(String newEndTime) {
        this.newEndTime = newEndTime;
    }
}
