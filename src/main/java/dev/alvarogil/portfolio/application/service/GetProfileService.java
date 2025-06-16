package dev.alvarogil.portfolio.application.service;

import dev.alvarogil.portfolio.domain.model.profile.Profile;
import dev.alvarogil.portfolio.domain.port.in.GetProfileUseCase;
import dev.alvarogil.portfolio.domain.port.out.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GetProfileService implements GetProfileUseCase {
    private final ProfileRepository profileRepository;

    public GetProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile execute() {
        return profileRepository.find().orElseThrow(() -> new NoSuchElementException("Profile not found"));
    }
}
