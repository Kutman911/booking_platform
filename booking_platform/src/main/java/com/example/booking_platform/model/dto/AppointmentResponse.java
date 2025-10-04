package com.example.booking_platform.model.dto;

import com.example.booking_platform.model.entity.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AppointmentResponse {
    private UUID id;
    private UUID clientId;
    private UUID specialistId;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
}
