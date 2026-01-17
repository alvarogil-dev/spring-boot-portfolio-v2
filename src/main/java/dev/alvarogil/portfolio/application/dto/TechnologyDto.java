package dev.alvarogil.portfolio.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Technology",
        description = "Represents a technology entry."
)
public record TechnologyDto(
        @Schema(description = "Technology name", example = "Spring Boot")
        String name
) {
}
