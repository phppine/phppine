package org.example.animalsheltersecurity.dto;

import java.util.Set;

/**
 * Public representation of a user. The password is intentionally absent.
 */
public record UserResponse(
        Long id,
        String username,
        Set<String> roles,
        Boolean enabled
) {
}
