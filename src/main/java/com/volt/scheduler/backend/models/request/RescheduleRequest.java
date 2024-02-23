package com.volt.scheduler.backend.models.request;

import java.sql.Timestamp;

public class RescheduleRequest {

    Timestamp newStartTime;
    Timestamp newEndTimestamp;

    public Timestamp getNewStartTime() {
        return newStartTime;
    }

    public void setNewStartTime(Timestamp newStartTime) {
        this.newStartTime = newStartTime;
    }

    public Timestamp getNewEndTime() {
        return newEndTimestamp;
    }

    public void setNewEndTime(Timestamp newEndTimestamp) {
        this.newEndTimestamp = newEndTimestamp;
    }
}
