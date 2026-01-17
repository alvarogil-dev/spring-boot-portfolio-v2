package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.application.dto.LanguageDto;
import dev.alvarogil.portfolio.application.mapper.LanguageMapper;
import dev.alvarogil.portfolio.domain.model.language.Language;
import dev.alvarogil.portfolio.domain.port.in.ListLanguagesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/languages")
@Tag(name = "Languages", description = "Endpoints for language data")
public class LanguageController {
    private final ListLanguagesUseCase listLanguagesUseCase;

    public LanguageController(ListLanguagesUseCase listLanguagesUseCase) {
        this.listLanguagesUseCase = listLanguagesUseCase;
    }

    @Operation(summary = "List languages", description = "Returns the list of language entries.")
    @GetMapping
    public ResponseEntity<List<LanguageDto>> listLanguages() {
        List<Language> languages = listLanguagesUseCase.execute();
        List<LanguageDto> dtoList = languages.stream()
                .map(LanguageMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}
