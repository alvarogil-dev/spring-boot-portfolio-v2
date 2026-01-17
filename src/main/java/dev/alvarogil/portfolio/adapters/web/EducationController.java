package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.application.dto.EducationDto;
import dev.alvarogil.portfolio.application.mapper.EducationMapper;
import dev.alvarogil.portfolio.domain.model.education.Education;
import dev.alvarogil.portfolio.domain.port.in.ListEducationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/education")
@Tag(name = "Education", description = "Endpoints for education data")
public class EducationController {
    private final ListEducationUseCase listEducationUseCase;

    public EducationController(ListEducationUseCase listEducationUseCase) {
        this.listEducationUseCase = listEducationUseCase;
    }

    @Operation(summary = "List education", description = "Returns the list of education entries.")
    @GetMapping
    public ResponseEntity<List<EducationDto>> listEducation() {
        List<Education> educationEntries = listEducationUseCase.execute();
        List<EducationDto> dtoList = educationEntries.stream()
                .map(EducationMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}
