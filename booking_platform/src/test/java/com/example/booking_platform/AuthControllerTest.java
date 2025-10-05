package com.example.booking_platform;

import com.example.booking_platform.controller.AuthController;
import com.example.booking_platform.model.dto.AuthResponse;
import com.example.booking_platform.model.dto.LoginRequest;
import com.example.booking_platform.model.dto.RegisterRequest;
import com.example.booking_platform.model.entity.Role;
import com.example.booking_platform.model.entity.User;
import com.example.booking_platform.security.JwtUtil;
import com.example.booking_platform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newuser");
        request.setPassword("12345");
        request.setRole("CLIENT");

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .username("newuser")
                .password("encodedPass")
                .role(Role.CLIENT)
                .build();

        when(userService.registerUser(any(RegisterRequest.class))).thenReturn(savedUser);

        ResponseEntity<String> response = authController.register(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("User registered successfully: newuser");

        verify(userService, times(1)).registerUser(any(RegisterRequest.class));
    }

    @Test
    void testLoginUser() {
        LoginRequest request = new LoginRequest();
        request.setUsername("client1");
        request.setPassword("12345");

        when(jwtUtil.generateToken("client1")).thenReturn("mocked-jwt-token");

        ResponseEntity<AuthResponse> response = authController.login(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getToken()).isEqualTo("mocked-jwt-token");

        verify(jwtUtil, times(1)).generateToken("client1");
    }
}
