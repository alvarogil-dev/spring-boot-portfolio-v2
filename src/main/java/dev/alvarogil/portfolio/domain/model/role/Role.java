package dev.alvarogil.portfolio.domain.model.role;

import dev.alvarogil.portfolio.domain.model.Period;
import dev.alvarogil.portfolio.domain.model.Translatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Role implements Translatable<RoleTranslation> {
    private Period period;
    private List<RoleTranslation> translations = new ArrayList<>();

    public Role(Period period) {
        this.period = period;
    }

    public Period getPeriod() {
        return period;
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
}
