package org.example.animalsheltersecurity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnimalCreateRequest(
        @NotBlank(message = "nickname must not be blank")
        String nickname,

        @NotBlank(message = "species must not be blank")
        String species,

        @NotNull(message = "age is required")
        @Min(value = 0, message = "age must be zero or positive")
        Integer age,

        String status
) {
}
