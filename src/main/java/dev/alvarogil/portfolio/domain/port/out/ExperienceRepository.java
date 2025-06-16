package dev.alvarogil.portfolio.domain.port.out;

import dev.alvarogil.portfolio.domain.model.experience.Experience;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExperienceRepository {
    Experience save(Experience experience);
    List<Experience> findAll();
    Optional<Experience> findById(UUID id);
}
