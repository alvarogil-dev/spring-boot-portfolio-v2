package dev.alvarogil.portfolio.infrastructure.persistence;

import dev.alvarogil.portfolio.domain.model.education.Education;
import dev.alvarogil.portfolio.domain.port.out.EducationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryEducationRepository implements EducationRepository {

    private final List<Education> educationEntries;

    public InMemoryEducationRepository() {
        educationEntries = List.of(
                new Education(
                        "2021",
                        "Grado en Ingeniería Informática (Pendiente TFG)",
                        "Universitat Politècnica de Catalunya (UPC)",
                        "Barcelona, España",
                        List.of()
                ),
                new Education(
                        "2017",
                        "Cursillo formativo Enfocat (Java/SQL)",
                        "Fundación Esplai",
                        "Barcelona, España",
                        List.of()
                ),
                new Education(
                        "2014",
                        "Título de Bachillerato",
                        "Instituto Baldiri Guilera",
                        "El Prat de Llobregat, España",
                        List.of(
                                "Premio \"Recerca Jove PRJ 2014\" a los logros académicos",
                                "Premio \"Fòrum de treballs de recerca del Prat\" a los logros académicos"
                        )
                )
        );
    }

    @Override
    public List<Education> findAll() {
        return List.copyOf(educationEntries);
    }
}
