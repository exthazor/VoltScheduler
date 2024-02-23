package com.volt.scheduler.backend.controllers;

import com.volt.scheduler.backend.exceptions.CustomExceptionForMessaging;
import com.volt.scheduler.backend.exceptions.CustomRuntimeExceptionForMessaging;
import com.volt.scheduler.backend.models.Appointment;
import com.volt.scheduler.backend.models.request.RescheduleRequest;
import com.volt.scheduler.backend.models.response.CommonResponse;
import com.volt.scheduler.backend.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Endpoint to book an appointment
    @PostMapping("/book")
    public ResponseEntity<CommonResponse> bookAppointment(@RequestBody Appointment appointment) {
        try {
            appointmentService.bookAppointment(appointment);
            return new ResponseEntity<>(new CommonResponse<>(null, "Appointment booked successfully", true), HttpStatus.CREATED);
        } catch (CustomExceptionForMessaging e) {
            return new ResponseEntity<>(new CommonResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (CustomRuntimeExceptionForMessaging e) {
            return new ResponseEntity<>(new CommonResponse<>(null, e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new CommonResponse<>(null, "Something went wrong", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to reschedule an appointment
    @PostMapping("/{appointmentId}/reschedule")
    public ResponseEntity<CommonResponse> rescheduleAppointment(@PathVariable Long appointmentId,
                                                                @RequestBody RescheduleRequest request) {
        try {
            appointmentService.rescheduleAppointment(appointmentId, request.getNewStartTime(), request.getNewEndTime());
            return new ResponseEntity<>(new CommonResponse<>(null, "Appointment rescheduled successfully", true), HttpStatus.OK);
        } catch (CustomExceptionForMessaging e) {
            return new ResponseEntity<>(new CommonResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (CustomRuntimeExceptionForMessaging e) {
            return new ResponseEntity<>(new CommonResponse<>(null, e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new CommonResponse<>(null, "Something went wrong", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete an appointment
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<CommonResponse> cancelAppointment(@PathVariable Long appointmentId) {
        try {
            appointmentService.cancelAppointment(appointmentId);
            return new ResponseEntity<>(new CommonResponse<>(null, "Appointment canceled successfully", true), HttpStatus.OK);
        } catch (CustomExceptionForMessaging e) {
            return new ResponseEntity<>(new CommonResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (CustomRuntimeExceptionForMessaging e) {
            return new ResponseEntity<>(new CommonResponse<>(null, e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new CommonResponse<>(null, "Something went wrong", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get all booked appointments of an operator
    @GetMapping("/operator/{operatorId}")
    public ResponseEntity<CommonResponse> getAllAppointmentsByOperator(@PathVariable Long operatorId) {
        try {
            List<Appointment> appointments = appointmentService.findAllAppointmentsByOperator(operatorId);
            return new ResponseEntity<>(new CommonResponse<>(appointments, "Appointments retrieved successfully", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CommonResponse<>(null, "An error occurred", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
