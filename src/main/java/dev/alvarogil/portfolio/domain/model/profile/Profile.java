package dev.alvarogil.portfolio.domain.model.profile;

import dev.alvarogil.portfolio.domain.model.Translatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Profile implements Translatable<ProfileTranslation> {
    private final String avatarUrl;
    private final String name;
    private final String email;
    private final String phone;
    private final String location;
    private List<ProfileTranslation> translations;

    public Profile(String avatarUrl, String name, String email, String phone, String location) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public List<ProfileTranslation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

    // domain logic

    @Override
    public Optional<ProfileTranslation> getTranslation(String language) {
        return translations.stream()
                .filter(profileTranslation -> profileTranslation.language().equals(language))
                .findFirst();
    }

    @Override
    public void addTranslation(ProfileTranslation profileTranslation) {
        String language = profileTranslation.language();

        boolean exists = translations.stream()
                        .anyMatch(profileTranslation1 -> profileTranslation1.language().equalsIgnoreCase(language));

        if(exists) {
            throw new IllegalArgumentException("Translation already exists for language: " + language);
        }

        translations.add(profileTranslation);
    }
}
