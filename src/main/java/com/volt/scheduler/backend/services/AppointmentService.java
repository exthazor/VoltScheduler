package com.volt.scheduler.backend.services;

import com.volt.scheduler.backend.exceptions.CustomExceptionForMessaging;
import com.volt.scheduler.backend.exceptions.CustomRuntimeExceptionForMessaging;
import com.volt.scheduler.backend.models.Appointment;
import com.volt.scheduler.backend.models.request.AppointmentRequest;
import com.volt.scheduler.backend.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    AppointmentRepository appointmentRepository;

    //Ability to 'book' an appointment slot by specific Operator or any
    public void bookAppointment(AppointmentRequest appointmentRequest) throws
            CustomExceptionForMessaging, CustomRuntimeExceptionForMessaging {

        Long operatorId = appointmentRequest.getOperatorId();
        LocalDate date = appointmentRequest.getDate(); // Assuming the date is part of the request
        LocalTime startTime = LocalTime.parse(appointmentRequest.getStartTime());
        LocalTime endTime = LocalTime.parse(appointmentRequest.getEndTime());

        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

        // Convert LocalDateTime to Timestamp
        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

        // If operatorId is null, find an available operator
        if (operatorId == null) {
            List<Long> availableOperators = appointmentRepository.findAvailableOperator(startTimestamp, endTimestamp);
            if (availableOperators.isEmpty()) {
                throw new CustomExceptionForMessaging("No available operators for the given time slot.");
            }
            operatorId = availableOperators.get(0);
        }

        List<Appointment> existingAppointments = appointmentRepository.findAppointmentsByOperatorAndTime(operatorId, startTimestamp, endTimestamp);
        if (!existingAppointments.isEmpty()) {
            throw new CustomRuntimeExceptionForMessaging("The slot with the specified operator is already booked.");
        }

        Appointment appointment = new Appointment();
        appointment.setOperatorId(operatorId);
        appointment.setStartTime(startTimestamp);
        appointment.setEndTime(endTimestamp);

        appointmentRepository.save(appointment);
    }


    public void rescheduleAppointment(Long appointmentId, String newStartTimeStr, String newEndTimeStr, LocalDate date)
            throws CustomExceptionForMessaging, CustomRuntimeExceptionForMessaging {

        Appointment existingAppointment = findAppointmentById(appointmentId);

        LocalTime newStartTime = LocalTime.parse(newStartTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime newEndTime = LocalTime.parse(newEndTimeStr, DateTimeFormatter.ofPattern("HH:mm"));

        LocalDateTime newStartDateTime = LocalDateTime.of(date, newStartTime);
        LocalDateTime newEndDateTime = LocalDateTime.of(date, newEndTime);

        // Convert LocalDateTime to Timestamp if necessary or directly use LocalDateTime based on your JPA setup
        Timestamp newStartTimestamp = Timestamp.valueOf(newStartDateTime);
        Timestamp newEndTimestamp = Timestamp.valueOf(newEndDateTime);

        // Check if the new time slot is available for the same operator
        List<Appointment> conflictingAppointments =
                appointmentRepository.findAppointmentsByOperatorAndTime(
                        existingAppointment.getOperatorId(),
                        newStartTimestamp,
                        newEndTimestamp
                );

        if (!conflictingAppointments.isEmpty()) {
            throw new CustomRuntimeExceptionForMessaging("The new slot is already booked.");
        }

        // Update the appointment time if the slot is available
        existingAppointment.setStartTime(newStartTimestamp);
        existingAppointment.setEndTime(newEndTimestamp);
        appointmentRepository.save(existingAppointment);
    }


    public void cancelAppointment(Long appointmentId) throws CustomExceptionForMessaging {
        // Retrieve the appointment to ensure it exists
        findAppointmentById(appointmentId);
        // Delete the appointment
        appointmentRepository.deleteById(appointmentId);
    }

    public Appointment findAppointmentById(Long id) throws CustomExceptionForMessaging {

        List<Appointment> appointments = appointmentRepository.findById(id);

        if (appointments.isEmpty()) {
            throw new CustomExceptionForMessaging("Appointment not found.");
        }
        // return appointment in the list
        return appointments.get(0);
    }


}
