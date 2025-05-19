package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.application.dto.ProfileDto;
import dev.alvarogil.portfolio.domain.port.in.GetProfileUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Locale;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    private ProfileDto createProfileDto(String language) {
        return new ProfileDto(
                "avatarUrl",
                "name",
                "email",
                "location",
                language,
                "title",
                "summary"
                );
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetProfileUseCase getProfileUseCase;

    @Test
    void givenNonExistingProfile_whenGetProfile_thenRetrieve404() throws Exception {
        //given
        Locale locale = Locale.of("es");
        when(getProfileUseCase.execute(locale)).thenThrow(new NoSuchElementException("Profile not found"));

        //when - then
        mockMvc.perform(get("/profile")
                        .header("Accept-Language", "es")
                )
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
        //given
        Locale locale = Locale.of("es");

        when(getProfileUseCase.execute(locale)).thenThrow(new IllegalStateException("No translation available"));

        //when - then
        mockMvc.perform(get("/profile")
                        .header("Accept-Language", "es")
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No translation available"))
                .andExpect(jsonPath("$.path").value("/profile"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void givenExistingProfileAndNonExistingTranslationAndExistingDefaultTranslation_whenGetProfile_thenReturnFallbackProfile() throws Exception {
        //given
        Locale locale = Locale.of("es");

        ProfileDto profileDto = createProfileDto("en");

        when(getProfileUseCase.execute(locale)).thenReturn(profileDto);

        //when - then
        mockMvc.perform(get("/profile")
                        .header("Accept-Language", "es")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avatarUrl").value("avatarUrl"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.location").value("location"))
                .andExpect(jsonPath("$.language").value("en"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.summary").value("summary"));
    }

    @Test
    void givenInvalidLocale_whenGetProfile_thenReturnFallbackProfile() throws Exception {
        //given
        Locale locale = Locale.of("xx");

        ProfileDto profileDto = createProfileDto("en");

        when(getProfileUseCase.execute(locale)).thenReturn(profileDto);

        //when - then
        mockMvc.perform(get("/profile")
                        .header("Accept-Language", "xx")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avatarUrl").value("avatarUrl"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.location").value("location"))
                .andExpect(jsonPath("$.language").value("en"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.summary").value("summary"));
    }

    @Test
    void givenExistingProfileAndExistingTranslation_whenGetProfile_thenReturnProfile () throws Exception {
        //given
        Locale locale = Locale.of("es");

        ProfileDto profileDto = createProfileDto("es");

        when(getProfileUseCase.execute(locale)).thenReturn(profileDto);

        //when - then
        mockMvc.perform(get("/profile")
                        .header("Accept-Language", "es")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avatarUrl").value("avatarUrl"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.location").value("location"))
                .andExpect(jsonPath("$.language").value("es"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.summary").value("summary"));
    }

}
