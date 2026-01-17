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
                new Language("Bilingüe en español y catalán"),
                new Language("C1 inglés")
        );
    }

    @Override
    public List<Language> findAll() {
        return List.copyOf(languages);
    }
}
