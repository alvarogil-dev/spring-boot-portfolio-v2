package dev.alvarogil.portfolio.domain.model.role;

import dev.alvarogil.portfolio.domain.model.Translatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Achievement implements Translatable<AchievementTranslation> {
    private final List<AchievementTranslation> translations = new ArrayList<>();

    public List<AchievementTranslation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

    @Override
    public Optional<AchievementTranslation> getTranslation(String language) {
        return translations.stream()
                .filter(achievementTranslation -> achievementTranslation.language().equalsIgnoreCase(language))
                .findFirst();
    }

    @Override
    public void addTranslation(AchievementTranslation translation) {
        String language = translation.language();

        boolean exists = translations.stream()
                .anyMatch(translation1 -> translation1.language().equalsIgnoreCase(language));

        if (exists) {
            throw new IllegalArgumentException("Translation already exists for language: " + language);
        }

        translations.add(translation);
    }
}
