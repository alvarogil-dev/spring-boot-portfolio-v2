package dev.alvarogil.portfolio.application.mapper;

import dev.alvarogil.portfolio.application.dto.TechnologyDto;
import dev.alvarogil.portfolio.domain.model.technology.Technology;

public class TechnologyMapper {
    private TechnologyMapper() {
    }

    public static TechnologyDto toDto(Technology technology) {
        return new TechnologyDto(technology.getName());
    }
}
