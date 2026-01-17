package dev.alvarogil.portfolio.domain.model.role;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AchievementTest {

    @Test
    void givenNonExistingLanguage_whenGetTranslation_thenReturnsEmpty() {
        Achievement achievement = new Achievement();

        var result = achievement.getTranslation("es");

        assertThat(result).isEmpty();
    }

    @Test
    void givenExistingLanguage_whenGetTranslation_thenReturnsTranslation() {
        String expectedLanguage = "es";
        Achievement achievement = new Achievement();

        AchievementTranslation translation = new AchievementTranslation(
                expectedLanguage,
                "Titulo",
                "Descripcion"
        );

        achievement.addTranslation(translation);

        var result = achievement.getTranslation(expectedLanguage);

        assertThat(result)
                .isPresent()
                .map(AchievementTranslation::language)
                .hasValue(expectedLanguage);
    }

    @Test
    void givenExistingLanguage_whenGetTranslationOrFallback_thenReturnsThatTranslation() {
        String expectedLanguage = "es";
        Achievement achievement = new Achievement();

        AchievementTranslation translation = new AchievementTranslation(
                expectedLanguage,
                "Titulo",
                "Descripcion"
        );

        achievement.addTranslation(translation);

        var result = achievement.getTranslationOrFallback(expectedLanguage);

        assertThat(result.language()).isEqualTo(expectedLanguage);
    }

    @Test
    void givenNonExistingLanguage_whenGetTranslationOrFallback_thenReturnsEnglish() {
        String expectedLanguage = "es";
        String defaultLanguage = "en";
        Achievement achievement = new Achievement();

        AchievementTranslation translation = new AchievementTranslation(
                defaultLanguage,
                "Title",
                "Description"
        );

        achievement.addTranslation(translation);

        var result = achievement.getTranslationOrFallback(expectedLanguage);

        assertThat(result.language()).isEqualTo(defaultLanguage);
    }

    @Test
    void givenNoEnglishTranslation_whenGetTranslationOrFallback_thenThrowsException() {
        Achievement achievement = new Achievement();

        assertThrows(IllegalStateException.class, () -> achievement.getTranslationOrFallback("es"));
    }

    @Test
    void givenDuplicateLanguage_whenAddTranslation_thenThrowsException() {
        Achievement achievement = new Achievement();

        AchievementTranslation translation = new AchievementTranslation(
                "en",
                "Title",
                "Description"
        );

        achievement.addTranslation(translation);

        AchievementTranslation repeatedTranslation = new AchievementTranslation(
                "en",
                "Another title",
                "Another description"
        );

        assertThrows(IllegalArgumentException.class, () -> achievement.addTranslation(repeatedTranslation));
    }
}
