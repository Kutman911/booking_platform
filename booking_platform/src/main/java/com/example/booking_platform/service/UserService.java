package com.example.booking_platform.service;

import com.example.booking_platform.model.dto.RegisterRequest;
import com.example.booking_platform.model.entity.User;

public interface UserService {
    User register(RegisterRequest request);
    User findByUsername(String username);
}
