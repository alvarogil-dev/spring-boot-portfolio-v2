package dev.alvarogil.portfolio.application.mapper;

import dev.alvarogil.portfolio.application.dto.ProfileDto;
import dev.alvarogil.portfolio.domain.model.profile.Profile;
import dev.alvarogil.portfolio.domain.model.profile.ProfileTranslation;

public class ProfileMapper {
    private ProfileMapper() {
    }

    public static ProfileDto toDto(Profile profile, ProfileTranslation profileTranslation) {
        return new ProfileDto(
                profile.getAvatarUrl(),
                profile.getName(),
                profile.getEmail(),
                profile.getLocation(),
                profileTranslation.language(),
                profileTranslation.title(),
                profileTranslation.summary()
        );
    }
}
