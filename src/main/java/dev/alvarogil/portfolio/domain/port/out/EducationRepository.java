package dev.alvarogil.portfolio.domain.port.out;

import dev.alvarogil.portfolio.domain.model.education.Education;

import java.util.List;

public interface EducationRepository {
    List<Education> findAll();
}
