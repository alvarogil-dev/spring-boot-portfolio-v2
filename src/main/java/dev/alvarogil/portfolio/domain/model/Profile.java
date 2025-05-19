package dev.alvarogil.portfolio.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Profile {
    private String avatarUrl;
    private String name;
    private String email;
    private String location;
    private List<ProfileTranslation> translations;

    public Profile(String avatarUrl, String name, String email, String location) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.email = email;
        this.location = location;
        translations = new ArrayList<>();
    }

    // getters only for secured reading or to create DTOs

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public List<ProfileTranslation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

    // domain logic

    public Optional<ProfileTranslation> getTranslation(String language) {
        return translations.stream()
                .filter(profileTranslation -> profileTranslation.getLanguage().equals(language))
                .findFirst();
    }

    public ProfileTranslation getTranslationOrFallback(String language) {
        return getTranslation(language)
                .orElseGet(() -> getTranslation("en")
                        .orElseThrow(() -> new IllegalStateException("No translation available")));
    }

    public void addTranslation(ProfileTranslation profileTranslation) {
        String language = profileTranslation.getLanguage();

        boolean exists = translations.stream()
                        .anyMatch(profileTranslation1 -> profileTranslation1.getLanguage().equalsIgnoreCase(language));

        if(exists) {
            throw new IllegalArgumentException("Translation already exists for language: " + language);
        }

        translations.add(profileTranslation);
    }
}
