package org.example.animalsheltersecurity.dto;

import java.time.LocalDateTime;

public record AnimalHandoverRecordResponse(
        Long id,
        Long applicationId,
        LocalDateTime preparationDate,
        LocalDateTime confirmationDate,
        String status
) {
}
