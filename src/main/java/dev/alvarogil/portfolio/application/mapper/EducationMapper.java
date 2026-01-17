package dev.alvarogil.portfolio.application.mapper;

import dev.alvarogil.portfolio.application.dto.EducationDto;
import dev.alvarogil.portfolio.domain.model.education.Education;

public class EducationMapper {
    private EducationMapper() {
    }

    public static EducationDto toDto(Education education) {
        return new EducationDto(
                education.getYear(),
                education.getTitle(),
                education.getInstitution(),
                education.getLocation(),
                education.getAchievements()
        );
    }
}
