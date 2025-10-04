package com.example.booking_platform;

import com.example.booking_platform.model.entity.*;
import com.example.booking_platform.repository.AppointmentRepository;
import com.example.booking_platform.repository.SpecialistRepository;
import com.example.booking_platform.repository.UserRepository;
import com.example.booking_platform.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SpecialistRepository specialistRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private User client;
    private Specialist specialist;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = User.builder()
                .id(UUID.randomUUID())
                .username("client1")
                .password("pass")
                .role(Role.CLIENT)
                .build();

        specialist = Specialist.builder()
                .id(UUID.randomUUID())
                .fullName("John Barber")
                .specialty("Haircut")
                .description("Top specialist")
                .build();

        appointment = Appointment.builder()
                .id(UUID.randomUUID())
                .client(client)
                .specialist(specialist)
                .dateTime(LocalDateTime.now())
                .status(AppointmentStatus.PENDING)
                .build();
    }

    @Test
    void testCreateAppointment() {
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = appointmentService.createAppointment(appointment);

        assertNotNull(result);
        assertEquals(AppointmentStatus.PENDING, result.getStatus());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void testGetAppointmentsForClient() {
        when(userRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(appointmentRepository.findByClient(client)).thenReturn(List.of(appointment));

        List<Appointment> result = appointmentService.getAppointmentsForClient(client.getId());

        assertEquals(1, result.size());
        assertEquals(client, result.get(0).getClient());
    }

    @Test
    void testGetAppointmentsForSpecialist() {
        when(specialistRepository.findById(specialist.getId())).thenReturn(Optional.of(specialist));
        when(appointmentRepository.findBySpecialist(specialist)).thenReturn(List.of(appointment));

        List<Appointment> result = appointmentService.getAppointmentsForSpecialist(specialist.getId());

        assertEquals(1, result.size());
        assertEquals(specialist, result.get(0).getSpecialist());
    }

    @Test
    void testConfirmAppointment() {
        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = appointmentService.confirmAppointment(appointment.getId());

        assertEquals(AppointmentStatus.CONFIRMED, result.getStatus());
    }

    @Test
    void testCancelAppointment() {
        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = appointmentService.cancelAppointment(appointment.getId());

        assertEquals(AppointmentStatus.CANCELED, result.getStatus());
    }
}
