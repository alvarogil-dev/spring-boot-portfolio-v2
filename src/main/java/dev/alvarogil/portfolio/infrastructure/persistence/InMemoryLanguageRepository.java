package dev.alvarogil.portfolio.infrastructure.persistence;

import dev.alvarogil.portfolio.domain.model.language.Language;
import dev.alvarogil.portfolio.domain.port.out.LanguageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryLanguageRepository implements LanguageRepository {

    private final List<Language> languages;

    public InMemoryLanguageRepository() {
        languages = List.of(
                new Language("Español", Language.Level.MOTHER_TONGUE),
                new Language("Catalán", Language.Level.MOTHER_TONGUE),
                new Language("Inglés", Language.Level.C1),
                new Language("Francés", Language.Level.A1)
        );
    }

    @Override
    public List<Language> findAll() {
        return List.copyOf(languages);
    }
}
