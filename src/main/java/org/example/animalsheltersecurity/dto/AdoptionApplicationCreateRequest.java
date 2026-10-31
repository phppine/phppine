package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.NotNull;

public record AdoptionApplicationCreateRequest(
        @NotNull(message = "animalId is required")
        Long animalId,

        @NotNull(message = "applicantId is required")
        Long applicantId,

        String status
) {
}
