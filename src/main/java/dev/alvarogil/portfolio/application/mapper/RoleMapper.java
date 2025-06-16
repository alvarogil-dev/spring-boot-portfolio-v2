package dev.alvarogil.portfolio.application.mapper;

import dev.alvarogil.portfolio.application.dto.RoleDto;
import dev.alvarogil.portfolio.domain.model.role.Role;
import dev.alvarogil.portfolio.domain.model.role.RoleTranslation;

import java.util.Locale;

public class RoleMapper {
    private RoleMapper() {
    }

    public static RoleDto toDto(Role role, Locale locale) {
        RoleTranslation roleTranslation = role.getTranslationOrFallback(locale.getLanguage());

        return new RoleDto(
                roleTranslation.title(),
                roleTranslation.shortSummary(),
                roleTranslation.longSummary(),
                role.getPeriod().startFormatted(locale),
                role.getPeriod().endFormatted(locale),
                locale.getLanguage()
        );
    }
}
