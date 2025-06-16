package dev.alvarogil.portfolio.domain.port.in;

import dev.alvarogil.portfolio.domain.model.experience.Experience;

import java.util.List;

public interface ListExperiencesUseCase {
    List<Experience> execute();
}
