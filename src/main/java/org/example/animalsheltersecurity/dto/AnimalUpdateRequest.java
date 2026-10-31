package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AnimalUpdateRequest(
        @Size(min = 1, message = "nickname must not be blank")
        String nickname,

        @Size(min = 1, message = "species must not be blank")
        String species,

        @Min(value = 0, message = "age must be zero or positive")
        Integer age,

        @Size(min = 1, message = "status must not be blank")
        String status
) {
}
