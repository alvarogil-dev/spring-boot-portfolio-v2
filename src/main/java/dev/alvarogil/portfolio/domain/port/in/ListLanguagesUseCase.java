package dev.alvarogil.portfolio.domain.port.in;

import dev.alvarogil.portfolio.domain.model.language.Language;

import java.util.List;

public interface ListLanguagesUseCase {
    List<Language> execute();
}
