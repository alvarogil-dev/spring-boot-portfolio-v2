package dev.alvarogil.portfolio.infrastructure.persistence;

import dev.alvarogil.portfolio.domain.model.Profile;
import dev.alvarogil.portfolio.domain.model.ProfileTranslation;
import dev.alvarogil.portfolio.domain.port.out.ProfileRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryProfileRepository implements ProfileRepository {

    private Profile profile;

    public InMemoryProfileRepository() {
        profile = new Profile(
                "https://www.alvarogil.dev/img/profile.png",
                "Álvaro Gil",
                "alvaro@alvarogil.dev",
                "Barcelona");

        ProfileTranslation profileTranslationEn = new ProfileTranslation("en", "DevOps | Backend", "I'm a devops and backend developer");
        ProfileTranslation profileTranslationSpa = new ProfileTranslation("es", "DevOps | Backend", "Soy devops y desarrollador backend");

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
