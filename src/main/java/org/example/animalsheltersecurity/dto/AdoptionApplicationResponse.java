package org.example.animalsheltersecurity.dto;

public record AdoptionApplicationResponse(
        Long id,
        Long animalId,
        String animalNickname,
        Long applicantId,
        String applicantUsername,
        String status
) {
}
