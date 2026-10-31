package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.Size;

public record AnimalHandoverRecordUpdateRequest(
        Long applicationId,

        @Size(min = 1, message = "status must not be blank")
        String status
) {
}
