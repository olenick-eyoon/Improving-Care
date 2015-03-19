package com.olenick.avatar.main.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.validation.constraints.NotNull;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.model.DataSet;
import com.olenick.avatar.model.Environment;
import com.olenick.avatar.model.ReportFilter;
import com.olenick.avatar.model.totals.Totals;
import com.olenick.avatar.model.totals.TotalsSearchSpec;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;
import com.olenick.avatar.web.containers.LoginPage;
import com.olenick.avatar.web.containers.PatientExperienceIFrame;

/**
 * Get Systems totals (from the "Overview" tab) command.
 */
public class GetSystemsTotalsCommand implements Command {
    private static final Logger log = LoggerFactory
            .getLogger(GetSystemsTotalsCommand.class);

    private static final int SYSTEM_CODE_POSITION = 0;
    private static final int SYSTEM_NAME_POSITION = 1;
    private static final int SYSTEM_SEARCH_STRING_POSITION = 2;
    private static final int FROM_YEAR_POSITION = 3;
    private static final int FROM_MONTH_POSITION = 4;
    private static final int TO_YEAR_POSITION = 5;
    private static final int TO_MONTH_POSITION = 6;
    private static final int SURVEY_TYPE_POSITION = 7;
    private static final String MULTI_SELECT_ALL = "_FOC_NULL";
    private static final String NO_QUALIFIED_SURVEY_TYPE = "Avatar";
    private static final String PASSWORD = "password";
    private static final String SHEET_NAME = "ICEP Prod vs QA";
    private static final String USERNAME = "rferrari@avatarsolutions.com";

    private final String inputCSVFilename;
    private final String outputXLSXFilename;

    public GetSystemsTotalsCommand(@NotNull final String inputCSVFilename,
            @NotNull final String outputXLSXFilename) {
        this.inputCSVFilename = inputCSVFilename;
        this.outputXLSXFilename = outputXLSXFilename;
    }

    @Override
    public void execute() throws Exception {
        File inputCSVFile = new File(this.inputCSVFilename);
        CSVParser parser = CSVParser.parse(inputCSVFile,
                Charset.forName("UTF-8"), CSVFormat.EXCEL);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        int recordNumber = 0;
        for (CSVRecord record : parser.getRecords()) {
            TotalsSearchSpec searchSpec = this.getSearchSpec(record);
            Totals totals = this.fetchTotals(searchSpec);
            this.writeTotals(sheet, recordNumber++, searchSpec, totals);
            this.writeWorkbook(workbook, outputXLSXFilename);
        }
    }

    private ReportFilter buildReportFilter(TotalsSearchSpec searchSpec) {
        ReportFilter reportFilter = new ReportFilter();
        reportFilter.setSystem(searchSpec.getSystemCode());
        reportFilter.setOrganizations(MULTI_SELECT_ALL);
        reportFilter.setDepartments(MULTI_SELECT_ALL);
        reportFilter.setLocations(MULTI_SELECT_ALL);
        reportFilter.setSurveyType(searchSpec.getSurveyType());
        reportFilter.setPatientTypes(MULTI_SELECT_ALL);
        reportFilter.setFactorComposites(MULTI_SELECT_ALL);
        reportFilter.setItemQuestions(MULTI_SELECT_ALL);
        // reportFilter.setDateKey(DateKey.DISCHARGE_VISIT);
        reportFilter.setFrom(searchSpec.getFromMonthSpec());
        reportFilter.setTo(searchSpec.getToMonthSpec());
        // reportFilter.setGroupBy(DateRangeGroupBy.MONTHLY);
        // reportFilter.setCalculation(Calculation.TOP_BOX);
        // reportFilter.setDataSet(dateSetKey);
        return reportFilter;
    }

    private Totals fetchTotals(TotalsSearchSpec searchSpec) {
        Totals totals = new Totals();
        ReportFilter reportFilter = this.buildReportFilter(searchSpec);
        this.fetchTotals(totals, Environment.PRODUCTION, USERNAME, PASSWORD,
                searchSpec, reportFilter);
        this.fetchTotals(totals, Environment.QA, USERNAME, PASSWORD,
                searchSpec, reportFilter);
        return totals;
    }

    /**
     * TODO: Split this method.
     * 
     * @param totals Totals instance to be modified.
     * @param environment Environment spec.
     * @param username Username.
     * @param password Password.
     * @param searchSpec Search specification for totals.
     * @param reportFilter "Template" report filter.
     */
    private void fetchTotals(Totals totals, Environment environment,
            String username, String password, TotalsSearchSpec searchSpec,
            ReportFilter reportFilter) {
        log.info("Fetching totals for " + searchSpec);
        ExtendedRemoteWebDriver driver = null;
        try {
            driver = new ExtendedRemoteWebDriver(new FirefoxDriver());
            reportFilter.setDataSet(DataSet.ALL);
            PatientExperienceIFrame patientExperienceIFrame = this
                    .loadPatientExperience(driver, environment.getURLRoot(),
                            username, password);
            Long totalAll = patientExperienceIFrame.accessPanelFrame()
                    .changeSystem(reportFilter).configureFilters(reportFilter)
                    .applyConfiguredFilters().openOverviewTab()
                    .waitForElementsToLoad().getTotalCount();
            totals.set(environment, DataSet.ALL, totalAll);
            log.info("Total (" + environment + ", " + DataSet.ALL + ") = "
                    + totalAll);
            if (!searchSpec.getSurveyType().equals(NO_QUALIFIED_SURVEY_TYPE)) {
                reportFilter.setDataSet(DataSet.QUALIFIED);
                Long totalQualified = patientExperienceIFrame
                        .waitForElementsToLoad()
                        .configureCalculationFilter(reportFilter)
                        .applyConfiguredFilters().openOverviewTab()
                        .waitForElementsToLoad().getTotalCount();
                totals.set(environment, DataSet.QUALIFIED, totalQualified);
                log.info("Total (" + environment + ", " + DataSet.QUALIFIED
                        + ") = " + totalAll);
            }
        } catch (WebDriverException exception) {
            log.error("Failed fetching totals", exception);
        } finally {
            try {
                if (driver != null) {
                    driver.quit();
                }
            } catch (WebDriverException ignored) {
            }
        }
    }

    private PatientExperienceIFrame loadPatientExperience(
            final ExtendedRemoteWebDriver driver, final String urlRoot,
            final String username, final String password) {
        PatientExperienceIFrame patientExperienceIFrame = new LoginPage(driver,
                urlRoot).open().login(username, password)
                .navigateToPatientExperienceTab().waitForElementsToLoad();
        patientExperienceIFrame.openOverviewTab().waitForElementsToLoad();
        return patientExperienceIFrame;
    }

    private TotalsSearchSpec getSearchSpec(CSVRecord record) {
        TotalsSearchSpec searchSpec = new TotalsSearchSpec();
        searchSpec.setSystemCode(record.get(SYSTEM_CODE_POSITION));
        searchSpec.setSystemName(record.get(SYSTEM_NAME_POSITION));
        searchSpec.setSystemSearchString(record
                .get(SYSTEM_SEARCH_STRING_POSITION));
        searchSpec.setFromMonthSpec(record.get(FROM_YEAR_POSITION),
                record.get(FROM_MONTH_POSITION));
        searchSpec.setToMonthSpec(record.get(TO_YEAR_POSITION),
                record.get(TO_MONTH_POSITION));
        searchSpec.setSurveyType(record.get(SURVEY_TYPE_POSITION));
        return searchSpec;
    }

    private void writeTotals(Sheet sheet, int recordNumber,
            TotalsSearchSpec searchSpec, Totals totals) {
        Row row = sheet.createRow(recordNumber * 3);
        row.createCell(0).setCellValue(searchSpec.getHumanReadableMonthRange());

        this.writeDataSetTotals(sheet, recordNumber * 3 + 1, totals,
                DataSet.ALL);
        if (!searchSpec.getSurveyType().equals(NO_QUALIFIED_SURVEY_TYPE)) {
            this.writeDataSetTotals(sheet, recordNumber * 3 + 2, totals,
                    DataSet.QUALIFIED);
        }
    }

    private void writeDataSetTotals(Sheet sheet, int position, Totals totals,
            DataSet dataSet) {
        Row row = sheet.createRow(position);
        row.createCell(0).setCellValue(
                dataSet.name().substring(0, 1)
                        + dataSet.name().substring(1).toLowerCase());
        writeCellTotal(row.createCell(1),
                totals.get(Environment.PRODUCTION, dataSet));
        writeCellTotal(row.createCell(2), totals.get(Environment.QA, dataSet));
        int rowNumber = row.getRowNum() + 1;
        row.createCell(3).setCellFormula("C" + rowNumber + "-B" + rowNumber);
    }

    private void writeCellTotal(Cell cell, Long value) {
        if (value == null) {
            cell.setCellValue("ERROR");
        } else {
            cell.setCellValue(value);
        }
    }

    private void writeWorkbook(Workbook workbook, String outputFilename)
            throws IOException {
        FileOutputStream fileOut = new FileOutputStream(outputFilename);
        workbook.write(fileOut);
        fileOut.close();
    }
}
