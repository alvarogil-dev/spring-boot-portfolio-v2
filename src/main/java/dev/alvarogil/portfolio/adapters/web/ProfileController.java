package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.application.dto.ProfileDto;
import dev.alvarogil.portfolio.domain.port.in.GetProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/profile")
@Tag(name = "Profile", description = "Endpoints for public profile information")
public class ProfileController {

    private final GetProfileUseCase getProfileUseCase;

    public ProfileController(GetProfileUseCase getProfileUseCase) {
        this.getProfileUseCase = getProfileUseCase;
    }

    @Operation(
            summary = "Get public profile",
            description = "Returns the profile information translated to the language specified in the Accept-Language header. Falls back to English if translation is not available."
    )
    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(@Parameter Locale locale) {
        return ResponseEntity.ok(getProfileUseCase.execute(locale));
    }
}
