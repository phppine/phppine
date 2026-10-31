package org.example.animalsheltersecurity.dto;

import java.util.Set;

public record EnclosureResponse(
        Long id,
        Set<String> allowedSpecies,
        Integer capacity
) {
}
