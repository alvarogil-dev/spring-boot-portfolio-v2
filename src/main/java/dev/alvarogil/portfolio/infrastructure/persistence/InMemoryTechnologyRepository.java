package dev.alvarogil.portfolio.infrastructure.persistence;

import dev.alvarogil.portfolio.domain.model.technology.Technology;
import dev.alvarogil.portfolio.domain.port.out.TechnologyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryTechnologyRepository implements TechnologyRepository {

    private final List<Technology> technologies;

    public InMemoryTechnologyRepository() {
        technologies = List.of(
                new Technology("Java"),
                new Technology("Spring Boot"),
                new Technology("Docker"),
                new Technology("Kubernetes"),
                new Technology("Azure"),
                new Technology("AWS"),
                new Technology("Terraform"),
                new Technology("Jenkins"),
                new Technology("Kafka"),
                new Technology("RabbitMQ"),
                new Technology("MongoDB"),
                new Technology("PostgreSQL"),
                new Technology("React"),
                new Technology("NodeJS"),
                new Technology("Git"),
                new Technology("Ansible")
        );
    }

    @Override
    public List<Technology> findAll() {
        return List.copyOf(technologies);
    }
}
