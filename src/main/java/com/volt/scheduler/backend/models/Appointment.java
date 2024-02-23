package com.volt.scheduler.backend.models;

import java.sql.Timestamp;

// Data class for Appointment Object
public class Appointment {

    private Long appointmentId; // Unique identifier for the appointment
    private Long operatorId; // ID of the operator associated with this appointment
    private Timestamp startTime; // Start time of the appointment
    private Timestamp endTime; // End time of the appointment

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }


}
