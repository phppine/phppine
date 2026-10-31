package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.Size;

public record AdoptionApplicationUpdateRequest(
        Long animalId,

        Long applicantId,

        @Size(min = 1, message = "status must not be blank")
        String status
) {
}
