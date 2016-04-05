package com.olenick.avatar.model.report_values;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.exceptions.ParseException;
import com.olenick.avatar.model.Environment;
import com.olenick.avatar.model.MonthSpec;
import com.olenick.avatar.model.java8.Month;
import com.olenick.avatar.model.java8.TextStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Use something like JSefa.
 */
public class ReportValuesSearchSpec {
    private static final Logger log = LoggerFactory.getLogger(ReportValuesSearchSpec.class);
    private static final String ALL_ITEMS = "ALL";
    private static final String ITEMS_SEPARATOR = "\\|";
    private static final Set<String> NO_QUALIFIED_SURVEY_TYPES = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList("Avatar")));
    private static final Set<String> VALID_ENVIRONMENTS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("QA", "PRODUCTION", "DEFAULT")));
    private static final Environment[][] ENVIRONMENTS_PER_SHEET = new Environment[][]{
            new Environment[]{Environment.PRODUCTION, Environment.QA},
            new Environment[]{Environment.QA}};

    private File csvFile;
    private Long recordNumber;
    private Long rowNumberInCSV;
    private Integer sheetNumber;
    private String sectionTitle;
    private String systemCode;
    private String organizationCode;
    private String surveyType;
    private String patientType;
    private MonthSpec fromMonthSpec;
    private MonthSpec toMonthSpec;
    private List<String> items;
    private List<Environment> environments;

    public ReportValuesSearchSpec() {
        this.items = Collections.emptyList();
        this.environments = new ArrayList<>();
    }

    public ReportValuesSearchSpec(File csvFile, long recordNumber, long rowNumberInCSV,
                                  int sheetNumber, String sectionTitle, String systemCode,
                                  String organizationCode, String surveyType, String patientType,
                                  MonthSpec fromMonthSpec, MonthSpec toMonthSpec,
                                  List<String> items, String environments) throws ParseException {
        this.setCsvFile(csvFile);
        this.setRecordNumber(recordNumber);
        this.setRowNumberInCSV(rowNumberInCSV);
        this.setSheetNumber(sheetNumber);
        this.setSectionTitle(sectionTitle);
        this.setSystemCode(systemCode);
        this.setOrganizationCode(organizationCode);
        this.setSurveyType(surveyType);
        this.setPatientType(patientType);
        this.setFromMonthSpec(fromMonthSpec);
        this.setToMonthSpec(toMonthSpec);
        this.setItems(items);
        this.setEnvironments(environments);
    }

    public ReportValuesSearchSpec(File csvFile, long recordNumber, long rowNumberInCSV,
                                  int sheetNumber, String sectionTitle, String systemCode,
                                  String organizationCode, String surveyType, String patientType,
                                  MonthSpec fromMonthSpec, MonthSpec toMonthSpec,
                                  List<String> items, List<Environment> environments) throws ParseException {
        this.setCsvFile(csvFile);
        this.setRecordNumber(recordNumber);
        this.setRowNumberInCSV(rowNumberInCSV);
        this.setSheetNumber(sheetNumber);
        this.setSectionTitle(sectionTitle);
        this.setSystemCode(systemCode);
        this.setOrganizationCode(organizationCode);
        this.setSurveyType(surveyType);
        this.setPatientType(patientType);
        this.setFromMonthSpec(fromMonthSpec);
        this.setToMonthSpec(toMonthSpec);
        this.setItems(items);
        this.setEnvironments(environments);
    }

    public ReportValuesSearchSpec(File csvFile, long recordNumber, long rowNumberInCSV,
                                  int sheetNumber, String sectionTitle, String systemCode,
                                  String organizationCode, String surveyType, String patientType,
                                  String fromYear, String fromMonth, String toYear, String toMonth,
                                  String items, String environments) throws ParseException {
        this(csvFile, recordNumber, rowNumberInCSV, sheetNumber, sectionTitle, systemCode,
                organizationCode, surveyType, patientType, buildMonthSpec(
                        fromYear, fromMonth), buildMonthSpec(toYear, toMonth),
                Arrays.asList(items.split(ITEMS_SEPARATOR)), environments);
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

    public Long getRowNumberInCSV() {
        return recordNumber;
    }

    public void setRowNumberInCSV(Long rowNumber) {
        this.rowNumberInCSV = rowNumber;
    }

    public Integer getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(Integer sheetNumber) throws ParseException {
        if (sheetNumber >= 0 && sheetNumber < ENVIRONMENTS_PER_SHEET.length)
            this.sheetNumber = sheetNumber;
        else
            throw new ParseException("Sheet number value: " + sheetNumber + ", refers to an invalid worksheet.");
    }

    public void setSheetNumber(String sheetNumber) throws ParseException {
        try{
            this.setSheetNumber(Integer.valueOf(sheetNumber));
        } catch (NumberFormatException e) {
            throw new ParseException("Sheet number value: " + sheetNumber + ", is not an integer number.");
        }
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

    public List<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<Environment> environment) { this.environments = environment; }

    public void setEnvironments(String environments) throws ParseException {
        int originalSize, afterHashedSize;

        List<String> environmentList = Arrays.asList(environments.split(ITEMS_SEPARATOR));
        originalSize = environmentList.size();

        //All to uppercase
        for(int i = 0; i < environmentList.size(); i++) {
            environmentList.set(i, environmentList.get(i).toUpperCase());
        }

        //Check for duplicates
        HashSet<String> noDuplicates = new HashSet(environmentList);
        afterHashedSize = noDuplicates.size();

        if (originalSize != afterHashedSize) {
            throw new ParseException("Environment list parameter has duplicated values: " + environments);
        }

        this.environments.clear();

        //Check for invalid options not available in VALID_ENVIRONMENTS
        for (String env : environmentList) {
            try {
                this.environments.add(Environment.valueOf(env));
            } catch (IllegalArgumentException e) {
                //DEFAULT is not a valid environment as is, but we handle it here
                if (env.equals("DEFAULT")) {
                    if (this.environments.size() > 0)
                        log.warn("Enviroment list parameter has the DEFAULT value among others. " +
                                "Other values will be discarded if DEFAULT is present.");
                    this.environments.clear();
                    this.environments.addAll(Arrays.asList(ENVIRONMENTS_PER_SHEET[this.sheetNumber]));
                    break;
                } else {
                    throw new ParseException(
                            "Environment list parameter has invalid values. Valid values are: " + VALID_ENVIRONMENTS
                                    + ". Provided values: " + environments);
                }
            }
        }
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
        if (rowNumberInCSV != null ? !rowNumberInCSV.equals(that.rowNumberInCSV)
                : that.rowNumberInCSV != null)
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
        if (environments != null ? !environments.equals(that.environments) : that.environments != null)
            return false;
        return !(items != null ? !items.equals(that.items) : that.items != null);

    }

    @Override
    public int hashCode() {
        int result = csvFile != null ? csvFile.hashCode() : 0;
        result = 31 * result
                + (recordNumber != null ? recordNumber.hashCode() : 0);
        result = 31 * result
                + (rowNumberInCSV != null ? rowNumberInCSV.hashCode() : 0);
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
        result = 31 * result + (environments != null ? environments.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReportValuesSearchSpec{");
        sb.append("csv=").append(csvFile);
        sb.append(", recNo=").append(recordNumber);
        sb.append(", rowNoInCSV=").append(rowNumberInCSV);
        sb.append(", sheetNo=").append(sheetNumber);
        sb.append(", title='").append(sectionTitle).append('\'');
        sb.append(", sysCode='").append(systemCode).append('\'');
        sb.append(", orgCode='").append(organizationCode).append('\'');
        sb.append(", survey='").append(surveyType).append('\'');
        sb.append(", patient='").append(patientType).append('\'');
        sb.append(", from=").append(fromMonthSpec);
        sb.append(", to=").append(toMonthSpec);
        sb.append(", items=").append(items);
        sb.append(", environments=").append(environments);
        sb.append('}');
        return sb.toString();
    }

    public String toStringShort() {
        final StringBuilder sb = new StringBuilder("SearchSpec{");
        sb.append("title='").append(sectionTitle).append('\'');
        sb.append(", survey='").append(surveyType).append('\'');
        sb.append(", from=").append(fromMonthSpec);
        sb.append(", to=").append(toMonthSpec);
        sb.append(", environments=").append(environments);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public ReportValuesSearchSpec clone() {
        ReportValuesSearchSpec clone = null;
        List<String> clonedItems = new ArrayList<>();
        List<Environment> clonedEnvironments = new ArrayList<>();

        try {
            //Deep copy of the items property
            for(String item : this.getItems()) {
                clonedItems.add(item);
            }

            //Deep copy of the environments property
            for(Environment item : this.getEnvironments()) {
                clonedEnvironments.add(item);
            }

            clone = new ReportValuesSearchSpec(
                new File(this.csvFile.getPath()),
                this.getRecordNumber(),
                this.getRowNumberInCSV(),
                this.getSheetNumber(),
                this.getSectionTitle(),
                this.getSystemCode(),
                this.getOrganizationCode(),
                this.getSurveyType(),
                this.getPatientType(),
                this.getFromMonthSpec(),
                this.getToMonthSpec(),
                clonedItems,
                clonedEnvironments
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return clone;
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
