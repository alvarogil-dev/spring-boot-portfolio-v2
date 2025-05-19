package dev.alvarogil.portfolio.application.service;

import dev.alvarogil.portfolio.application.dto.ProfileDto;
import dev.alvarogil.portfolio.application.mapper.ProfileMapper;
import dev.alvarogil.portfolio.domain.model.Profile;
import dev.alvarogil.portfolio.domain.model.ProfileTranslation;
import dev.alvarogil.portfolio.domain.port.in.GetProfileUseCase;
import dev.alvarogil.portfolio.domain.port.out.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class GetProfileService implements GetProfileUseCase {
    private final ProfileRepository profileRepository;

    public GetProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileDto execute(Locale locale) {
        String language = locale.getLanguage();

        Profile profile = profileRepository.find().orElseThrow(() -> new NoSuchElementException("Profile not found"));

        ProfileTranslation profileTranslation = profile.getTranslationOrFallback(language);

        return ProfileMapper.toDto(profile, profileTranslation);

    }
}
