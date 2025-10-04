package com.example.booking_platform.service.impl;

import com.example.booking_platform.model.entity.Specialist;
import com.example.booking_platform.repository.SpecialistRepository;
import com.example.booking_platform.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;

    @Override
    public Specialist createSpecialist(Specialist specialist) {
        return specialistRepository.save(specialist);
    }

    @Override
    public List<Specialist> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    @Override
    public Specialist getSpecialistById(UUID id) {
        return specialistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialist not found"));
    }
}
