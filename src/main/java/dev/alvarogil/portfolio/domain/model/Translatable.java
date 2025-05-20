package dev.alvarogil.portfolio.domain.model;

import java.util.Optional;

public interface Translatable<T> {
    String DEFAULT_LANGUAGE = "en";

    Optional<T> getTranslation(String language);

    default T getTranslationOrFallback(String language) {
        return getTranslation(language)
                .orElseGet(() -> getTranslation(DEFAULT_LANGUAGE)
                        .orElseThrow(() -> new IllegalStateException("No translation available")));
    }
}
