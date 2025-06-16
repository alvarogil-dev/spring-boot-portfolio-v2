package dev.alvarogil.portfolio.adapters.web;

import dev.alvarogil.portfolio.application.dto.ExperienceDto;
import dev.alvarogil.portfolio.application.mapper.ExperienceMapper;
import dev.alvarogil.portfolio.domain.model.experience.Experience;
import dev.alvarogil.portfolio.domain.model.role.Role;
import dev.alvarogil.portfolio.domain.model.role.RoleTranslation;
import dev.alvarogil.portfolio.domain.model.Period;
import dev.alvarogil.portfolio.domain.port.in.ListExperiencesUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.YearMonth;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExperienceController.class)
class ExperienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListExperiencesUseCase listExperiencesUseCase;

    private Experience createExperience(String lang) {
        Period period = new Period(YearMonth.of(2020, 1), YearMonth.of(2021, 1));
        RoleTranslation translation = new RoleTranslation(lang, "title", "shortSummary", "longSummary");
        Role role = new Role(period);
        role.addTranslation(translation);
        return new Experience("Google", "https://google.com", "https://logo.com", role);
    }

    @Test
    void givenExperiencesExist_whenGetExperiences_thenReturnsTranslatedDtos() throws Exception {
        // given
        Locale locale = Locale.of("es");
        Experience experience = createExperience("es");

        when(listExperiencesUseCase.execute()).thenReturn(List.of(experience));

        ExperienceDto expectedDto = ExperienceMapper.toDto(experience, locale);

        // when - then
        mockMvc.perform(get("/experience")
                        .header("Accept-Language", locale.getLanguage()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].companyName").value(expectedDto.companyName()))
                .andExpect(jsonPath("$[0].companyUrl").value(expectedDto.companyUrl()))
                .andExpect(jsonPath("$[0].companyAvatarUrl").value(expectedDto.companyAvatarUrl()))
                .andExpect(jsonPath("$[0].period").value(expectedDto.period()))
                .andExpect(jsonPath("$[0].roles[0].title").value(expectedDto.roles().getFirst().title()))
                .andExpect(jsonPath("$[0].roles[0].shortSummary").value(expectedDto.roles().getFirst().shortSummary()))
                .andExpect(jsonPath("$[0].roles[0].longSummary").value(expectedDto.roles().getFirst().longSummary()))
                .andExpect(jsonPath("$[0].roles[0].startDate").value(expectedDto.roles().getFirst().startDate()))
                .andExpect(jsonPath("$[0].roles[0].endDate").value(expectedDto.roles().getFirst().endDate()))
                .andExpect(jsonPath("$[0].roles[0].language").value(expectedDto.roles().getFirst().language()));
    }
}
