package com.example.booking_platform.model.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentRequest {
    private UUID specialistId;
    private LocalDateTime dateTime;
}
