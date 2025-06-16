package dev.alvarogil.portfolio.domain.model.role;

public record RoleTranslation(
        String language,
        String title,
        String shortSummary,
        String longSummary
)
{
}
