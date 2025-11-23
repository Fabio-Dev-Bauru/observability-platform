package com.observability.domain.entities;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String role;

    public static User create(String username, String password, String email, String role) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();
    }
}
