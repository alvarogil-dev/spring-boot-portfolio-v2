package dev.alvarogil.portfolio.domain.port.out;

import dev.alvarogil.portfolio.domain.model.technology.Technology;

import java.util.List;

public interface TechnologyRepository {
    List<Technology> findAll();
}
