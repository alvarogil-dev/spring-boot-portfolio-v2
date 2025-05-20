package dev.alvarogil.portfolio.domain.port.out;

import dev.alvarogil.portfolio.domain.model.profile.Profile;

import java.util.Optional;

public interface ProfileRepository {
    Optional<Profile> find();

    void save(Profile profile);
}
