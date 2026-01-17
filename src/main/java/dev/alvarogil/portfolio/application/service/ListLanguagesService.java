package dev.alvarogil.portfolio.application.service;

import dev.alvarogil.portfolio.domain.model.language.Language;
import dev.alvarogil.portfolio.domain.port.in.ListLanguagesUseCase;
import dev.alvarogil.portfolio.domain.port.out.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListLanguagesService implements ListLanguagesUseCase {
    private final LanguageRepository languageRepository;

    public ListLanguagesService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> execute() {
        return languageRepository.findAll();
    }
}
