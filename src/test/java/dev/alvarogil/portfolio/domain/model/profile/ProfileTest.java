package dev.alvarogil.portfolio.domain.model.profile;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProfileTest {

    private Profile createEmptyProfile() {
        return new Profile(
                "avatarUrl",
                "name",
                "email",
                "location");
    }

    // avoiding addTranslation() to keep this unit test "pure"... enjoy @Albert ;)
    private void injectTranslations(Profile profile, List<ProfileTranslation> profileTranslations) throws Exception {
        Field field = Profile.class.getDeclaredField("translations");
        field.setAccessible(true);
        field.set(profile, profileTranslations);
    }

    @Test
    void givenNonExistingLanguage_whenGetTranslation_thenReturnsEmpty() {
        //given
        Profile profile = createEmptyProfile();

        //when
        var result = profile.getTranslation("es");

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void givenExistingLanguage_whenGetTranslation_thenReturnsTranslation() throws Exception {
        //given
        String expectedLanguage = "es";

        Profile profile = createEmptyProfile();

        ProfileTranslation profileTranslation = new ProfileTranslation(
                expectedLanguage,
                "title",
                "summary");

        injectTranslations(profile, List.of(profileTranslation));

        //when
        var result = profile.getTranslation(expectedLanguage);

        //then
        assertThat(result)
                .isPresent()
                .map(ProfileTranslation::language)
                .hasValue(expectedLanguage);
    }

    @Test
    void givenExistingLanguage_whenGetTranslationOrFallback_thenReturnsThatTranslation() throws Exception {
        //given
        String expectedLanguage = "es";

        Profile profile = createEmptyProfile();

        ProfileTranslation profileTranslation = new ProfileTranslation(
                expectedLanguage,
                "title",
                "summary");

        injectTranslations(profile, List.of(profileTranslation));

        //when
        var result = profile.getTranslationOrFallback(expectedLanguage);

        //then
        assertThat(result.language()).isEqualTo(expectedLanguage);
    }

    @Test
    void givenNonExistingLanguage_whenGetTranslationOrFallback_thenReturnsEnglish() throws Exception {
        //given
        String expectedLanguage = "es";
        String defaultLanguage = "en";

        Profile profile = createEmptyProfile();

        ProfileTranslation profileTranslation = new ProfileTranslation(
                defaultLanguage,
                "title",
                "summary");

        injectTranslations(profile, List.of(profileTranslation));

        //when
        var result = profile.getTranslationOrFallback(expectedLanguage);

        //then
        assertThat(result.language()).isEqualTo(defaultLanguage);
    }

    @Test
    void givenNoEnglishTranslation_whenGetTranslationOrFallback_thenThrowsException() {
        //given
        String expectedLanguage = "es";

        Profile profile = createEmptyProfile();

        //when - then
        assertThrows(IllegalStateException.class, () -> profile.getTranslationOrFallback(expectedLanguage));
    }

    @Test
    void givenNewLanguage_whenAddTranslation_thenAddsIt() {
        //given
        String expectedLanguage = "en";

        Profile profile = createEmptyProfile();

        ProfileTranslation profileTranslation = new ProfileTranslation(
                expectedLanguage,
                "title",
                "summary");

        //when
        profile.addTranslation(profileTranslation);

        //then
        var translation = profile.getTranslation(expectedLanguage);

        assertThat(translation)
                .isPresent()
                .map(ProfileTranslation::language)
                .hasValue(expectedLanguage);
    }

    @Test
    void givenDuplicateLanguage_whenAddTranslation_thenThrowsException() {
        //given
        String expectedLanguage = "en";

        Profile profile = createEmptyProfile();

        ProfileTranslation profileTranslation = new ProfileTranslation(
                expectedLanguage,
                "title",
                "summary");

        profile.addTranslation(profileTranslation);

        ProfileTranslation repeatedLanguageProfileTranslation = new ProfileTranslation(
                expectedLanguage,
                "title more verbose",
                "summary with more words"
        );

        //when - then
        assertThrows(IllegalArgumentException.class, () -> profile.addTranslation(repeatedLanguageProfileTranslation));

    }
}
