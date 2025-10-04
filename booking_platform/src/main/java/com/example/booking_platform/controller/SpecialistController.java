package com.example.booking_platform.controller;

import com.example.booking_platform.model.entity.Specialist;
import com.example.booking_platform.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/specialists")
@RequiredArgsConstructor
public class SpecialistController {

    private final SpecialistService specialistService;

    @GetMapping
    public ResponseEntity<List<Specialist>> getAllSpecialists() {
        return ResponseEntity.ok(specialistService.getAllSpecialists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialist> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(specialistService.getSpecialistById(id));
    }

    @PostMapping
    public ResponseEntity<Specialist> create(@RequestBody Specialist specialist) {
        return ResponseEntity.ok(specialistService.createSpecialist(specialist));
    }
}
