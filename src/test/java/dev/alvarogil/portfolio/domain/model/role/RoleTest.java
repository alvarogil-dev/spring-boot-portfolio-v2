package dev.alvarogil.portfolio.domain.model.role;

import dev.alvarogil.portfolio.domain.model.Period;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoleTest {

    private Role createEmptyRole() {
        return new Role(new Period(YearMonth.of(2025, 1), YearMonth.of(2025, 12)));
    }

    private void injectTranslations(Role role, List<RoleTranslation> translations) throws Exception {
        Field field = Role.class.getDeclaredField("translations");
        field.setAccessible(true);
        field.set(role, translations);
    }

    @Test
    void givenNonExistingLanguage_whenGetTranslation_thenReturnsEmpty() {
        //given
        Role role = createEmptyRole();

        //when
        var result = role.getTranslation("es");

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void givenExistingLanguage_whenGetTranslation_thenReturnsTranslation() throws Exception {
        //given
        String expectedLanguage = "es";

        Role role = createEmptyRole();

        RoleTranslation profileTranslation = new RoleTranslation(
                expectedLanguage,
                "title",
                "shortSummary",
                "longSummary");

        injectTranslations(role, List.of(profileTranslation));

        //when
        var result = role.getTranslation(expectedLanguage);

        //then
        assertThat(result)
                .isPresent()
                .map(RoleTranslation::language)
                .hasValue(expectedLanguage);
    }

    @Test
    void givenExistingLanguage_whenGetTranslationOrFallback_thenReturnsThatTranslation() throws Exception {
        //given
        String expectedLanguage = "es";

        Role role = createEmptyRole();

        RoleTranslation roleTranslation = new RoleTranslation(
                expectedLanguage,
                "title",
                "shortSummary",
                "longSummary");

        injectTranslations(role, List.of(roleTranslation));

        //when
        var result = role.getTranslationOrFallback(expectedLanguage);

        //then
        assertThat(result.language()).isEqualTo(expectedLanguage);
    }

    @Test
    void givenNonExistingLanguage_whenGetTranslationOrFallback_thenReturnsEnglish() throws Exception {
        //given
        String expectedLanguage = "es";
        String defaultLanguage = "en";

        Role role = createEmptyRole();

        RoleTranslation profileTranslation = new RoleTranslation(
                defaultLanguage,
                "title",
                "shortSummary",
                "longSummary");

        injectTranslations(role, List.of(profileTranslation));

        //when
        var result = role.getTranslationOrFallback(expectedLanguage);

        //then
        assertThat(result.language()).isEqualTo(defaultLanguage);
    }

    @Test
    void givenNoEnglishTranslation_whenGetTranslationOrFallback_thenThrowsException() {
        //given
        String expectedLanguage = "es";

        Role role = createEmptyRole();

        //when - then
        assertThrows(IllegalStateException.class, () -> role.getTranslationOrFallback(expectedLanguage));
    }

    @Test
    void givenNewLanguage_whenAddTranslation_thenAddsIt() {
        //given
        String expectedLanguage = "en";

        Role profile = createEmptyRole();

        RoleTranslation profileTranslation = new RoleTranslation(
                expectedLanguage,
                "title",
                "shortSummary",
                "longSummary");

        //when
        profile.addTranslation(profileTranslation);

        //then
        var translation = profile.getTranslation(expectedLanguage);

        assertThat(translation)
                .isPresent()
                .map(RoleTranslation::language)
                .hasValue(expectedLanguage);
    }

    @Test
    void givenDuplicateLanguage_whenAddTranslation_thenThrowsException() {
        //given
        String expectedLanguage = "en";

        Role role = createEmptyRole();

        RoleTranslation profileTranslation = new RoleTranslation(
                expectedLanguage,
                "title",
                "shortSummary",
                "longSummary");

        role.addTranslation(profileTranslation);

        RoleTranslation repeatedLanguageProfileTranslation = new RoleTranslation(
                expectedLanguage,
                "title more verbose",
                "short summary with more words",
                "long summary with more and more and more words"
        );

        //when - then
        assertThrows(IllegalArgumentException.class, () -> role.addTranslation(repeatedLanguageProfileTranslation));

    }
}
