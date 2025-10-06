package com.example.booking_platform.controller;

import com.example.booking_platform.model.entity.Appointment;
import com.example.booking_platform.model.entity.Specialist;
import com.example.booking_platform.repository.AppointmentRepository;
import com.example.booking_platform.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/specialist")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SPECIALIST')")
public class SpecialistController {

    private final SpecialistRepository specialistRepository;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/me/{userId}")
    public ResponseEntity<Specialist> getProfile(@PathVariable UUID userId) {
        Specialist specialist = specialistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Specialist not found"));
        return ResponseEntity.ok(specialist);
    }

    @PutMapping("/me/{userId}")
    public ResponseEntity<Specialist> updateProfile(@PathVariable UUID userId,
                                                    @RequestBody Specialist updatedInfo) {
        Specialist specialist = specialistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Specialist not found"));

        specialist.setFullName(updatedInfo.getFullName());
        specialist.setSpecialty(updatedInfo.getSpecialty());
        specialist.setDescription(updatedInfo.getDescription());

        return ResponseEntity.ok(specialistRepository.save(specialist));
    }

    @GetMapping("/appointments/{specialistId}")
    public ResponseEntity<List<Appointment>> getAppointments(@PathVariable UUID specialistId) {
        return ResponseEntity.ok(appointmentRepository.findBySpecialistId(specialistId));
    }

    @PutMapping("/appointments/{appointmentId}/confirm")
    public ResponseEntity<Appointment> confirmAppointment(@PathVariable UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(com.example.booking_platform.model.entity.AppointmentStatus.CONFIRMED);
        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    @PutMapping("/appointments/{appointmentId}/cancel")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(com.example.booking_platform.model.entity.AppointmentStatus.CANCELED);
        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }
}
