package dev.alvarogil.portfolio.domain.port.in;

import dev.alvarogil.portfolio.domain.model.profile.Profile;

public interface GetProfileUseCase {
    Profile execute();
}
