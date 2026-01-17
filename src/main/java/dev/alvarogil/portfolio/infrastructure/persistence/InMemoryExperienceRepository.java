package dev.alvarogil.portfolio.infrastructure.persistence;

import dev.alvarogil.portfolio.domain.model.Period;
import dev.alvarogil.portfolio.domain.model.experience.Experience;
import dev.alvarogil.portfolio.domain.model.role.Achievement;
import dev.alvarogil.portfolio.domain.model.role.AchievementTranslation;
import dev.alvarogil.portfolio.domain.model.role.Role;
import dev.alvarogil.portfolio.domain.model.role.RoleTranslation;
import dev.alvarogil.portfolio.domain.model.role.Technology;
import dev.alvarogil.portfolio.domain.port.out.ExperienceRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryExperienceRepository implements ExperienceRepository {

    private final List<Experience> experiences = new CopyOnWriteArrayList<>(); //avoid ConcurrentModificationException

    public InMemoryExperienceRepository() {

        RoleTranslation roleTranslation = new RoleTranslation(
                "en",
                "Backend developer",
                "Responsible of developing microservices for a medical tech device",
               "Responsible of developing microservices for a medical tech device" +
                       " but explained with a lot and a bunch of fancy words"
        );

        RoleTranslation roleTranslationSpanish = new RoleTranslation(
                "es",
                "Desarrollador Backend",
                "Responsable del desarrollo de microservicios para un dispositivo médico",
                "Responsable del desarrollo de microservicios para un dispositivo médico" +
                        " pero explicado con un montón de palabros complejos y otros no tanto"
        );

        Role role = new Role(new Period(YearMonth.of(2022, 3), null));
        role.addTranslation(roleTranslation);
        role.addTranslation(roleTranslationSpanish);
        role.addTechnology(new Technology("Spring Boot", "https://spring.io/projects/spring-boot"));
        role.addTechnology(new Technology("PostgreSQL", "https://www.postgresql.org/"));

        Achievement achievement = new Achievement();
        achievement.addTranslation(new AchievementTranslation(
                "en",
                "Reduced deployment time",
                "Automated the CI/CD pipeline to cut deployment time by 40%."
        ));
        achievement.addTranslation(new AchievementTranslation(
                "es",
                "Reducción del tiempo de despliegue",
                "Automatización del pipeline CI/CD para reducir el tiempo de despliegue en un 40%."
        ));
        role.addAchievement(achievement);

        Experience experience = new Experience(
                "ERNI",
                "https://www.betterask.erni/es-es1/",
                "https://www.betterask.erni/wp-content/uploads/2023/09/ERNI_logo_color-2048x532.png",
                role);

        experiences.add(experience);
    }

    @Override
    public Experience save(Experience experience) {
        experiences.add(experience);
        return experience;
    }

    @Override
    public List<Experience> findAll() {
        return List.copyOf(experiences); //immutable experiences list
    }

    @Override
    public Optional<Experience> findById(UUID id) {
        return experiences.stream()
                .filter(experience -> experience.getUuid().equals(id))
                .findFirst();
    }
}
