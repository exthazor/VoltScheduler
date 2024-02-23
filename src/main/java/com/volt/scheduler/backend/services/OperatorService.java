package com.volt.scheduler.backend.services;

import com.volt.scheduler.backend.models.Appointment;
import com.volt.scheduler.backend.models.TimeSlot;
import com.volt.scheduler.backend.repositories.AppointmentRepository;
import com.volt.scheduler.utils.UtilClass;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalTime;

@Service
public class OperatorService {

    private final AppointmentRepository appointmentRepository;

    public OperatorService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findAllAppointmentsByOperator(Long operatorId) {

        return appointmentRepository.findAllByOperatorId(operatorId);
    }
    public List<String> findOpenSlotsForOperator(Long operatorId) {
        // Generate start and end timestamps for the current day
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        // Convert to timestamp
        Timestamp dayStart = Timestamp.valueOf(startOfDay);
        Timestamp dayEnd = Timestamp.valueOf(endOfDay);
        //Find all appointments
        List<Appointment> bookedAppointments = appointmentRepository.findAppointmentsByOperatorAndTime(operatorId, dayStart, dayEnd);
        // Ensure appointments are sorted by start time
        bookedAppointments.sort(Comparator.comparing(Appointment::getStartTime));

        List<String> openSlots = new ArrayList<>();
        Timestamp currentSlotStart = dayStart;

        for (Appointment appointment : bookedAppointments) {
            if (currentSlotStart.before(appointment.getStartTime())) {
                // Found an open slot
                openSlots.add(UtilClass.formatTimeRange(currentSlotStart, appointment.getStartTime()));
            }
            // Move to the end of the current appointment for the next open slot start
            currentSlotStart = appointment.getEndTime();
        }

        // Check for an open slot between the last appointment and the end of the day
        if (currentSlotStart.before(dayEnd)) {
            openSlots.add(UtilClass.formatTimeRange(currentSlotStart, dayEnd));
        }

        return mergeConsecutiveSlots(openSlots);
    }
    // Helper function to merge consecutive slots. If slot = 4-5, 5-6, it will return 4-6
    // Inspired from: https://stackoverflow.com/questions/32771415/java-8-stream-mixing-two-elements
    private List<String> mergeConsecutiveSlots(List<String> slots) {
        // Base case of slots being empty
        if (slots.isEmpty()) {
            return slots;
        }

        // First, sort the slots by their start times
        List<TimeSlot> timeSlots = slots.stream()
                .map(this::parseSlot)
                .sorted(Comparator.comparing(slot -> slot.start))
                .toList();

        List<TimeSlot> mergedSlots = new ArrayList<>();
        TimeSlot currentSlot = timeSlots.get(0);

        for (int i = 1; i < timeSlots.size(); i++) {
            TimeSlot nextSlot = timeSlots.get(i);
            if (currentSlot.end.equals(nextSlot.start) || currentSlot.end.isAfter(nextSlot.start)) {
                // If the current slot end time is equal to or after the next slot start time, merge them
                currentSlot = new TimeSlot(currentSlot.start, UtilClass.max(currentSlot.end, nextSlot.end));
            } else {
                // No overlap, add the current slot to the result and move to the next
                mergedSlots.add(currentSlot);
                currentSlot = nextSlot;
            }
        }
        mergedSlots.add(currentSlot); // Add the last slot

        // Convert merged time slots back to string format
        return mergedSlots.stream()
                .map(TimeSlot::toString)
                .collect(Collectors.toList());
    }

     //Parses a string representation of a time slot into a TimeSlot object.
     //The input string is expected to be in the format "HH:mm-HH:mm",
     //representing the start and end times of a time slot.
    private TimeSlot parseSlot(String slot) {

        String[] parts = slot.split("-");
        LocalTime start = LocalTime.parse(parts[0]);
        LocalTime end = LocalTime.parse(parts[1]);
        return new TimeSlot(start, end);
    }
}
