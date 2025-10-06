package com.example.booking_platform.controller;

import com.example.booking_platform.model.entity.Appointment;
import com.example.booking_platform.model.entity.AppointmentStatus;
import com.example.booking_platform.model.entity.Specialist;
import com.example.booking_platform.model.entity.User;
import com.example.booking_platform.repository.AppointmentRepository;
import com.example.booking_platform.repository.SpecialistRepository;
import com.example.booking_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENT')")
public class ClientController {

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final AppointmentRepository appointmentRepository;

    // 🔹 View all specialists
    @GetMapping("/specialists")
    public ResponseEntity<List<Specialist>> getAllSpecialists() {
        return ResponseEntity.ok(specialistRepository.findAll());
    }

    // 🔹 Create a new appointment
    @PostMapping("/appointments/{specialistId}")
    public ResponseEntity<Appointment> createAppointment(
            @PathVariable UUID specialistId,
            @RequestParam UUID clientId,
            @RequestParam String dateTime
    ) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new RuntimeException("Specialist not found"));

        Appointment appointment = Appointment.builder()
                .client(client)
                .specialist(specialist)
                .dateTime(LocalDateTime.parse(dateTime))
                .status(AppointmentStatus.PENDING)
                .build();

        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    // 🔹 View client’s appointments
    @GetMapping("/appointments/{clientId}")
    public ResponseEntity<List<Appointment>> getAppointments(@PathVariable UUID clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return ResponseEntity.ok(appointmentRepository.findByClient(client));
    }

    // 🔹 Cancel an appointment
    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);
        return ResponseEntity.ok("Appointment canceled successfully");
    }
}
