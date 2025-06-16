package dev.alvarogil.portfolio.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Profile",
        description = "Represents the public information of the user's profile in a specific language."
)
public record ProfileDto(

        @Schema(description = "URL of the avatar image", example = "https://www.alvarogil.dev/img/profile.png")
        String avatarUrl,

        @Schema(description = "Full name of the person", example = "Álvaro Gil")
        String name,

        @Schema(description = "Email address", example = "alvaro@alvarogil.dev")
        String email,

        @Schema(description = "Location or city", example = "Barcelona, Spain")
        String location,

        @Schema(description = "Language of the translation", example = "en")
        String language,

        @Schema(description = "Job title or professional role", example = "Software Engineer")
        String title,

        @Schema(description = "Short summary or bio", example = "Passionate about backend and DevOps")
        String summary

) {}
