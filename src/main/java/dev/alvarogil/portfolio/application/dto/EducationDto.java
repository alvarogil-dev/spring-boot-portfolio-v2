package dev.alvarogil.portfolio.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "Education",
        description = "Represents an education entry including achievements."
)
public record EducationDto(
        @Schema(description = "Graduation year", example = "2021")
        String year,

        @Schema(description = "Degree or course name", example = "Grado en Ingeniería Informática")
        String title,

        @Schema(description = "Institution name", example = "Universitat Politècnica de Catalunya (UPC)")
        String institution,

        @Schema(description = "Location", example = "Barcelona, España")
        String location,

        @Schema(description = "Achievements or awards", example = "[\"Premio Recerca Jove PRJ 2014\"]")
        List<String> achievements
) {
}
