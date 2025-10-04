package com.example.booking_platform.repository;

import com.example.booking_platform.model.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecialistRepository extends JpaRepository<Specialist, UUID> {
}
