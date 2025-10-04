package com.example.booking_platform.model.entity;

public enum AppointmentStatus {
    PENDING,
    CONFIRMED,
    CANCELED;

    public AppointmentStatus nextStatus(AppointmentStatus newStatus) {
        return newStatus;
    }
}
