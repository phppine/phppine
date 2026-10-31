package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record EnclosureUpdateRequest(
        @Size(min = 1, message = "allowedSpecies must contain at least one species")
        Set<@NotBlank String> allowedSpecies,

        @Min(value = 1, message = "capacity must be positive")
        Integer capacity
) {
}
