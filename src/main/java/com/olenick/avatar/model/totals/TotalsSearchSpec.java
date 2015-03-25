package com.olenick.avatar.model.totals;

import java.io.File;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.MonthSpec;

/**
 * TODO: Use something like JSefa.
 */
public class TotalsSearchSpec {
    private File csvFile;
    private Long recordNumber;
    private String systemCode;
    private String systemName;
    private String systemSearchString;
    private MonthSpec fromMonthSpec;
    private MonthSpec toMonthSpec;
    private String surveyType;

    public TotalsSearchSpec() {}

    public TotalsSearchSpec(File csvFile, long recordNumber, String systemCode,
            String systemName, MonthSpec fromMonthSpec, MonthSpec toMonthSpec,
            String surveyType, String systemSearchString) {
        this.csvFile = csvFile;
        this.recordNumber = recordNumber;
        this.systemCode = systemCode;
        this.systemName = systemName;
        this.systemSearchString = systemSearchString;
        this.fromMonthSpec = fromMonthSpec;
        this.toMonthSpec = toMonthSpec;
        this.surveyType = surveyType;
    }

    public TotalsSearchSpec(File csvFile, long recordNumber, String systemCode,
            String systemName, String fromYear, String fromMonth,
            String toYear, String toMonth, String surveyType,
            String systemSearchString) {
        this(csvFile, recordNumber, systemCode, systemName, buildMonthSpec(
                fromYear, fromMonth), buildMonthSpec(toYear, toMonth),
                surveyType, systemSearchString);
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

    public File getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    public Long getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(long recordNumber) {
        this.recordNumber = recordNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalsSearchSpec that = (TotalsSearchSpec) o;

        if (csvFile != null ? !csvFile.equals(that.csvFile) : that.csvFile != null)
            return false;
        if (fromMonthSpec != null ? !fromMonthSpec.equals(that.fromMonthSpec) : that.fromMonthSpec != null)
            return false;
        if (recordNumber != null ? !recordNumber.equals(that.recordNumber) : that.recordNumber != null)
            return false;
        if (surveyType != null ? !surveyType.equals(that.surveyType) : that.surveyType != null)
            return false;
        if (systemCode != null ? !systemCode.equals(that.systemCode) : that.systemCode != null)
            return false;
        if (systemName != null ? !systemName.equals(that.systemName) : that.systemName != null)
            return false;
        if (systemSearchString != null ? !systemSearchString.equals(that.systemSearchString) : that.systemSearchString != null)
            return false;
        if (toMonthSpec != null ? !toMonthSpec.equals(that.toMonthSpec) : that.toMonthSpec != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = csvFile != null ? csvFile.hashCode() : 0;
        result = 31 * result + (recordNumber != null ? recordNumber.hashCode() : 0);
        result = 31 * result + (systemCode != null ? systemCode.hashCode() : 0);
        result = 31 * result + (systemName != null ? systemName.hashCode() : 0);
        result = 31 * result + (systemSearchString != null ? systemSearchString.hashCode() : 0);
        result = 31 * result + (fromMonthSpec != null ? fromMonthSpec.hashCode() : 0);
        result = 31 * result + (toMonthSpec != null ? toMonthSpec.hashCode() : 0);
        result = 31 * result + (surveyType != null ? surveyType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TotalsSearchSpec{");
        sb.append("csv=").append(csvFile);
        sb.append(", recNo=").append(recordNumber);
        sb.append(", sysCode='").append(systemCode).append('\'');
        sb.append(", sysName='").append(systemName).append('\'');
        sb.append(", sysSearch='").append(systemSearchString).append('\'');
        sb.append(", from=").append(fromMonthSpec);
        sb.append(", to=").append(toMonthSpec);
        sb.append(", survey='").append(surveyType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
