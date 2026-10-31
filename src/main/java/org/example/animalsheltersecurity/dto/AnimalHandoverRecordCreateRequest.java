package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.NotNull;

public record AnimalHandoverRecordCreateRequest(
        @NotNull(message = "applicationId is required")
        Long applicationId,

        String status
) {
}
