package com.observability.application.usecases;

import com.observability.application.dtos.AuthResponse;
import com.observability.application.dtos.RegisterRequest;
import com.observability.domain.entities.User;
import com.observability.domain.ports.output.UserRepository;
import com.observability.infrastructure.security.CustomUserDetails;
import com.observability.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse execute(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "USER")
                .build();

        // Since User.id is generated in create method but we are using builder here, let's ensure ID is set
        // Or use the User.create factory method if I update it to handle encoding, but encoding is application logic.
        // I'll just let the ID be null and let persistence handle it? No, User.create sets the ID.
        // Let's use a modified approach to ensure ID generation.
        user = User.create(
                request.getUsername(), 
                passwordEncoder.encode(request.getPassword()), 
                request.getEmail(), 
                request.getRole() != null ? request.getRole() : "USER"
        );

        userRepository.save(user);
        
        var jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        
        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
