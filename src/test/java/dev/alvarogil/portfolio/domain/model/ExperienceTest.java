package dev.alvarogil.portfolio.domain.model;

import dev.alvarogil.portfolio.domain.model.experience.Experience;
import dev.alvarogil.portfolio.domain.model.role.Role;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExperienceTest
{
    @Test
    void givenSingleRole_whenGetPeriod_thenReturnSamePeriod() {
        //given
        Period period = new Period(YearMonth.of(2025, 1), YearMonth.of(2025, 12));
        Role role = new Role(period);
        Experience experience = new Experience(
                "companyName",
                "companyUrl",
                "companyAvatarUrl",
                role);
        //when
        Period result = experience.getPeriod();

        //then
        assertEquals(result, period);

    }

    @Test
    void givenMultipleRoles_whenGetPeriod_thenReturnCoveringPeriod() {
        //given
        YearMonth start = YearMonth.of(2025, 1);
        YearMonth end = YearMonth.of(2026, 12);

        Period period = new Period(start, YearMonth.of(2025, 12));
        Period period1 = new Period(YearMonth.of(2026, 1), end);
        Role role = new Role(period);
        Role role1 = new Role(period1);

        Experience experience = new Experience(
                "companyName",
                "companyUrl",
                "companyAvatarUrl",
                role);
        experience.addRole(role1);

        //when
        Period result = experience.getPeriod();

        //then
        Period expectedPeriod = new Period(start, end);
        assertEquals(result, expectedPeriod);
    }

    @Test
    void givenRoleWithOpenEnd_whenGetPeriod_thenReturnPeriodWithNullEnd() {
        //given
        YearMonth start = YearMonth.of(2025, 1);

        Period period = new Period(start, null);
        Role role = new Role(period);

        Experience experience = new Experience(
                "companyName",
                "companyUrl",
                "companyAvatarUrl",
                role);
        //when
        Period result = experience.getPeriod();

        //then
        Period expectedPeriod = new Period(start, null);
        assertEquals(result, expectedPeriod);
    }

    @Test
    void givenNullRole_whenConstructingExperience_thenThrows() {
        //when - then
        assertThrows(IllegalArgumentException.class, () -> new Experience(
                "companyName",
                "companyUrl",
                "companyAvatarUrl",
                null
        ));
    }


    @Test
    void givenNullRole_whenAddRole_thenThrows() {
        //given
        Period period = new Period(YearMonth.of(2025, 1), YearMonth.of(2025, 12));

        Role role = new Role(period);

        Experience experience = new Experience(
                "companyName",
                "companyUrl",
                "companyAvatarUrl",
                role
        );

        //when - then
        assertThrows(IllegalArgumentException.class, () -> experience.addRole(null));

    }

    @Test
    void givenNotNullRole_whenAddRole_thenRoleAppearsInListAndPeriodUpdates () {
        //given
        YearMonth start = YearMonth.of(2025, 1);

        Period period = new Period(start, YearMonth.of(2025, 2));

        Role role = new Role(period);

        Experience experience = new Experience(
                "companyName",
                "companyUrl",
                "companyAvatarUrl",
                role
        );

        //when
        YearMonth end = YearMonth.of(2025, 12);

        Period period1 = new Period(YearMonth.of(2025, 2), end);

        Role role1 = new Role(period1);

        experience.addRole(role1);

        //then
        Period expectedPeriod = new Period(start, end);

        assertTrue(experience.getRoles().contains(role1));
        assertEquals(expectedPeriod, experience.getPeriod());
    }

    @Test
    void getRoles_thenReturnsUnmodifiableList() {
        //given
        YearMonth start = YearMonth.of(2025, 1);

        Period period = new Period(start, YearMonth.of(2025, 2));

        Role role = new Role(period);

        Experience experience = new Experience(
                "companyName",
                "companyUrl",
                "companyAvatarUrl",
                role
        );

        //when - then
        List<Role> roles = experience.getRoles();

        assertThrows(UnsupportedOperationException.class, () -> roles.add(role));
    }
}
