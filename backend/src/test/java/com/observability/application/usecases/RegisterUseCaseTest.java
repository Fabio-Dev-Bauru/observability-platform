package com.observability.application.usecases;

import com.observability.application.dtos.AuthResponse;
import com.observability.application.dtos.RegisterRequest;
import com.observability.domain.entities.User;
import com.observability.domain.ports.output.UserRepository;
import com.observability.infrastructure.security.CustomUserDetails;
import com.observability.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RegisterUseCase registerUseCase;

    @Test
    void shouldRegisterUserSuccessfully() {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "password", "USER");
        
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(CustomUserDetails.class))).thenReturn("jwt-token");

        AuthResponse response = registerUseCase.execute(request);

        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
        assertEquals("jwt-token", response.getToken());
        assertEquals("USER", response.getRole());

        verify(userRepository).save(any(User.class));
    }
}
