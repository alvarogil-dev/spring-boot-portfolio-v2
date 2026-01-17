package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.domain.port.in.GenerateCvPdfUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/cv")
@Tag(name = "CV", description = "Endpoints for curriculum generation")
public class CvController {

    private final GenerateCvPdfUseCase generateCvPdfUseCase;

    public CvController(GenerateCvPdfUseCase generateCvPdfUseCase) {
        this.generateCvPdfUseCase = generateCvPdfUseCase;
    }

    @Operation(
            summary = "Download CV in PDF format",
            description = "Generates a PDF version of the CV using the current portfolio data."
    )
    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadCvPdf(@Parameter Locale locale) {
        byte[] pdfBytes = generateCvPdfUseCase.execute(locale);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"CV_Alvaro_Gil.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
