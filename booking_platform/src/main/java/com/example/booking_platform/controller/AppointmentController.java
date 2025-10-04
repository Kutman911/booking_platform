package com.example.booking_platform.controller;

import com.example.booking_platform.model.entity.Appointment;
import com.example.booking_platform.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForClient(@PathVariable UUID clientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForClient(clientId);
        return ResponseEntity.ok(appointments);
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @GetMapping("/specialist/{specialistId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForSpecialist(@PathVariable UUID specialistId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForSpecialist(specialistId);
        return ResponseEntity.ok(appointments);
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Appointment> confirmAppointment(@PathVariable UUID id) {
        Appointment confirmed = appointmentService.confirmAppointment(id);
        return ResponseEntity.ok(confirmed);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable UUID id) {
        Appointment canceled = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(canceled);
    }
}
