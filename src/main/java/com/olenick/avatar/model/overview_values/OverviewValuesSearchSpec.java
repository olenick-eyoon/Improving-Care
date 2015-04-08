package com.olenick.avatar.model.overview_values;

import java.io.File;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.MonthSpec;

/**
 * TODO: Use something like JSefa.
 */
public class OverviewValuesSearchSpec {
    private static final Set<String> NO_QUALIFIED_SURVEY_TYPES = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList("Avatar")));

    private File csvFile;
    private Long recordNumber;
    private String sectionTitle;
    private String systemCode;
    private String organizationCode;
    private MonthSpec fromMonthSpec;
    private MonthSpec toMonthSpec;
    private String surveyType;

    public OverviewValuesSearchSpec() {}

    public OverviewValuesSearchSpec(File csvFile, long recordNumber,
            String sectionTitle, String systemCode, String organizationCode,
            MonthSpec fromMonthSpec, MonthSpec toMonthSpec, String surveyType) {
        this.csvFile = csvFile;
        this.recordNumber = recordNumber;
        this.sectionTitle = sectionTitle;
        this.systemCode = systemCode;
        this.organizationCode = organizationCode;
        this.fromMonthSpec = fromMonthSpec;
        this.toMonthSpec = toMonthSpec;
        this.surveyType = surveyType;
    }

    public OverviewValuesSearchSpec(File csvFile, long recordNumber,
            String sectionTitle, String systemCode, String organizationCode,
            String fromYear, String fromMonth, String toYear, String toMonth,
            String surveyType) {
        this(csvFile, recordNumber, sectionTitle, systemCode, organizationCode,
                buildMonthSpec(fromYear, fromMonth), buildMonthSpec(toYear,
                        toMonth), surveyType);
    }

    private static MonthSpec buildMonthSpec(@NotNull final String year,
            @NotNull final String month) {
        return new MonthSpec(Month.of(Integer.valueOf(month)), year);
    }

    public String getHumanReadableMonthRange() {
        return this
                .getFormattedMonthRange(this.fromMonthSpec, this.toMonthSpec);
    }

    public boolean isQualifiedEnabled() {
        return !NO_QUALIFIED_SURVEY_TYPES.contains(this.getSurveyType());
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

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        OverviewValuesSearchSpec that = (OverviewValuesSearchSpec) o;

        if (csvFile != null ? !csvFile.equals(that.csvFile)
                : that.csvFile != null)
            return false;
        if (fromMonthSpec != null ? !fromMonthSpec.equals(that.fromMonthSpec)
                : that.fromMonthSpec != null)
            return false;
        if (organizationCode != null ? !organizationCode
                .equals(that.organizationCode) : that.organizationCode != null)
            return false;
        if (recordNumber != null ? !recordNumber.equals(that.recordNumber)
                : that.recordNumber != null)
            return false;
        if (sectionTitle != null ? !sectionTitle.equals(that.sectionTitle)
                : that.sectionTitle != null)
            return false;
        if (surveyType != null ? !surveyType.equals(that.surveyType)
                : that.surveyType != null)
            return false;
        if (systemCode != null ? !systemCode.equals(that.systemCode)
                : that.systemCode != null)
            return false;
        if (toMonthSpec != null ? !toMonthSpec.equals(that.toMonthSpec)
                : that.toMonthSpec != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = csvFile != null ? csvFile.hashCode() : 0;
        result = 31 * result
                + (recordNumber != null ? recordNumber.hashCode() : 0);
        result = 31 * result
                + (sectionTitle != null ? sectionTitle.hashCode() : 0);
        result = 31 * result + (systemCode != null ? systemCode.hashCode() : 0);
        result = 31 * result
                + (organizationCode != null ? organizationCode.hashCode() : 0);
        result = 31 * result
                + (fromMonthSpec != null ? fromMonthSpec.hashCode() : 0);
        result = 31 * result
                + (toMonthSpec != null ? toMonthSpec.hashCode() : 0);
        result = 31 * result + (surveyType != null ? surveyType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OverviewValuesSearchSpec{");
        sb.append("csv=").append(csvFile);
        sb.append(", recNo=").append(recordNumber);
        sb.append(", title='").append(sectionTitle).append('\'');
        sb.append(", sysCode='").append(systemCode).append('\'');
        sb.append(", orgCode='").append(organizationCode).append('\'');
        sb.append(", from=").append(fromMonthSpec);
        sb.append(", to=").append(toMonthSpec);
        sb.append(", survey='").append(surveyType).append('\'');
        sb.append('}');
        return sb.toString();
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
}
