package dev.alvarogil.portfolio.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "Experience",
        description = "Represents a professional experience entry, including translated roles."
)
public record ExperienceDto(

        @Schema(description = "Company name", example = "Google")
        String companyName,

        @Schema(description = "Company website", example = "https://www.google.com")
        String companyUrl,

        @Schema(description = "URL of the company logo/avatar", example = "https://example.com/logo.png")
        String companyAvatarUrl,

        @Schema(description = "Period covered by all roles within this company", example = "Jan 2014 - Jan 2025")
        String period,

        @Schema(description = "List of roles held at the company")
        List<RoleDto> roles

) {}
