package com.volt.scheduler.backend.services;

import com.volt.scheduler.backend.exceptions.CustomExceptionForMessaging;
import com.volt.scheduler.backend.exceptions.CustomRuntimeExceptionForMessaging;
import com.volt.scheduler.backend.models.Appointment;
import com.volt.scheduler.backend.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    //Ability to 'book' an appointment slot by specific Operator or any
    public Appointment bookAppointment(Appointment appointment) throws CustomExceptionForMessaging,
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
        return appointment;
    }

    public Optional<Long> findAvailableOperator(Timestamp startTime, Timestamp endTime) {

        List<Long> availableOperators = appointmentRepository.findAvailableOperator(startTime, endTime);
        if (!availableOperators.isEmpty()) {
            return Optional.of(availableOperators.get(0));
        } else {
            return Optional.empty();
        }
    }



}
