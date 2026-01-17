package dev.alvarogil.portfolio.domain.model.role;

import dev.alvarogil.portfolio.domain.model.Period;
import dev.alvarogil.portfolio.domain.model.Translatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Role implements Translatable<RoleTranslation> {
    private Period period;
    private List<RoleTranslation> translations = new ArrayList<>();
    private final List<Technology> technologies = new ArrayList<>();
    private final List<Achievement> achievements = new ArrayList<>();

    public Role(Period period) {
        this.period = period;
    }

    public Period getPeriod() {
        return period;
    }

    public List<Technology> getTechnologies() {
        return Collections.unmodifiableList(technologies);
    }

    public List<Achievement> getAchievements() {
        return Collections.unmodifiableList(achievements);
    }

    @Override
    public Optional<RoleTranslation> getTranslation(String language) {
        return translations
                .stream()
                .filter(roleTranslation -> roleTranslation.language().equalsIgnoreCase(language))
                .findFirst();
    }

    @Override
    public void addTranslation(RoleTranslation translation) {
        String language = translation.language();

        boolean exists = translations.stream()
                .anyMatch(translation1 -> translation1.language().equalsIgnoreCase(language));

        if(exists) {
            throw new IllegalArgumentException("Translation already exists for language: " + language);
        }

        translations.add(translation);
    }

    public void addTechnology(Technology technology) {
        if (technology == null) throw new IllegalArgumentException("Technology cannot be null");

        if (technologies.contains(technology)) {
            throw new IllegalArgumentException("Technology already exists: " + technology.name());
        }

        technologies.add(technology);
    }

    public void addAchievement(Achievement achievement) {
        if (achievement == null) throw new IllegalArgumentException("Achievement cannot be null");

        achievements.add(achievement);
    }
}
