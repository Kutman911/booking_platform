package com.example.booking_platform.service;

import com.example.booking_platform.model.entity.Specialist;
import java.util.List;
import java.util.UUID;

public interface SpecialistService {
    Specialist createSpecialist(Specialist specialist);
    List<Specialist> getAllSpecialists();
    Specialist getSpecialistById(UUID id);
}
