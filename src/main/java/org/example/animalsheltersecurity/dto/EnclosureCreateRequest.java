package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record EnclosureCreateRequest(
        @NotEmpty(message = "allowedSpecies must contain at least one species")
        Set<@NotBlank String> allowedSpecies,

        @NotNull(message = "capacity is required")
        @Min(value = 1, message = "capacity must be positive")
        Integer capacity
) {
}
