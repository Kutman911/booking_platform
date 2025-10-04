package com.example.booking_platform.controller;

import com.example.booking_platform.model.dto.AppointmentRequest;
import com.example.booking_platform.model.dto.AppointmentResponse;
import com.example.booking_platform.model.entity.Appointment;
import com.example.booking_platform.model.entity.Specialist;
import com.example.booking_platform.model.entity.User;
import com.example.booking_platform.service.AppointmentService;
import com.example.booking_platform.service.AuthUserService;
import com.example.booking_platform.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AuthUserService authUserService;
    private final SpecialistRepository specialistRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
        User currentUser = authUserService.getCurrentUser();
        Specialist specialist = specialistRepository.findById(request.getSpecialistId())
                .orElseThrow(() -> new RuntimeException("Specialist not found"));

        Appointment appointment = Appointment.builder()
                .client(currentUser)
                .specialist(specialist)
                .dateTime(request.getDateTime())
                .build();

        Appointment saved = appointmentService.createAppointment(appointment);

        AppointmentResponse response = AppointmentResponse.builder()
                .id(saved.getId())
                .clientId(saved.getClient().getId())
                .specialistId(saved.getSpecialist().getId())
                .dateTime(saved.getDateTime())
                .status(saved.getStatus())
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments() {
        User currentUser = authUserService.getCurrentUser();
        List<AppointmentResponse> responses = appointmentService
                .getAppointmentsForClient(currentUser.getId())
                .stream()
                .map(a -> AppointmentResponse.builder()
                        .id(a.getId())
                        .clientId(a.getClient().getId())
                        .specialistId(a.getSpecialist().getId())
                        .dateTime(a.getDateTime())
                        .status(a.getStatus())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @GetMapping("/specialist/{specialistId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForSpecialist(@PathVariable UUID specialistId) {
        List<AppointmentResponse> responses = appointmentService
                .getAppointmentsForSpecialist(specialistId)
                .stream()
                .map(a -> AppointmentResponse.builder()
                        .id(a.getId())
                        .clientId(a.getClient().getId())
                        .specialistId(a.getSpecialist().getId())
                        .dateTime(a.getDateTime())
                        .status(a.getStatus())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @PutMapping("/{id}/confirm")
    public ResponseEntity<AppointmentResponse> confirmAppointment(@PathVariable UUID id) {
        Appointment appointment = appointmentService.confirmAppointment(id);
        AppointmentResponse response = AppointmentResponse.builder()
                .id(appointment.getId())
                .clientId(appointment.getClient().getId())
                .specialistId(appointment.getSpecialist().getId())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable UUID id) {
        Appointment appointment = appointmentService.cancelAppointment(id);
        AppointmentResponse response = AppointmentResponse.builder()
                .id(appointment.getId())
                .clientId(appointment.getClient().getId())
                .specialistId(appointment.getSpecialist().getId())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .build();
        return ResponseEntity.ok(response);
    }
}
