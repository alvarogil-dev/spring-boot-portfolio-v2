package dev.alvarogil.portfolio.application.service;

import dev.alvarogil.portfolio.domain.model.experience.Experience;
import dev.alvarogil.portfolio.domain.port.out.ExperienceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ListExperienceServiceTest {

    private ExperienceRepository experienceRepository;
    private ListExperienceService listExperienceService;

    @BeforeEach
    void setUp() {
        experienceRepository = mock(ExperienceRepository.class);
        listExperienceService = new ListExperienceService(experienceRepository);
    }

    @Test
    void givenExperiencesExist_whenExecute_thenReturnsAllExperiences() {
        // given
        Experience experience1 = mock(Experience.class);
        Experience experience2 = mock(Experience.class);
        List<Experience> expectedList = List.of(experience1, experience2);

        when(experienceRepository.findAll()).thenReturn(expectedList);

        // when
        List<Experience> result = listExperienceService.execute();

        // then
        assertEquals(expectedList, result);
        verify(experienceRepository).findAll();
    }
}
