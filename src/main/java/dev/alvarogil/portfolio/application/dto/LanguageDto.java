package dev.alvarogil.portfolio.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Language",
        description = "Represents a language proficiency entry."
)
public record LanguageDto(
        @Schema(description = "Language name", example = "Inglés")
        String name,

        @Schema(description = "Language level", example = "C1")
        String level
) {
}
