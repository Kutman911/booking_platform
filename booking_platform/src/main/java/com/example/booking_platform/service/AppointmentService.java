package com.example.booking_platform.service;

import com.example.booking_platform.model.entity.Appointment;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);
    List<Appointment> getAppointmentsForClient(UUID clientId);
    List<Appointment> getAppointmentsForSpecialist(UUID specialistId);
    Appointment confirmAppointment(UUID id);
    Appointment cancelAppointment(UUID id);
}
