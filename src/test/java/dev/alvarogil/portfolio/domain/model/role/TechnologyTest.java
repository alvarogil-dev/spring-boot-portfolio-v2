package dev.alvarogil.portfolio.domain.model.role;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TechnologyTest {

    @Test
    void whenValidData_thenCreatesTechnology() {
        Technology technology = new Technology("Spring Boot", "https://spring.io/projects/spring-boot");

        assertThat(technology.name()).isEqualTo("Spring Boot");
        assertThat(technology.url()).isEqualTo("https://spring.io/projects/spring-boot");
    }

    @Test
    void whenNameIsBlank_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Technology(" ", "https://example.com"));
    }

    @Test
    void whenNameIsNull_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Technology(null, "https://example.com"));
    }

    @Test
    void whenUrlIsBlank_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Technology("Spring Boot", " "));
    }

    @Test
    void whenUrlIsNull_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Technology("Spring Boot", null));
    }
}
