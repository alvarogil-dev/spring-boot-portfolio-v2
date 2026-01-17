package dev.alvarogil.portfolio.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Language",
        description = "Represents a language proficiency entry."
)
public record LanguageDto(
        @Schema(description = "Language description", example = "Bilingüe en español y catalán")
        String description
) {
}
