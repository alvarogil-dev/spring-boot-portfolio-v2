package dev.alvarogil.portfolio.application.service;

import dev.alvarogil.portfolio.domain.model.experience.Experience;
import dev.alvarogil.portfolio.domain.port.in.ListExperiencesUseCase;
import dev.alvarogil.portfolio.domain.port.out.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListExperienceService implements ListExperiencesUseCase {
    private final ExperienceRepository experienceRepository;

    public ListExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public List<Experience> execute() {
        return experienceRepository.findAll();
    }
}
