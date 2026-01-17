package dev.alvarogil.portfolio.domain.model.role;

public record Technology(
        String name,
        String url
) {
    public Technology {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Technology name cannot be null or blank");
        }
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Technology url cannot be null or blank");
        }
    }
}
