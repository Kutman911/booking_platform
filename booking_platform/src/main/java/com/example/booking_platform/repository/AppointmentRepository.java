package com.example.booking_platform.repository;

import com.example.booking_platform.model.entity.Appointment;
import com.example.booking_platform.model.entity.Specialist;
import com.example.booking_platform.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.booking_platform.model.entity.User;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByClient(User client);
    List<Appointment> findBySpecialist(Specialist specialist);
    List<Appointment> findBySpecialistId(UUID specialistId);


}
