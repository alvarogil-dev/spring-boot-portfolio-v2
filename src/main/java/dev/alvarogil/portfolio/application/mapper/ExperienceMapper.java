package dev.alvarogil.portfolio.application.mapper;

import dev.alvarogil.portfolio.application.dto.ExperienceDto;
import dev.alvarogil.portfolio.application.dto.RoleDto;
import dev.alvarogil.portfolio.domain.model.experience.Experience;

import java.util.List;
import java.util.Locale;

public class ExperienceMapper {
    private ExperienceMapper() {
    }

    public static ExperienceDto toDto(Experience experience, Locale locale) {

        List<RoleDto> roleDtoList = experience.getRoles().stream()
                .map(role -> RoleMapper.toDto(role, locale))
                .toList();

        return new ExperienceDto(
                experience.getCompanyName(),
                experience.getCompanyUrl(),
                experience.getCompanyAvatarUrl(),
                experience.getPeriod().toFormattedString(locale),
                roleDtoList
        );
    }
}
