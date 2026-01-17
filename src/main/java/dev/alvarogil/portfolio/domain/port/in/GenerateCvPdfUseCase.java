package dev.alvarogil.portfolio.domain.port.in;

import java.util.Locale;

public interface GenerateCvPdfUseCase {
    byte[] execute(Locale locale);
}
