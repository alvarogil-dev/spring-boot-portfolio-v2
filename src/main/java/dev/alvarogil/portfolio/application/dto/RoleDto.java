package dev.alvarogil.portfolio.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Role",
        description = "Represents a role or position held within a company."
)
public record RoleDto(

        @Schema(description = "Translated title of the role", example = "Senior Backend Developer")
        String title,

        @Schema(description = "Translated short description of the role", example = "Developed scalable APIs and microservices.")
        String shortSummary,

        @Schema(description = "Translated long description of the role", example = "Developed scalable APIs and microservices.")
        String longSummary,

        @Schema(description = "Start date", example = "Jan 2024")
        String startDate,

        @Schema(description = "End date (or null if current)", example = "Jan 2025")
        String endDate,

        @Schema(description = "Language of the translation", example = "en")
        String language

) {}
