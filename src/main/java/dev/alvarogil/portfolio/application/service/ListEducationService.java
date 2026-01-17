package dev.alvarogil.portfolio.application.service;

import dev.alvarogil.portfolio.domain.model.education.Education;
import dev.alvarogil.portfolio.domain.port.in.ListEducationUseCase;
import dev.alvarogil.portfolio.domain.port.out.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEducationService implements ListEducationUseCase {
    private final EducationRepository educationRepository;

    public ListEducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Override
    public List<Education> execute() {
        return educationRepository.findAll();
    }
}
