package com.volt.scheduler.backend.controllers;

import com.volt.scheduler.backend.models.Appointment;
import com.volt.scheduler.backend.models.response.CommonResponse;
import com.volt.scheduler.backend.services.OperatorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    // Endpoint to get all booked appointments of an operator
    @GetMapping("/{operatorId}")
    public ResponseEntity<?> getAllAppointmentsByOperator(@PathVariable Long operatorId) {

        try {
            List<Appointment> appointments = operatorService.findAllAppointmentsByOperator(operatorId);
            return new ResponseEntity<>(new CommonResponse<>(appointments, "Appointments retrieved successfully", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CommonResponse<>(null, "An error occurred", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Show open slots of operator
    @GetMapping("/{operatorId}/open-slots")
    public ResponseEntity<?> getOpenSlotsForOperator(
            @PathVariable Long operatorId,
            @RequestParam("dayStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dayStart,
            @RequestParam("dayEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dayEnd
    ) {
        try {
            List<String> openSlots = operatorService.findOpenSlotsForOperator(operatorId, Timestamp.valueOf(dayStart), Timestamp.valueOf(dayEnd));
            return ResponseEntity.ok(new CommonResponse<>(openSlots, "Open slots retrieved successfully", true));
        } catch (Exception e) {
            return new ResponseEntity<>(new CommonResponse<>(null, "An error occurred: " + e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
