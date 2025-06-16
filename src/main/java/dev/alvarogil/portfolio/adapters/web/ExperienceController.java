package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.application.dto.ExperienceDto;
import dev.alvarogil.portfolio.application.mapper.ExperienceMapper;
import dev.alvarogil.portfolio.domain.model.experience.Experience;
import dev.alvarogil.portfolio.domain.port.in.ListExperiencesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/experience")
@Tag(name = "Experience", description = "Endpoints for public experience information")
public class ExperienceController {

    private final ListExperiencesUseCase listExperiencesUseCase;

    public ExperienceController(ListExperiencesUseCase listExperiencesUseCase) {
        this.listExperiencesUseCase = listExperiencesUseCase;
    }

    @Operation(
            summary = "List experiences",
            description = "Returns a list of experiences translated to the language specified in the Accept-Language header. Falls back to English if translation is not available."
    )
    @GetMapping
    public ResponseEntity<List<ExperienceDto>> getExperiences(@Parameter Locale locale) {
        List<Experience> experiences = listExperiencesUseCase.execute();

        List<ExperienceDto> dtoList = experiences.stream()
                .map(exp -> ExperienceMapper.toDto(exp, locale))
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}
