package com.volt.scheduler.backend.services;

import com.volt.scheduler.backend.exceptions.CustomExceptionForMessaging;
import com.volt.scheduler.backend.exceptions.CustomRuntimeExceptionForMessaging;
import com.volt.scheduler.backend.models.Appointment;
import com.volt.scheduler.backend.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    AppointmentRepository appointmentRepository;

    //Ability to 'book' an appointment slot by specific Operator or any
    public void bookAppointment(Appointment appointment) throws CustomExceptionForMessaging,
            CustomRuntimeExceptionForMessaging {

        Long operatorId = appointment.getOperatorId();
        Timestamp startTime = appointment.getStartTime();
        Timestamp endTime = appointment.getEndTime();

        // If operatorId is null, find an available operator
        if (operatorId == null) {
            operatorId = findAvailableOperator(startTime, endTime).orElseThrow(() ->
                    new CustomExceptionForMessaging("No available operators for the given time slot."));
            appointment.setOperatorId(operatorId);
        }

        List<Appointment> existingAppointments = appointmentRepository.findAppointmentsByOperatorAndTime(operatorId, startTime, endTime);
        if (!existingAppointments.isEmpty()) {
            throw new CustomRuntimeExceptionForMessaging("The slot with the specified operator is already booked.");
        }

        appointmentRepository.save(appointment);
    }

    public Optional<Long> findAvailableOperator(Timestamp startTime, Timestamp endTime) {

        List<Long> availableOperators = appointmentRepository.findAvailableOperator(startTime, endTime);
        if (!availableOperators.isEmpty()) {
            return Optional.of(availableOperators.get(0));
        } else {
            return Optional.empty();
        }
    }

    public void rescheduleAppointment(Long appointmentId, Timestamp newStartTime, Timestamp newEndTime)
            throws CustomExceptionForMessaging {

        Appointment existingAppointment = findAppointmentById(appointmentId);

        // Check if the new time slot is available for the same operator
        List<Appointment> conflictingAppointments =
                appointmentRepository.findAppointmentsByOperatorAndTime(
                        existingAppointment.getOperatorId(),
                        newStartTime,
                        newEndTime
                );

        if (!conflictingAppointments.isEmpty()) {
            throw new CustomRuntimeExceptionForMessaging("The new slot is already booked.");
        }

        // Update the appointment time if slot is available
        existingAppointment.setStartTime(newStartTime);
        existingAppointment.setEndTime(newEndTime);
        appointmentRepository.save(existingAppointment);
    }

    public void cancelAppointment(Long appointmentId) throws CustomExceptionForMessaging {
        // Retrieve the appointment to ensure it exists
        findAppointmentById(appointmentId);
        // Delete the appointment
        appointmentRepository.deleteById(appointmentId);
    }

    public List<Appointment> findAllAppointmentsByOperator(Long operatorId) {

        return appointmentRepository.findAllByOperatorId(operatorId);
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
