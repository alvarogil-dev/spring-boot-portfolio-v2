package dev.alvarogil.portfolio.domain.model.education;

import java.util.Collections;
import java.util.List;

public class Education {
    private final String year;
    private final String title;
    private final String institution;
    private final String location;
    private final List<String> achievements;

    public Education(String year, String title, String institution, String location, List<String> achievements) {
        this.year = year;
        this.title = title;
        this.institution = institution;
        this.location = location;
        this.achievements = List.copyOf(achievements);
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getInstitution() {
        return institution;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getAchievements() {
        return Collections.unmodifiableList(achievements);
    }
}
