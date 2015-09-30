package com.olenick.avatar.model.java8;

import java.text.DateFormatSymbols;
import java.util.Locale;

/**
 * Partly borrowed from Java 8
 */
public enum Month {
    /**
     * The singleton instance for the month of January with 31 days.
     * This has the numeric value of {@code 1}.
     */
    JANUARY,
    /**
     * The singleton instance for the month of February with 28 days, or 29 in a leap year.
     * This has the numeric value of {@code 2}.
     */
    FEBRUARY,
    /**
     * The singleton instance for the month of March with 31 days.
     * This has the numeric value of {@code 3}.
     */
    MARCH,
    /**
     * The singleton instance for the month of April with 30 days.
     * This has the numeric value of {@code 4}.
     */
    APRIL,
    /**
     * The singleton instance for the month of May with 31 days.
     * This has the numeric value of {@code 5}.
     */
    MAY,
    /**
     * The singleton instance for the month of June with 30 days.
     * This has the numeric value of {@code 6}.
     */
    JUNE,
    /**
     * The singleton instance for the month of July with 31 days.
     * This has the numeric value of {@code 7}.
     */
    JULY,
    /**
     * The singleton instance for the month of August with 31 days.
     * This has the numeric value of {@code 8}.
     */
    AUGUST,
    /**
     * The singleton instance for the month of September with 30 days.
     * This has the numeric value of {@code 9}.
     */
    SEPTEMBER,
    /**
     * The singleton instance for the month of October with 31 days.
     * This has the numeric value of {@code 10}.
     */
    OCTOBER,
    /**
     * The singleton instance for the month of November with 30 days.
     * This has the numeric value of {@code 11}.
     */
    NOVEMBER,
    /**
     * The singleton instance for the month of December with 31 days.
     * This has the numeric value of {@code 12}.
     */
    DECEMBER;
    /**
     * Private cache of all the constants.
     */
    private static final Month[] ENUMS = Month.values();

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Month} from an {@code int} value.
     * <p>
     * {@code Month} is an enum representing the 12 months of the year.
     * This factory allows the enum to be obtained from the {@code int} value.
     * The {@code int} value follows the ISO-8601 standard, from 1 (January) to 12 (December).
     *
     * @param month  the month-of-year to represent, from 1 (January) to 12 (December)
     * @return the month-of-year, not null
     * @throws DateTimeException if the month-of-year is invalid
     */
    public static Month of(int month) {
        if (month < 1 || month > 12) {
            throw new DateTimeException("Invalid value for MonthOfYear: " + month);
        }
        return ENUMS[month - 1];
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the month-of-year {@code int} value.
     * <p>
     * The values are numbered following the ISO-8601 standard,
     * from 1 (January) to 12 (December).
     *
     * @return the month-of-year, from 1 (January) to 12 (December)
     */
    public int getValue() {
        return ordinal() + 1;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the textual representation, such as 'Jan' or 'December'.
     * <p>
     * This returns the textual name used to identify the month-of-year,
     * suitable for presentation to the user.
     * The parameters control the style of the returned text and the locale.
     * <p>
     * If no textual mapping is found then the {@link #getValue() numeric value} is returned.
     *
     * @param style  the length of the text required, not null
     * @param locale  the locale to use, not null
     * @return the text value of the month-of-year, not null
     */
    public String getDisplayName(TextStyle style, Locale locale) {
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] months;
        if (style == TextStyle.NORMAL) {
            months = dfs.getMonths();
        } else {
            months = dfs.getShortMonths();
        }
        String month = null;
        int num = ordinal();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    static class DateTimeException extends RuntimeException {
        public DateTimeException(String message) {
            super(message);
        }
    }
}
