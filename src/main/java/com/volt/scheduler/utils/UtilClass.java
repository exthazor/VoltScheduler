package com.volt.scheduler.utils;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalTime;

@Service
public class UtilClass {

    public static String formatTimeRange(Timestamp start, Timestamp end) {
        // Time as HH:mm (24-hour format)
        LocalTime startTime = start.toLocalDateTime().toLocalTime();
        LocalTime endTime = end.toLocalDateTime().toLocalTime();
        return String.format("%s-%s", startTime, endTime);
    }

    public static LocalTime max(LocalTime time1, LocalTime time2) {

        return time1.isAfter(time2) ? time1 : time2;
    }

}
