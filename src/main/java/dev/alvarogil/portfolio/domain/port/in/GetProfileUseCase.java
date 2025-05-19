package dev.alvarogil.portfolio.domain.port.in;

import dev.alvarogil.portfolio.application.dto.ProfileDto;

import java.util.Locale;

public interface GetProfileUseCase {
    ProfileDto execute(Locale locale);
}
