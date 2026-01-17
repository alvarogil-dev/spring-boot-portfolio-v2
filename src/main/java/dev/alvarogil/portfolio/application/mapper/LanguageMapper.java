package dev.alvarogil.portfolio.application.mapper;

import dev.alvarogil.portfolio.application.dto.LanguageDto;
import dev.alvarogil.portfolio.domain.model.language.Language;

public class LanguageMapper {
    private LanguageMapper() {
    }

    public static LanguageDto toDto(Language language) {
        return new LanguageDto(language.getName(), language.getLevel().getLabel());
    }
}
