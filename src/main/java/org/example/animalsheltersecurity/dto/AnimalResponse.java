package org.example.animalsheltersecurity.dto;

public record AnimalResponse(
        Long id,
        String nickname,
        String species,
        Integer age,
        String status
) {
}
