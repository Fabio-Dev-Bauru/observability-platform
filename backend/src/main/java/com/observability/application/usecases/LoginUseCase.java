package com.observability.application.usecases;

import com.observability.application.dtos.AuthRequest;
import com.observability.application.dtos.AuthResponse;
import com.observability.domain.entities.User;
import com.observability.domain.ports.output.UserRepository;
import com.observability.infrastructure.security.CustomUserDetails;
import com.observability.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse execute(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        
        var jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        
        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
