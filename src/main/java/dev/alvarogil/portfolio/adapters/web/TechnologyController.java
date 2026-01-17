package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.application.dto.TechnologyDto;
import dev.alvarogil.portfolio.application.mapper.TechnologyMapper;
import dev.alvarogil.portfolio.domain.model.technology.Technology;
import dev.alvarogil.portfolio.domain.port.in.ListTechnologiesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/technologies")
@Tag(name = "Technologies", description = "Endpoints for technology data")
public class TechnologyController {
    private final ListTechnologiesUseCase listTechnologiesUseCase;

    public TechnologyController(ListTechnologiesUseCase listTechnologiesUseCase) {
        this.listTechnologiesUseCase = listTechnologiesUseCase;
    }

    @Operation(summary = "List technologies", description = "Returns the list of technologies.")
    @GetMapping
    public ResponseEntity<List<TechnologyDto>> listTechnologies() {
        List<Technology> technologies = listTechnologiesUseCase.execute();
        List<TechnologyDto> dtoList = technologies.stream()
                .map(TechnologyMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}
