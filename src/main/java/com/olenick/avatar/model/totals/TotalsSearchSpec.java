package com.olenick.avatar.model.totals;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.MonthSpec;

/**
 * TODO: Use something like JSefa.
 */
public class TotalsSearchSpec {
    private String systemCode;
    private String systemName;
    private MonthSpec fromMonthSpec;
    private MonthSpec toMonthSpec;
    private String surveyType;
    private String systemSearchString;

    public TotalsSearchSpec() {}

    public TotalsSearchSpec(String systemCode, String systemName,
            MonthSpec fromMonthSpec, MonthSpec toMonthSpec, String surveyType,
            String systemSearchString) {
        this.systemCode = systemCode;
        this.systemName = systemName;
        this.fromMonthSpec = fromMonthSpec;
        this.toMonthSpec = toMonthSpec;
        this.surveyType = surveyType;
        this.systemSearchString = systemSearchString;
    }

    public TotalsSearchSpec(String systemCode, String systemName,
            String fromYear, String fromMonth, String toYear, String toMonth,
            String surveyType, String systemSearchString) {
        this(systemCode, systemName, buildMonthSpec(fromYear, fromMonth),
                buildMonthSpec(toYear, toMonth), surveyType, systemSearchString);
    }

    private static MonthSpec buildMonthSpec(@NotNull final String year,
            @NotNull final String month) {
        return new MonthSpec(Month.of(Integer.valueOf(month)), year);
    }

    public String getHumanReadableMonthRange() {
        return this
                .getFormattedMonthRange(this.fromMonthSpec, this.toMonthSpec);
    }

    private String getFormattedMonthRange(MonthSpec from, MonthSpec to) {
        Month fromMonth = from.getMonth();
        String fromYear = from.getYear();
        Month toMonth = to.getMonth();
        String toYear = to.getYear();

        StringBuilder formattedRange = new StringBuilder();
        formattedRange.append(this.getShortMonth(fromMonth));
        if (fromYear.equals(toYear)) {
            if (fromMonth != toMonth) {
                formattedRange.append('-').append(this.getShortMonth(toMonth));
            }
            formattedRange.append(' ').append(this.getShortYear(fromYear));
        } else {
            formattedRange.append(' ').append(this.getShortYear(fromYear))
                    .append('-').append(this.getShortMonth(toMonth))
                    .append(' ').append(this.getShortYear(toYear));
        }
        return formattedRange.toString();
    }

    private String getShortMonth(Month month) {
        return month.getDisplayName(TextStyle.SHORT, Locale.US);
    }

    private String getShortYear(String year) {
        return "'" + year.substring(2);
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public MonthSpec getFromMonthSpec() {
        return fromMonthSpec;
    }

    public void setFromMonthSpec(MonthSpec fromMonthSpec) {
        this.fromMonthSpec = fromMonthSpec;
    }

    public void setFromMonthSpec(final String fromYear, final String fromMonth) {
        this.fromMonthSpec = buildMonthSpec(fromYear, fromMonth);
    }

    public MonthSpec getToMonthSpec() {
        return toMonthSpec;
    }

    public void setToMonthSpec(MonthSpec toMonthSpec) {
        this.toMonthSpec = toMonthSpec;
    }

    public void setToMonthSpec(final String toYear, final String toMonth) {
        this.toMonthSpec = buildMonthSpec(toYear, toMonth);
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getSystemSearchString() {
        return systemSearchString;
    }

    public void setSystemSearchString(String systemSearchString) {
        this.systemSearchString = systemSearchString;
    }
}
