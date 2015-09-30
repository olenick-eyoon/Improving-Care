package com.olenick.avatar.model.report_values;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.MonthSpec;
import com.olenick.avatar.model.java8.Month;
import com.olenick.avatar.model.java8.TextStyle;

/**
 * TODO: Use something like JSefa.
 */
public class ReportValuesSearchSpec {
    private static final String ALL_ITEMS = "ALL";
    private static final String ITEMS_SEPARATOR = "\\|";
    private static final Set<String> NO_QUALIFIED_SURVEY_TYPES = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList("Avatar")));

    private File csvFile;
    private Long recordNumber;
    private Integer sheetNumber;
    private String sectionTitle;
    private String systemCode;
    private String organizationCode;
    private String surveyType;
    private String patientType;
    private MonthSpec fromMonthSpec;
    private MonthSpec toMonthSpec;
    private List<String> items;

    public ReportValuesSearchSpec() {
        this.items = Collections.emptyList();
    }

    public ReportValuesSearchSpec(File csvFile, long recordNumber,
                                  int sheetNumber, String sectionTitle, String systemCode,
                                  String organizationCode, String surveyType, String patientType,
                                  MonthSpec fromMonthSpec, MonthSpec toMonthSpec, List<String> items) {
        this.csvFile = csvFile;
        this.recordNumber = recordNumber;
        this.sheetNumber = sheetNumber;
        this.sectionTitle = sectionTitle;
        this.systemCode = systemCode;
        this.organizationCode = organizationCode;
        this.surveyType = surveyType;
        this.patientType = patientType;
        this.fromMonthSpec = fromMonthSpec;
        this.toMonthSpec = toMonthSpec;
        this.items = items;
    }

    public ReportValuesSearchSpec(File csvFile, long recordNumber,
                                  int sheetNumber, String sectionTitle, String systemCode,
                                  String organizationCode, String surveyType, String patientType,
                                  String fromYear, String fromMonth, String toYear, String toMonth,
                                  String items) {
        this(csvFile, recordNumber, sheetNumber, sectionTitle, systemCode,
                organizationCode, surveyType, patientType, buildMonthSpec(
                        fromYear, fromMonth), buildMonthSpec(toYear, toMonth),
                Arrays.asList(items.split(ITEMS_SEPARATOR)));
    }

    public String getHumanReadableMonthRange() {
        return this
                .getFormattedMonthRange(this.fromMonthSpec, this.toMonthSpec);
    }

    public boolean isQualifiedEnabled() {
        return !NO_QUALIFIED_SURVEY_TYPES.contains(this.getSurveyType());
    }

    public boolean isAllItems() {
        return this.items.size() == 1 & ALL_ITEMS.equals(this.items.get(0));
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

    public void setRecordNumber(Long recordNumber) {
        this.recordNumber = recordNumber;
    }

    public Integer getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(Integer sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public void setSheetNumber(String sheetNumber) {
        this.sheetNumber = Integer.valueOf(sheetNumber);
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

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
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

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setItems(String items) {
        this.items = Arrays.asList(items.split(ITEMS_SEPARATOR));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ReportValuesSearchSpec that = (ReportValuesSearchSpec) o;

        if (csvFile != null ? !csvFile.equals(that.csvFile)
                : that.csvFile != null)
            return false;
        if (recordNumber != null ? !recordNumber.equals(that.recordNumber)
                : that.recordNumber != null)
            return false;
        if (sheetNumber != null ? !sheetNumber.equals(that.sheetNumber)
                : that.sheetNumber != null)
            return false;
        if (sectionTitle != null ? !sectionTitle.equals(that.sectionTitle)
                : that.sectionTitle != null)
            return false;
        if (systemCode != null ? !systemCode.equals(that.systemCode)
                : that.systemCode != null)
            return false;
        if (organizationCode != null ? !organizationCode
                .equals(that.organizationCode) : that.organizationCode != null)
            return false;
        if (surveyType != null ? !surveyType.equals(that.surveyType)
                : that.surveyType != null)
            return false;
        if (patientType != null ? !patientType.equals(that.patientType)
                : that.patientType != null)
            return false;
        if (fromMonthSpec != null ? !fromMonthSpec.equals(that.fromMonthSpec)
                : that.fromMonthSpec != null)
            return false;
        if (toMonthSpec != null ? !toMonthSpec.equals(that.toMonthSpec)
                : that.toMonthSpec != null)
            return false;
        return !(items != null ? !items.equals(that.items) : that.items != null);

    }

    @Override
    public int hashCode() {
        int result = csvFile != null ? csvFile.hashCode() : 0;
        result = 31 * result
                + (recordNumber != null ? recordNumber.hashCode() : 0);
        result = 31 * result
                + (sheetNumber != null ? sheetNumber.hashCode() : 0);
        result = 31 * result
                + (sectionTitle != null ? sectionTitle.hashCode() : 0);
        result = 31 * result + (systemCode != null ? systemCode.hashCode() : 0);
        result = 31 * result
                + (organizationCode != null ? organizationCode.hashCode() : 0);
        result = 31 * result + (surveyType != null ? surveyType.hashCode() : 0);
        result = 31 * result
                + (patientType != null ? patientType.hashCode() : 0);
        result = 31 * result
                + (fromMonthSpec != null ? fromMonthSpec.hashCode() : 0);
        result = 31 * result
                + (toMonthSpec != null ? toMonthSpec.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReportValuesSearchSpec{");
        sb.append("csv=").append(csvFile);
        sb.append(", recNo=").append(recordNumber);
        sb.append(", sheetNo=").append(sheetNumber);
        sb.append(", title='").append(sectionTitle).append('\'');
        sb.append(", sysCode='").append(systemCode).append('\'');
        sb.append(", orgCode='").append(organizationCode).append('\'');
        sb.append(", survey='").append(surveyType).append('\'');
        sb.append(", patient='").append(patientType).append('\'');
        sb.append(", from=").append(fromMonthSpec);
        sb.append(", to=").append(toMonthSpec);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }

    private static MonthSpec buildMonthSpec(@NotNull final String year,
            @NotNull final String month) {
        return new MonthSpec(Month.of(Integer.valueOf(month)), year);
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
