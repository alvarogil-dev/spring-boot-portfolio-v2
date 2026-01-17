package dev.alvarogil.portfolio.domain.model.language;

public class Language {
    public enum Level {
        MOTHER_TONGUE("Lengua materna"),
        A1("A1"),
        A2("A2"),
        B1("B1"),
        B2("B2"),
        C1("C1"),
        C2("C2");

        private final String label;

        Level(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private final String name;
    private final Level level;

    public Language(String name, Level level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }
}
