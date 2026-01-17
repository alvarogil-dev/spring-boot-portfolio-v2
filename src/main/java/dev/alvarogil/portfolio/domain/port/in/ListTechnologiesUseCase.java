package dev.alvarogil.portfolio.domain.port.in;

import dev.alvarogil.portfolio.domain.model.technology.Technology;

import java.util.List;

public interface ListTechnologiesUseCase {
    List<Technology> execute();
}
