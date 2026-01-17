package dev.alvarogil.portfolio.application.service;

import dev.alvarogil.portfolio.domain.model.technology.Technology;
import dev.alvarogil.portfolio.domain.port.in.ListTechnologiesUseCase;
import dev.alvarogil.portfolio.domain.port.out.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTechnologiesService implements ListTechnologiesUseCase {
    private final TechnologyRepository technologyRepository;

    public ListTechnologiesService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public List<Technology> execute() {
        return technologyRepository.findAll();
    }
}
