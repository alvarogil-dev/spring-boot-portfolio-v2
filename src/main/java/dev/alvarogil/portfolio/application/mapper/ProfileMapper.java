package dev.alvarogil.portfolio.application.mapper;

import dev.alvarogil.portfolio.application.dto.ProfileDto;
import dev.alvarogil.portfolio.domain.model.profile.Profile;
import dev.alvarogil.portfolio.domain.model.profile.ProfileTranslation;

import java.util.Locale;

public class ProfileMapper {
    private ProfileMapper() {
    }

    public static ProfileDto toDto(Profile profile, Locale locale) {

        ProfileTranslation profileTranslation = profile.getTranslationOrFallback(locale.getLanguage());

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
