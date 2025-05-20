package dev.alvarogil.portfolio.application.service;


import dev.alvarogil.portfolio.application.dto.ProfileDto;
import dev.alvarogil.portfolio.domain.model.profile.Profile;
import dev.alvarogil.portfolio.domain.model.profile.ProfileTranslation;
import dev.alvarogil.portfolio.domain.port.out.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GetProfileServiceTest {

    private ProfileRepository profileRepository;
    private GetProfileService getProfileService;


    @BeforeEach
    void setup() {
        profileRepository = mock(ProfileRepository.class);
        getProfileService = new GetProfileService(profileRepository);
    }

    private Profile createEmptyProfile() {
        return new Profile(
                "avatarUrl",
                "name",
                "email",
                "location");
    }

    private void verifyRepository() {
        verify(profileRepository).find();
        verifyNoMoreInteractions(profileRepository);
    }

    @Test
    void givenProfileNotExists_whenExecute_thenThrow() {
        //given
        Locale locale = Locale.of("es");
        when(profileRepository.find()).thenReturn(Optional.empty());

        //when - then
        assertThrows(NoSuchElementException.class, () -> getProfileService.execute(locale));

        verifyRepository();
    }

    @Test
    void givenProfileAndLanguageExists_whenExecute_thenReturnsProfileDto() {
        //given
        Locale locale = Locale.of("es");

        Profile profile = createEmptyProfile();

        ProfileTranslation profileTranslation = new ProfileTranslation(
                "es",
                "title",
                "summary"
        );

        profile.addTranslation(profileTranslation);

        when(profileRepository.find()).thenReturn(Optional.of(profile));

        //when

        ProfileDto result = getProfileService.execute(locale);

        //then
        assertThat(result.avatarUrl()).isEqualTo(profile.getAvatarUrl());
        assertThat(result.name()).isEqualTo(profile.getName());
        assertThat(result.email()).isEqualTo(profile.getEmail());
        assertThat(result.location()).isEqualTo(profile.getLocation());
        assertThat(result.language()).isEqualTo(profileTranslation.language());
        assertThat(result.title()).isEqualTo(profileTranslation.title());
        assertThat(result.summary()).isEqualTo(profileTranslation.summary());

        verifyRepository();
    }

    @Test
    void givenProfileExistsAndLanguageNotExists_whenExecute_thenReturnsDefaultProfileDto() {
        //given
        String defaultLanguage = "en";
        String desiredLanguage = "es";
        Locale locale = Locale.of(desiredLanguage);

        Profile profile = createEmptyProfile();

        ProfileTranslation profileTranslation = new ProfileTranslation(
                defaultLanguage,
                "title",
                "summary"
        );

        profile.addTranslation(profileTranslation);

        when(profileRepository.find()).thenReturn(Optional.of(profile));

        //when

        ProfileDto result = getProfileService.execute(locale);

        //then
        assertThat(result.language()).isNotEqualTo(desiredLanguage);
        assertThat(result.language()).isEqualTo(defaultLanguage);

        assertThat(result.avatarUrl()).isEqualTo(profile.getAvatarUrl());
        assertThat(result.name()).isEqualTo(profile.getName());
        assertThat(result.email()).isEqualTo(profile.getEmail());
        assertThat(result.location()).isEqualTo(profile.getLocation());
        assertThat(result.title()).isEqualTo(profileTranslation.title());
        assertThat(result.summary()).isEqualTo(profileTranslation.summary());

        verifyRepository();
    }

    @Test
    void givenProfileExistsWithoutAnyTranslation_whenExecute_thenThrowsException() {
        //given
        String desiredLanguage = "es";
        Locale locale = Locale.of(desiredLanguage);

        Profile profile = createEmptyProfile();

        when(profileRepository.find()).thenReturn(Optional.of(profile));

        //when - then

        assertThrows(IllegalStateException.class, () -> getProfileService.execute(locale));

        verifyRepository();
    }

}
