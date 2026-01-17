package dev.alvarogil.portfolio.domain.port.in;

import dev.alvarogil.portfolio.domain.model.education.Education;

import java.util.List;

public interface ListEducationUseCase {
    List<Education> execute();
}
