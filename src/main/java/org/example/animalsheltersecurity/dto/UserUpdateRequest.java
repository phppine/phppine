package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserUpdateRequest(
        @Size(min = 1, message = "username must not be blank")
        String username,

        @Size(min = 1, message = "password must not be blank")
        String password,

        Set<String> roles,

        Boolean enabled
) {
}
