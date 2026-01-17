package dev.alvarogil.portfolio.domain.port.out;

import dev.alvarogil.portfolio.domain.model.language.Language;

import java.util.List;

public interface LanguageRepository {
    List<Language> findAll();
}
