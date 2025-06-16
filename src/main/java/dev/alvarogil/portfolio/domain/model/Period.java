package dev.alvarogil.portfolio.domain.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public record Period(YearMonth start, YearMonth end) {

    public Period {
        if(start == null) throw new IllegalArgumentException("Start date can't be null");
        if(end != null) {
            if(start.equals(end)) throw new IllegalArgumentException("End date can't be the same as start date");
            if (start.isAfter(end)) throw new IllegalArgumentException("Start date can't be posterior to end date");
        }
    }

    // Returns the period that covers both intervals
    public Period span(Period other) {
        YearMonth spannedStart = this.start.isBefore(other.start) ? this.start : other.start;
        YearMonth spannedEnd;

        // if end null, period not finished
        if(this.end == null || other.end == null) {
            spannedEnd = null;
        }
        else {
            spannedEnd = this.end.isBefore(other.end) ? other.end : this.end;
        }

        return new Period(spannedStart, spannedEnd);
    }

    public String startFormatted(Locale locale) {
        return start.format(DateTimeFormatter.ofPattern("MMM yyyy", locale));
    }

    public String endFormatted(Locale locale) {
        return (end != null)
                ? end.format(DateTimeFormatter.ofPattern("MMM yyyy", locale))
                : localizedPresent(locale);
    }

    public String toFormattedString(Locale locale) {
        return startFormatted(locale) + " - " + endFormatted(locale);
    }

    private String localizedPresent(Locale locale) {
        return locale.getLanguage().equalsIgnoreCase("es") ? "Actualidad" : "Present";
    }

    // returns the period that covers all the intervals
    public static Period cover(List<Period> periods) {
        return periods.stream()
                .reduce(Period::span)
                .orElseThrow(() -> new NoSuchElementException("List of periods to cover can't be empty nor null."));
    }
}
