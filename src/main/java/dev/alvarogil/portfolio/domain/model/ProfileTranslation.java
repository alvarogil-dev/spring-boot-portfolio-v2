package dev.alvarogil.portfolio.domain.model;

public class ProfileTranslation {
    private final String language;
    private final String title;
    private final String summary;

    public ProfileTranslation(String language, String title, String summary) {
        this.language = language;
        this.title = title;
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }
}
