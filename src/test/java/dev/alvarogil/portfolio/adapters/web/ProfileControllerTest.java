package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.domain.model.profile.Profile;
import dev.alvarogil.portfolio.domain.model.profile.ProfileTranslation;
import dev.alvarogil.portfolio.domain.port.in.GetProfileUseCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetProfileUseCase getProfileUseCase;

    private Profile createProfile(String language) {
        Profile profile = new Profile("avatarUrl", "name", "email", "location");
        profile.addTranslation(new ProfileTranslation(language, "title", "summary"));
        return profile;
    }

    @Test
    void givenNonExistingProfile_whenGetProfile_thenRetrieve404() throws Exception {
        Locale locale = Locale.of("es");
        when(getProfileUseCase.execute()).thenThrow(new NoSuchElementException("Profile not found"));

        mockMvc.perform(get("/profile")
                        .header("Accept-Language", locale.getLanguage()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Profile not found"))
                .andExpect(jsonPath("$.path").value("/profile"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void givenExistingProfileAndNonExistingTranslations_whenGetProfile_thenReturn404() throws Exception {
        Locale locale = Locale.of("es");
        when(getProfileUseCase.execute()).thenThrow(new IllegalStateException("No translation available"));

        mockMvc.perform(get("/profile")
                        .header("Accept-Language", locale.getLanguage()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No translation available"))
                .andExpect(jsonPath("$.path").value("/profile"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @ParameterizedTest
    @MethodSource("languageCases")
    void givenProfileWithTranslationOrFallback_whenGetProfile_thenReturnCorrectTranslation(String requestedLang, String profileLang) throws Exception {
        Locale locale = Locale.of(requestedLang);
        Profile profile = createProfile(profileLang);

        when(getProfileUseCase.execute()).thenReturn(profile);

        mockMvc.perform(get("/profile")
                        .header("Accept-Language", locale.getLanguage()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avatarUrl").value("avatarUrl"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.location").value("location"))
                .andExpect(jsonPath("$.language").value(profileLang))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.summary").value("summary"));
    }

    private static Stream<Arguments> languageCases() {
        return Stream.of(
                arguments("es", "es"),  // Traducción exacta
                arguments("es", "en"),  // No existe en "es", usa fallback "en"
                arguments("xx", "en")   // Idioma inválido, usa fallback "en"
        );
    }
}
