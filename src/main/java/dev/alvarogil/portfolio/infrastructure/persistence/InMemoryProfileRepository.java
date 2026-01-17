package dev.alvarogil.portfolio.infrastructure.persistence;

import dev.alvarogil.portfolio.domain.model.profile.Profile;
import dev.alvarogil.portfolio.domain.model.profile.ProfileTranslation;
import dev.alvarogil.portfolio.domain.port.out.ProfileRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryProfileRepository implements ProfileRepository {

    private Profile profile;

    public InMemoryProfileRepository() {
        profile = new Profile(
                "https://www.alvarogil.dev/img/profile.png",
                "Álvaro Antonio Gil Vázquez",
                "alvaro@alvarogil.dev",
                "687063529",
                "Barcelona, 08830");

        ProfileTranslation profileTranslationEn = new ProfileTranslation(
                "en",
                "DevOps | Backend | Software Architect | Tech Lead | IT Mentor | Freelancer",
                "DevOps and backend professional with over 7 years of experience in Java, Spring Boot, Docker, " +
                        "Kubernetes, Azure, and more. I help companies and individuals achieve their tech goals " +
                        "through innovative solutions and professional mentorship."
        );
        ProfileTranslation profileTranslationSpa = new ProfileTranslation(
                "es",
                "DevOps | Backend | Arquitecto de Software | Tech Lead | Mentor IT | Freelancer",
                "Profesional DevOps y backend con más de 7 años de experiencia en Java, Spring Boot, Docker, " +
                        "Kubernetes, Azure y más. Ayudo a empresas y particulares a alcanzar sus objetivos " +
                        "tecnológicos con soluciones innovadoras y mentoría profesional."
        );

        profile.addTranslation(profileTranslationEn);
        profile.addTranslation(profileTranslationSpa);
    }

    @Override
    public Optional<Profile> find() {
        return Optional.of(profile);
    }

    @Override
    public void save(Profile profile) {
        this.profile = profile;
    }
}
