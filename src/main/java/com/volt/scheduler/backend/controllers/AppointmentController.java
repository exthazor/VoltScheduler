package com.volt.scheduler.backend.controllers;

import com.volt.scheduler.backend.exceptions.CustomExceptionForMessaging;
import com.volt.scheduler.backend.exceptions.CustomRuntimeExceptionForMessaging;
import com.volt.scheduler.backend.models.Appointment;
import com.volt.scheduler.backend.models.response.CommonResponse;
import com.volt.scheduler.backend.models.response.EmptyResponse;
import com.volt.scheduler.backend.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<CommonResponse> bookAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment bookedAppointment = appointmentService.bookAppointment(appointment);
            return new ResponseEntity<>(new EmptyResponse("Appointment booked successfully"), HttpStatus.CREATED);
        } catch (CustomExceptionForMessaging e) {
            return new ResponseEntity<>(new EmptyResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (CustomRuntimeExceptionForMessaging e) {
            return new ResponseEntity<>(new EmptyResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new EmptyResponse("Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
