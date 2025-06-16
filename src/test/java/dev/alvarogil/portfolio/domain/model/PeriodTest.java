package dev.alvarogil.portfolio.domain.model;

import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


class PeriodTest {
    @Test
    void givenStartNull_whenInstantiatingPeriod_thenThrows() {
        //given
        YearMonth start = null;
        YearMonth end = YearMonth.now();

        //when - then
        assertThrows(IllegalArgumentException.class, () -> new Period(start, end));

    }

    @Test
    void givenStartEqualsEnd_whenInstantiatingPeriod_thenThrows() {
        //given
        YearMonth yearMonth = YearMonth.now();

        //when - then
        assertThrows(IllegalArgumentException.class, () -> new Period(yearMonth, yearMonth));

    }

    @Test
    void givenStartAfterEnd_whenInstantiatingPeriod_thenThrows() {
        //given
        YearMonth end = YearMonth.now();
        YearMonth start = end.plusYears(1);

        //when - then
        assertThrows(IllegalArgumentException.class, () -> new Period(start, end));
    }

    @Test
    void givenStartBeforeEnd_whenInstantiating_createsSuccesfully() {
        //given
        YearMonth start = YearMonth.now();
        YearMonth end = start.plusYears(1);

        //when - then
        assertDoesNotThrow(() -> new Period(start, end));
    }
    
    @Test
    void givenPeriods_whenSpan_isCommutative() {
        //given
        Period period1 = new Period(YearMonth.now(), YearMonth.now().plusYears(1));
        Period period2 = new Period(YearMonth.now().minusYears(1), YearMonth.now().plusYears(2));

        //when

        Period result = period1.span(period2);
        Period resultOtherWay = period2.span(period1);

        //then
        assertEquals(result, resultOtherWay);
    }

    @Test
    void givenPeriodsWithoutNullEnds_whenSpan_thenReturnsPeriodCoveringBothIntervals() {
        // given
        YearMonth start1 = YearMonth.of(2024, 1);
        YearMonth end1   = YearMonth.of(2024, 6);
        YearMonth start2 = YearMonth.of(2023, 12);
        YearMonth end2   = YearMonth.of(2024, 9);

        Period p1 = new Period(start1, end1);
        //p2 contains p1
        Period p2 = new Period(start2, end2);

        // when
        Period result = p1.span(p2);

        // then
        assertEquals(new Period(start2, end2), result);
    }

    @Test
    void givenPeriodsWithNullEnd_whenSpan_thenReturnsPeriodWithNullEnd() {
        // given
        YearMonth start1 = YearMonth.of(2024, 1);
        YearMonth start2 = YearMonth.of(2023, 12);
        YearMonth end2   = YearMonth.of(2024, 9);

        Period p1 = new Period(start1, null);
        Period p2 = new Period(start2, end2);

        // when
        Period result = p1.span(p2);

        // then
        assertEquals(new Period(start2, null), result);
    }

    @Test
    void givenEmptyList_whenCover_thenThrow() {
        //given
        List<Period> periods = Collections.emptyList();

        //when-then
        assertThrows(NoSuchElementException.class, () -> Period.cover(periods));
    }

    @Test
    void givenSinglePeriod_whenCover_thenReturnsSamePeriod() {
        //given
        Period period = new Period(YearMonth.now(), YearMonth.now().plusYears(1));
        List<Period> periods = List.of(period);

        //when
        Period result = Period.cover(periods);

        //then
        assertEquals(result, period);
    }

    @Test
    void givenPeriodsWithoutNullEnd_whenCover_thenReturnPeriodCoveringAllIntervals() {
        //given
        Period period = new Period(YearMonth.of(2024, 1), YearMonth.of(2024, 12));
        Period period1 = new Period(YearMonth.of(2024,12), YearMonth.of(2025, 12));
        Period period2 = new Period(YearMonth.of(2025,1), YearMonth.of(2025, 3));

        //when
        Period result = Period.cover(List.of(period, period1, period2));

        //then
        assertEquals(new Period(YearMonth.of(2024, 1), YearMonth.of(2025, 12)), result);
    }

    @Test
    void givenPeriodsWithNullEnd_whenCover_thenReturnPeriodWithNullEnd() {
        //given
        Period period = new Period(YearMonth.of(2024, 1), YearMonth.of(2024, 12));
        Period period1 = new Period(YearMonth.of(2024,12), YearMonth.of(2025, 12));
        Period period2 = new Period(YearMonth.of(2025,1), null);

        //when
        Period result = Period.cover(List.of(period, period1, period2));

        //then
        assertEquals(new Period(YearMonth.of(2024, 1), null), result);
    }
}
