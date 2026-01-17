package dev.alvarogil.portfolio.application.service;


import dev.alvarogil.portfolio.domain.model.profile.Profile;
import dev.alvarogil.portfolio.domain.model.profile.ProfileTranslation;
import dev.alvarogil.portfolio.domain.port.out.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                "phone",
                "location");
    }

    private void verifyRepository() {
        verify(profileRepository).find();
        verifyNoMoreInteractions(profileRepository);
    }

    @Test
    void givenProfileNotExists_whenExecute_thenThrow() {
        //given
        when(profileRepository.find()).thenReturn(Optional.empty());

        //when - then
        assertThrows(NoSuchElementException.class, () -> getProfileService.execute());

        verifyRepository();
    }

    @Test
    void givenProfileExists_whenExecute_thenReturnsProfile() {
        //given
        Profile profile = createEmptyProfile();

        ProfileTranslation profileTranslation = new ProfileTranslation(
                "es",
                "title",
                "summary"
        );

        profile.addTranslation(profileTranslation);

        when(profileRepository.find()).thenReturn(Optional.of(profile));

        //when

        Profile result = getProfileService.execute();

        //then
        assertEquals(profile, result);

        verifyRepository();
    }
}
