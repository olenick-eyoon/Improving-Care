package com.olenick.avatar.main.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.validation.constraints.NotNull;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.exceptions.FetchSystemTotalsException;
import com.olenick.avatar.model.DataSet;
import com.olenick.avatar.model.Environment;
import com.olenick.avatar.model.ReportFilter;
import com.olenick.avatar.model.totals.DataSetTotals;
import com.olenick.avatar.model.totals.Totals;
import com.olenick.avatar.model.totals.TotalsSearchSpec;
import com.olenick.avatar.poi.excel.RegionFormatter;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;
import com.olenick.avatar.web.containers.LoginPage;
import com.olenick.avatar.web.containers.PatientExperienceIFrame;

/**
 * Get Systems totals (from the "Overview" tab) command.
 */
public class GetSystemsTotalsCommand implements Command {
    private static final Logger log = LoggerFactory
            .getLogger(GetSystemsTotalsCommand.class);

    private static final int NUMBER_OF_RECORD_THREADS = 3;
    private static final int SYSTEM_CODE_POSITION = 0;
    private static final int SYSTEM_NAME_POSITION = 1;
    private static final int SYSTEM_SEARCH_STRING_POSITION = 2;
    private static final int FROM_YEAR_POSITION = 3;
    private static final int FROM_MONTH_POSITION = 4;
    private static final int TO_YEAR_POSITION = 5;
    private static final int TO_MONTH_POSITION = 6;
    private static final int SURVEY_TYPE_POSITION = 7;

    private static final int RECORD_RETRIES = 3;
    private static final String MULTI_SELECT_ALL = "_FOC_NULL";
    private static final String NO_QUALIFIED_SURVEY_TYPE = "Avatar";
    private static final String PASSWORD = "password";
    private static final String SHEET_NAME = "ICEP Prod vs QA";
    private static final String USERNAME = "rferrari@avatarsolutions.com";

    private final DateFormat currentDateFormat = new SimpleDateFormat(
            "dd-MMM-yyyy");

    private final String inputCSVFilename;
    private final String outputXLSXFilename;

    private Font boldRedFont;
    private Font redFont;

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
        Sheet sheet = this.createSheet(workbook);
        this.createFonts(workbook);

        final ExecutorService recordThreadPool = Executors
                .newFixedThreadPool(NUMBER_OF_RECORD_THREADS);
        final ExecutorCompletionService<DecoratedTotals> recordCompletionService = new ExecutorCompletionService<>(
                recordThreadPool);

        int recordNumber = 0;
        List<CSVRecord> records = parser.getRecords();
        for (CSVRecord record : records) {
            final TotalsSearchSpec searchSpec = this.getSearchSpec(
                    inputCSVFile, record);
            final int rNumber = recordNumber++;
            recordCompletionService.submit(new Callable<DecoratedTotals>() {
                @Override
                public DecoratedTotals call() throws Exception {
                    DecoratedTotals totals = null;
                    boolean done = false;
                    int trial = 0;
                    while (!done && trial++ < RECORD_RETRIES) {
                        try {
                            // return new DecoratedTotals(rNumber, searchSpec,
                            // new Totals());
                            totals = new DecoratedTotals(rNumber, searchSpec,
                                    fetchTotals(searchSpec));
                            done = true;
                        } catch (WebDriverException exception) {
                            log.error("Error fetching totals for (record: "
                                    + rNumber + ") " + searchSpec
                                    + ", trial number: " + trial, exception);
                        }
                    }
                    if (totals == null) {
                        throw new FetchSystemTotalsException(
                                "Error fetching totals for (record: " + rNumber
                                        + ") " + searchSpec);
                    }
                    return totals;
                }
            });
        }
        Date today = new Date();
        for (int i = 0; i < records.size(); ++i) {
            Future<DecoratedTotals> future = recordCompletionService.take();
            try {
                DecoratedTotals decoratedTotals = future.get();
                this.writeTotals(workbook, sheet, decoratedTotals.recordNumber,
                        decoratedTotals.searchSpec, decoratedTotals.totals,
                        today);
                this.writeWorkbook(workbook, outputXLSXFilename);
            } catch (ExecutionException exception) {
                log.warn("Error while fetching totals for a record");
            } catch (IOException | InterruptedException exception) {
                recordThreadPool.shutdown();
                throw new FetchSystemTotalsException(
                        "While fetching a record's totals", exception);
            }
        }
        recordThreadPool.shutdown();
    }

    /**
     * TODO: Generalise ExecutorService/ExecutorCompletionService usage.
     * 
     * @param searchSpec
     * @return
     */
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

    private void createFonts(Workbook workbook) {
        this.boldRedFont = workbook.createFont();
        this.boldRedFont.setColor(Font.COLOR_RED);
        this.boldRedFont.setBold(true);
        this.redFont = workbook.createFont();
        this.redFont.setColor(Font.COLOR_RED);
    }

    private Sheet createSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        sheet.setColumnWidth(0, 4057);
        sheet.setColumnWidth(1, 2323);
        sheet.setColumnWidth(2, 2323);
        sheet.setColumnWidth(3, 2323);
        sheet.setColumnWidth(4, 9617);
        sheet.setDefaultRowHeightInPoints(15.75f);
        return sheet;
    }

    private Totals fetchTotals(final TotalsSearchSpec searchSpec)
            throws FetchSystemTotalsException {
        Totals totals = new Totals();
        final ReportFilter reportFilter = this.buildReportFilter(searchSpec);
        Environment[] environments = Environment.values();
        final ExecutorService envThreadPool = Executors
                .newFixedThreadPool(environments.length);
        EnumMap<Environment, Future<DataSetTotals>> futures = new EnumMap<>(
                Environment.class);
        for (final Environment environment : environments) {
            futures.put(environment,
                    envThreadPool.submit(new Callable<DataSetTotals>() {
                        @Override
                        public DataSetTotals call() throws Exception {
                            return fetchTotals(environment, USERNAME, PASSWORD,
                                    searchSpec, reportFilter);
                        }
                    }));
        }
        for (Map.Entry<Environment, Future<DataSetTotals>> futureEntry : futures
                .entrySet()) {
            Environment environment = futureEntry.getKey();
            try {
                totals.set(futureEntry.getKey(), futureEntry.getValue().get());
            } catch (ExecutionException exception) {
                log.warn("Error while fetching a pair of totals for "
                        + environment);
            } catch (InterruptedException exception) {
                throw new FetchSystemTotalsException("While fetching "
                        + searchSpec + " on " + environment, exception);
            }
        }
        envThreadPool.shutdown();
        return totals;
    }

    /**
     * TODO: Split this method.
     * 
     * @param environment Environment spec.
     * @param username Username.
     * @param password Password.
     * @param searchSpec Search specification for totals.
     * @param reportFilter "Template" report filter.
     * @return Totals for all data-sets.
     */
    private DataSetTotals fetchTotals(Environment environment, String username,
            String password, TotalsSearchSpec searchSpec,
            ReportFilter reportFilter) {
        log.info("Fetching totals for " + searchSpec);
        DataSetTotals result = new DataSetTotals();
        ExtendedRemoteWebDriver driver = null;
        try {
            DataSet dataSet = DataSet.ALL;
            driver = new ExtendedRemoteWebDriver(new FirefoxDriver());
            ReportFilter myReportFilter = reportFilter.clone();
            myReportFilter.setDataSet(dataSet);
            PatientExperienceIFrame patientExperienceIFrame = this
                    .loadPatientExperience(driver, environment.getURLRoot(),
                            username, password);
            Long totalAll = patientExperienceIFrame.accessPanelFrame()
                    .changeSystem(reportFilter).configureFilters(reportFilter)
                    .applyConfiguredFilters().openOverviewTab()
                    .waitForElementsToLoad().getTotalCount();
            result.set(dataSet, totalAll);
            log.info("Total (" + environment + ", " + dataSet + ") = "
                    + totalAll);
            this.takeScreenshot(driver, searchSpec, environment, dataSet);
            if (!searchSpec.getSurveyType().equals(NO_QUALIFIED_SURVEY_TYPE)) {
                dataSet = DataSet.QUALIFIED;
                myReportFilter.setDataSet(dataSet);
                Long totalQualified = patientExperienceIFrame
                        .waitForElementsToLoad()
                        .configureCalculationFilter(myReportFilter)
                        .applyConfiguredFilters().openOverviewTab()
                        .waitForElementsToLoad().getTotalCount();
                result.set(dataSet, totalQualified);
                log.info("Total (" + environment + ", " + dataSet + ") = "
                        + totalAll);
                this.takeScreenshot(driver, searchSpec, environment, dataSet);
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
        return result;
    }

    private void takeScreenshot(ExtendedRemoteWebDriver driver,
            TotalsSearchSpec searchSpec, Environment environment,
            DataSet dataSet) {
        File csvFile = searchSpec.getCsvFile();
        File dir = csvFile.getParentFile();
        String csvFilename = csvFile.getName();
        StringBuilder screenshotFilename = new StringBuilder(
                csvFilename.replace(".csv", ""));
        screenshotFilename.append('.').append(searchSpec.getRecordNumber())
                .append('-').append(environment).append('-').append(dataSet)
                .append(".png");
        try {
            driver.takeScreenshot(new File(dir, screenshotFilename.toString()));
        } catch (IOException exception) {
            log.error("Failed taking screenshot for " + searchSpec, exception);
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

    private TotalsSearchSpec getSearchSpec(File csvFile, CSVRecord record) {
        TotalsSearchSpec searchSpec = new TotalsSearchSpec();
        searchSpec.setCsvFile(csvFile);
        searchSpec.setRecordNumber(record.getRecordNumber());
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

    private void writeDataSetTotals(boolean fetch, Sheet sheet, int rowNumber,
            Totals totals, DataSet dataSet) {
        Row row = sheet.createRow(rowNumber);

        row.createCell(0).setCellValue(
                dataSet.name().substring(0, 1)
                        + dataSet.name().substring(1).toLowerCase());

        int formulaRow = rowNumber + 1;

        if (fetch) {
            writeCellTotal(row.createCell(1),
                    totals.get(Environment.PRODUCTION, dataSet));
            writeCellTotal(row.createCell(2),
                    totals.get(Environment.QA, dataSet));
            row.createCell(3).setCellFormula(
                    "C" + formulaRow + "-B" + formulaRow);
        }

        SheetConditionalFormatting sheetCF = sheet
                .getSheetConditionalFormatting();

        ConditionalFormattingRule cfRuleError = sheetCF
                .createConditionalFormattingRule("ISERROR($D$" + formulaRow
                        + ")");
        PatternFormatting cfErrorPF = cfRuleError.createPatternFormatting();
        cfErrorPF.setFillBackgroundColor(IndexedColors.RED.getIndex());
        cfErrorPF.setFillPattern(CellStyle.SOLID_FOREGROUND);

        ConditionalFormattingRule cfRuleNoData = sheetCF
                .createConditionalFormattingRule("OR(ISBLANK($B$" + formulaRow
                        + "), ISBLANK($C$" + formulaRow + "))");
        PatternFormatting cfNoDataPF = cfRuleNoData.createPatternFormatting();
        cfNoDataPF.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT
                .getIndex());
        cfNoDataPF.setFillPattern(CellStyle.SOLID_FOREGROUND);

        ConditionalFormattingRule cfRuleNonZero = sheetCF
                .createConditionalFormattingRule("$D$" + formulaRow + "<>0");
        PatternFormatting cfNonZeroPF = cfRuleNonZero.createPatternFormatting();
        cfNonZeroPF.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        cfNonZeroPF.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cfRuleNonZero.createFontFormatting().setFontStyle(false, true);

        CellRangeAddress[] cfRangeNoData = new CellRangeAddress[] { new CellRangeAddress(
                rowNumber, rowNumber, 1, 4) };
        sheetCF.addConditionalFormatting(cfRangeNoData, cfRuleNoData);
        CellRangeAddress[] cfRangeOthers = new CellRangeAddress[] { new CellRangeAddress(
                rowNumber, rowNumber, 0, 3) };
        sheetCF.addConditionalFormatting(cfRangeOthers, cfRuleError);
        sheetCF.addConditionalFormatting(cfRangeOthers, cfRuleNonZero);
    }

    private void writeTotals(Workbook workbook, Sheet sheet, int recordNumber,
            TotalsSearchSpec searchSpec, Totals totals, Date today) {
        final int ROWS_PER_RECORD = 7;
        int rowNumber = recordNumber * ROWS_PER_RECORD;

        // 1 System name
        Row systemNameRow = sheet.createRow(recordNumber * ROWS_PER_RECORD);
        CellRangeAddress systemNameRange = new CellRangeAddress(rowNumber,
                rowNumber, 0, 3);
        systemNameRow.createCell(0).setCellValue(searchSpec.getSystemName());
        new RegionFormatter(systemNameRange, sheet, workbook)
                .setAlignment(CellStyle.ALIGN_CENTER)
                .setBorder(CellStyle.BORDER_MEDIUM)
                .setFillPattern(CellStyle.SOLID_FOREGROUND).mergeRegion();
        // TODO: Find the way of doing this below properly
        ((XSSFCellStyle) systemNameRow.getCell(0).getCellStyle())
                .setFillForegroundColor(new XSSFColor(new java.awt.Color(169,
                        208, 142)));
        int row1 = rowNumber;
        ++rowNumber;

        // 2 Current date
        Row currentDateRow = sheet.createRow(rowNumber);
        currentDateRow.createCell(1).setCellValue(
                this.currentDateFormat.format(today));
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 1, 3),
                sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER)
                .setBorder(CellStyle.BORDER_MEDIUM).mergeRegion();
        ++rowNumber;

        // 3 Labels
        Row labelsRow = sheet.createRow(rowNumber);
        int col = 1;
        for (String label : new String[] { "Prod", "QA", "Diff", "Notes" }) {
            labelsRow.createCell(col++).setCellValue(label);
        }
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 1, 4),
                sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER)
                .setBorder(CellStyle.BORDER_MEDIUM)
                .setInternalBorders(CellStyle.BORDER_MEDIUM);
        ++rowNumber;

        // 4 Survey type
        Row surveyTypeRow = sheet.createRow(rowNumber);
        surveyTypeRow.setHeightInPoints(30f);
        surveyTypeRow.createCell(0).setCellValue(searchSpec.getSurveyType());
        surveyTypeRow.createCell(1).setCellValue(
                "All counts are from the Total line of the Overview Tab");
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 0, 0),
                sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER)
                .setBorder(CellStyle.BORDER_MEDIUM).setFont(this.boldRedFont);
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 1, 3),
                sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER)
                .setBorder(CellStyle.BORDER_MEDIUM)
                .setFillForegroundColor(IndexedColors.YELLOW.getIndex())
                .setFillPattern(CellStyle.SOLID_FOREGROUND).setWrapText(true)
                .mergeRegion();
        // TODO: Find the way of doing this below properly
        surveyTypeRow.getCell(0).getCellStyle().setFont(this.boldRedFont);
        ++rowNumber;

        // 5 Date range
        Row dateRangeRow = sheet.createRow(rowNumber);
        dateRangeRow.createCell(0).setCellValue(
                searchSpec.getHumanReadableMonthRange());
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 0, 0),
                sheet, workbook).setFont(redFont);
        // TODO: Find the way of doing this below properly
        dateRangeRow.getCell(0).getCellStyle().setFont(this.redFont);
        int row5 = rowNumber;
        ++rowNumber;

        // 6 Totals: Qualified
        this.writeDataSetTotals(
                !searchSpec.getSurveyType().equals(NO_QUALIFIED_SURVEY_TYPE),
                sheet, rowNumber, totals, DataSet.QUALIFIED);
        ++rowNumber;
        // 7 Totals: All
        this.writeDataSetTotals(true, sheet, rowNumber, totals, DataSet.ALL);
        int row7 = rowNumber;

        // 5-7: borders
        new RegionFormatter(new CellRangeAddress(row5, row7, 0, 4), sheet,
                workbook).setBorder(CellStyle.BORDER_MEDIUM)
                .setInternalBorders(CellStyle.BORDER_THIN);

        // 1-7: Outer border
        new RegionFormatter(new CellRangeAddress(row1, row7, 0, 4), sheet,
                workbook).setBorder(CellStyle.BORDER_MEDIUM);
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

    private static class DecoratedTotals {
        public final int recordNumber;
        public final TotalsSearchSpec searchSpec;
        public final Totals totals;

        public DecoratedTotals(final int recordNumber,
                final TotalsSearchSpec searchSpec, final Totals totals) {
            this.recordNumber = recordNumber;
            this.searchSpec = searchSpec;
            this.totals = totals;
        }
    }
}
