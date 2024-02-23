package com.volt.scheduler.backend.models;

import java.time.LocalTime;

public class TimeSlot {
    public LocalTime start;
    public LocalTime end;

    public TimeSlot(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start + "-" + end;
    }
}