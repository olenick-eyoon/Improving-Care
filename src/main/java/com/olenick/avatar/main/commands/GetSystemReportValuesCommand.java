package com.olenick.avatar.main.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.exceptions.ParseException;
import com.olenick.avatar.model.CSVParameters;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.exceptions.FetchSystemTotalsException;
import com.olenick.avatar.main.commands.system_report_values.ReportValueAdapter;
import com.olenick.avatar.model.DataSet;
import com.olenick.avatar.model.Environment;
import com.olenick.avatar.model.ReportFilter;
import com.olenick.avatar.model.ReportTab;
import com.olenick.avatar.model.User;
import com.olenick.avatar.model.report_values.CrossEnvironmentReportValues;
import com.olenick.avatar.model.report_values.DataSetReportValues;
import com.olenick.avatar.model.report_values.ReportValues;
import com.olenick.avatar.model.report_values.ReportValuesSearchSpec;
import com.olenick.avatar.web.containers.LoginPage;
import com.olenick.avatar.web.containers.PatientExperienceIFrame;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;
import com.olenick.selenium.exceptions.ElementNotLoadedException;
import com.olenick.util.poi.excel.RegionFormatter;

/**
 * Get Systems "Overview" (tab) values command.
 * <p>
 * TODO: Split this class into several builders.
 * </p>
 */
public class GetSystemReportValuesCommand implements Command {
    public static final int SHEET_INDEX_ALL_VALUES = 1;
    public static final String SURVEY_TYPE_HCAHPS = "HCAHPS";
    private static final Logger log = LoggerFactory.getLogger(GetSystemReportValuesCommand.class);
    private static final String USERNAME_TEMPLATE = "ielia-test-%03d@olenick.com";
    private static final String PASSWORD = "Password1";
    private static final long TIMEOUT_SECS_FETCH_VALUES = 900L;
    private static final int NUMBER_OF_RECORD_THREADS = 3;
    private static final int RETRIES = 3;
    private static final int COLUMN_WIDTH_1_ALL_VALUES = 7235;
    private static final int COLUMN_WIDTH_1_TOTALS = 4057;
    private static final int COLUMN_WIDTH_2 = 2323;
    private static final int COLUMN_WIDTH_3 = 2323;
    private static final int COLUMN_WIDTH_4 = 2323;
    private static final int COLUMN_WIDTH_5 = 9617;
    private static final float DEFAULT_ROW_HEIGHT = 15.75f;
    private static final float ROW_HEIGHT_SURVEY_TYPE_TOTALS = 30f;
    private static final float ROW_HEIGHT_SURVEY_TYPE_ALL_VALUES = 60f;
    private static final String ITEM_NAME_TOTAL = "Total";
    private static final String MULTI_SELECT_ALL = "_FOC_NULL";
    private static final String SHEET_NAME_ALL_VALUES = "ICEP QA vs iCare1";
    private static final String SHEET_NAME_TOTALS = "ICEP Prod vs QA";
    private static final String STRING_VALUE_ERROR = "ERROR";
    private static final String SURVEY_TYPE_LABEL_ALL_VALUES = "ICEP QA values are from the Overview tab, Qualified data set; iCare1 values are from the {surveyType} report";
    private static final String SURVEY_TYPE_LABEL_TOTALS = "All counts are from the Total line of the Overview Tab";
    private static final String SURVEY_TYPE_NOTES_ALL_VALUES_NO_QUALIFIED = "No data available for QUALIFIED. Data from ICEP QA is from ALL";
    private static final String LABEL_ALL_VALUES_A = "ICEP QA";
    private static final String LABEL_ALL_VALUES_B = "iCare1";
    private static final String LABEL_TOTALS_A = "Prod";
    private static final String LABEL_TOTALS_B = "QA";
    private static final String NO_DATA_CELL_VALUE = "N/D";
    private static final ReportValueAdapter REPORT_VALUE_ADAPTER = new ReportValueAdapter();

    private final String inputCSVFilename;
    private final String outputXLSXFilename;

    private final BlockingQueue<User> users;

    private Font boldRedFont;
    private Font redFont;

    public GetSystemReportValuesCommand(@NotNull final String inputCSVFilename, @NotNull final String outputXLSXFilename) {
        this.inputCSVFilename = inputCSVFilename;
        this.outputXLSXFilename = outputXLSXFilename;
        this.users = new ArrayBlockingQueue<>(NUMBER_OF_RECORD_THREADS);
        for (int i = 1; i <= NUMBER_OF_RECORD_THREADS; ++i) {
            if (i == 2) continue;
            this.users.add(new User(String.format(USERNAME_TEMPLATE, i), PASSWORD));
        }
    }

    /**
     * Get a map of SearchSpecs records with their line numbers in CSV
     * @return HashMap<Integer, ReportValuesSearchSpec>
     * @throws Exception
     */
    private HashMap<Integer, ReportValuesSearchSpec> loadAndValidateInputCSV() throws ParseException, IOException {
        boolean parsingErrorFound = false;
        HashMap<Integer, ReportValuesSearchSpec> recordsMap = new HashMap<>();
        File inputCSVFile = new File(this.inputCSVFilename);
        CSVParser parser = CSVParser.parse(inputCSVFile, Charset.forName("UTF-8"), CSVFormat.EXCEL);
        List<CSVRecord> records = parser.getRecords();

        int recordNumber = 0;

        for (CSVRecord record : records) {
            try {
                for (ReportValuesSearchSpec searchSpec : this.getSearchSpec(inputCSVFile, record)) {
                    recordsMap.put(++recordNumber, searchSpec);
                }
            }
            catch (ParseException e) {
                parsingErrorFound = true;
                log.error("CSV File: " + inputCSVFile + ", record #" + recordNumber + " - " + e.getMessage());
            }
        }

        if (parsingErrorFound) throw new ParseException("Invalid input CSV.");

        return recordsMap;
    }

    /**
     * TODO: Split this method.
     *
     * @throws Exception
     */
    @Override
    public void execute() throws Exception {
        HashMap<Integer, ReportValuesSearchSpec> records = loadAndValidateInputCSV();

        Workbook workbook = new XSSFWorkbook();
        Sheet totalsSheet = this.createSheet(workbook, SHEET_NAME_TOTALS, COLUMN_WIDTH_1_TOTALS);
        Sheet allValuesSheet = this.createSheet(workbook, SHEET_NAME_ALL_VALUES, COLUMN_WIDTH_1_ALL_VALUES);
        this.createFonts(workbook);

        final ExecutorService recordThreadPool = Executors.newFixedThreadPool(NUMBER_OF_RECORD_THREADS);

        List<Future<DecoratedOverviewValues>> futures = new ArrayList<>(records.size());
        for (Map.Entry<Integer, ReportValuesSearchSpec> record : records.entrySet()) {
            final ReportValuesSearchSpec searchSpec = record.getValue();
            final int rNumber = record.getKey();
            futures.add(recordThreadPool.submit(new Callable<DecoratedOverviewValues>() {
                @Override
                public DecoratedOverviewValues call() throws Exception {
                    DecoratedOverviewValues values = null;
                    boolean done = false;
                    int trial = 0;
                    while (!done && trial++ < RETRIES) {
                        User user = users.poll(TIMEOUT_SECS_FETCH_VALUES, TimeUnit.SECONDS);
                        try {
                            // XLSX Format testing:
                            // return new DecoratedOverviewValues(
                            // rNumber, searchSpec,
                            // new CrossEnvironmentReportValues());
                            values = new DecoratedOverviewValues(rNumber, searchSpec, fetchValues(user, searchSpec));
                            done = true;
                        } catch (ElementNotLoadedException
                                | WebDriverException exception) {
                            log.warn("Error fetching values for (record: " + rNumber + ") " + searchSpec + ", trial number: " + trial, exception);
                        } finally {
                            users.offer(user);
                        }
                    }
                    if (values == null) {
                        recordThreadPool.shutdown();
                        log.error("Failed fetching values for (record: " + rNumber + ") " + searchSpec);
                        throw new FetchSystemTotalsException("Error fetching values for (record: " + rNumber + ") " + searchSpec);
                    }
                    return values;
                }
            }));
        }
        Date today = new Date();
        int totalsTabRowNumber = 0;
        int specificOrgsTabRowNumber = 0;
        for (Future<DecoratedOverviewValues> future : futures) {
            try {
                DecoratedOverviewValues decoratedOverviewValues = future.get();
                if (decoratedOverviewValues.searchSpec.getSheetNumber() == 0) {
                    totalsTabRowNumber = this.writeTotals(workbook, totalsSheet, totalsTabRowNumber, decoratedOverviewValues.searchSpec, decoratedOverviewValues.crossEnvironmentReportValues, today);
                } else {
                    specificOrgsTabRowNumber = this.writeAllOverviewValues(workbook, allValuesSheet, specificOrgsTabRowNumber, decoratedOverviewValues.searchSpec, decoratedOverviewValues.crossEnvironmentReportValues, today);
                }
                this.writeWorkbook(workbook, outputXLSXFilename);
            } catch (ExecutionException exception) {
                log.warn("Error while fetching values for a record");
            } catch (IOException | InterruptedException exception) {
                recordThreadPool.shutdown();
                throw new FetchSystemTotalsException("While fetching a record's values", exception);
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
    private ReportFilter buildReportFilter(ReportValuesSearchSpec searchSpec) {
        ReportFilter reportFilter = new ReportFilter();
        reportFilter.setSystem(searchSpec.getSystemCode());
        reportFilter.setOrganizations(searchSpec.getOrganizationCode());
        reportFilter.setDepartments(MULTI_SELECT_ALL);
        reportFilter.setLocations(MULTI_SELECT_ALL);
        reportFilter.setSurveyType(searchSpec.getSurveyType());
        reportFilter.setPatientTypes(searchSpec.getPatientType());
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

    private Sheet createSheet(Workbook workbook, String sheetName, int firstColumnWidth) {
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setColumnWidth(0, firstColumnWidth);
        sheet.setColumnWidth(1, COLUMN_WIDTH_2);
        sheet.setColumnWidth(2, COLUMN_WIDTH_3);
        sheet.setColumnWidth(3, COLUMN_WIDTH_4);
        sheet.setColumnWidth(4, COLUMN_WIDTH_5);
        sheet.setDefaultRowHeightInPoints(DEFAULT_ROW_HEIGHT);
        return sheet;
    }

    private CrossEnvironmentReportValues fetchValues(final User user, final ReportValuesSearchSpec searchSpec) throws FetchSystemTotalsException {
        CrossEnvironmentReportValues crossEnvironmentReportValues = new CrossEnvironmentReportValues();
        final ReportFilter reportFilter = this.buildReportFilter(searchSpec);
        final ExecutorService envThreadPool = Executors.newFixedThreadPool(searchSpec.getEnvironments().size());
        EnumMap<Environment, Future<DataSetReportValues>> futures = new EnumMap<>(Environment.class);
        for (final Environment environment : searchSpec.getEnvironments()) {
            futures.put(environment, envThreadPool.submit(new Callable<DataSetReportValues>() {
                @Override
                public DataSetReportValues call() throws Exception {
                    DataSetReportValues result = null;
                    boolean done = false;
                    int trial = 0;
                    while (!done && trial++ < RETRIES) {
                        try {
                            result = fetchValues(environment, user.getUsername(), user.getPassword(), searchSpec, reportFilter);
                            done = true;
                        } catch (ElementNotLoadedException
                                | WebDriverException exception) {
                            log.warn("Error fetching values for " + searchSpec + ", " + environment + ", trial number: " + trial, exception);
                        } catch (Exception exception) {
                            log.error("Failed fetching values for " + searchSpec + ", " + environment, exception);
                            throw exception;
                        }
                    }
                    if (result == null) {
                        log.error("Failed fetching values for " + searchSpec + ", " + environment);
                        throw new FetchSystemTotalsException("Error fetching values for " + searchSpec + ", " + environment);
                    }
                    return result;
                }
            }));
        }
        for (Map.Entry<Environment, Future<DataSetReportValues>> futureEntry : futures.entrySet()) {
            Environment environment = futureEntry.getKey();
            try {
                crossEnvironmentReportValues.set(futureEntry.getKey(), futureEntry.getValue().get());
            } catch (ExecutionException exception) {
                log.error("Error while fetching a pair of value maps for " + searchSpec + " on " + environment);
            } catch (InterruptedException exception) {
                throw new FetchSystemTotalsException("While fetching " + searchSpec + " on " + environment, exception);
            }
        }
        envThreadPool.shutdown();
        return crossEnvironmentReportValues;
    }

    /**
     * TODO: Split this method.
     *
     * @param environment  Environment spec.
     * @param username     Username.
     * @param password     Password.
     * @param searchSpec   Search specification for overview values.
     * @param reportFilter "Template" report filter.
     * @return Overview values for all data-sets.
     */
    private DataSetReportValues fetchValues(Environment environment, String username, String password, ReportValuesSearchSpec searchSpec, ReportFilter reportFilter) {
        log.info("Fetching values for " + searchSpec + ", " + environment);
        DataSetReportValues result = new DataSetReportValues();
        ExtendedRemoteWebDriver driver = null;
        try {
            ReportTab reportTab = this.getReportTab(searchSpec);
            DataSet dataSet = DataSet.ALL;
            //driver = new ExtendedRemoteWebDriver(new FirefoxDriver());
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\eyoon\\Downloads\\Selenium\\Drivers\\Chrome\\chromedriver.exe");
            driver = new ExtendedRemoteWebDriver(new ChromeDriver());
            ReportFilter myReportFilter = reportFilter.clone();
            myReportFilter.setDataSet(dataSet);
            PatientExperienceIFrame patientExperienceIFrame = this.loadPatientExperience(driver, environment.getURLRoot(), username, password);
            ReportValues valuesAll = patientExperienceIFrame.accessPanelFrame().changeSystem(myReportFilter).configureFilters(myReportFilter).applyConfiguredFilters().waitForElementsToLoad().openReportTab(reportTab).waitForElementsToLoad().getValues();
            result.set(dataSet, valuesAll);
            log.info("ReportValues (" + environment + ", " + dataSet + ") = " + valuesAll);
            this.takeScreenshot(driver, searchSpec, environment, dataSet);
            if (searchSpec.isQualifiedEnabled()) {
                dataSet = DataSet.QUALIFIED;
                myReportFilter.setDataSet(dataSet);
                ReportValues valuesQualified = patientExperienceIFrame.configureCalculationFilter(myReportFilter).applyConfiguredFilters().waitForElementsToLoad().openReportTab(reportTab).waitForElementsToLoad().getValues();
                result.set(dataSet, valuesQualified);
                log.info("ReportValues (" + environment + ", " + dataSet + ") = " + valuesQualified);
                this.takeScreenshot(driver, searchSpec, environment, dataSet);
            }
        } finally {
            try {
                if (driver != null) {
                    driver.quit();
                }
            } catch (WebDriverException ignored) {
                log.error("Unable to make driver quit.");
            }
        }
        return result;
    }

    private ReportTab getReportTab(ReportValuesSearchSpec searchSpec) {
        ReportTab result;
        if (searchSpec.getSheetNumber() == SHEET_INDEX_ALL_VALUES && SURVEY_TYPE_HCAHPS.equals(searchSpec.getSurveyType())) {
            result = ReportTab.HCAHPS_NATIONAL;
        } else {
            result = ReportTab.OVERVIEW;
        }
        return result;
    }

    private Object getValue(CrossEnvironmentReportValues crossEnvironmentReportValues, Environment environment, DataSet dataSet, String itemName) {
        Object value;
        try {
            ReportValues values = crossEnvironmentReportValues.get(environment).get(dataSet);
            if (values.isDataAvailable()) {
                value = REPORT_VALUE_ADAPTER.getValue(itemName, values.get(itemName));
            } else {
                value = NO_DATA_CELL_VALUE;
            }
        } catch (NullPointerException exception) {
            value = null;
        }
        return value;
    }

    private void takeScreenshot(ExtendedRemoteWebDriver driver, ReportValuesSearchSpec searchSpec, Environment environment, DataSet dataSet) {
        File csvFile = searchSpec.getCsvFile();
        File dir = csvFile.getParentFile();
        String csvFilename = csvFile.getName();
        StringBuilder screenshotFilename = new StringBuilder(csvFilename.replace(".csv", ""));
        screenshotFilename.append('.').append(searchSpec.getRecordNumber()).append('-').append(environment).append('-').append(dataSet).append(".png");
        try {
            driver.takeScreenshot(new File(dir, screenshotFilename.toString()));
        } catch (IOException exception) {
            log.error("Failed taking screenshot for " + searchSpec, exception);
        }
    }

    private PatientExperienceIFrame loadPatientExperience(final ExtendedRemoteWebDriver driver, final String urlRoot, final String username, final String password) {
        PatientExperienceIFrame patientExperienceIFrame = new LoginPage(driver, urlRoot).open().login(username, password).navigateToPatientExperienceTab().waitForElementsToLoad();
        patientExperienceIFrame.openOverviewTab().waitForElementsToLoad();
        return patientExperienceIFrame;
    }

    /**
     * TODO: Move this.
     *
     * @param csvFile
     * @param record
     * @return
     */
    private ArrayList<ReportValuesSearchSpec> getSearchSpec(File csvFile, CSVRecord record) throws ParseException {
        //We check against the number of needed parameters
        if (record.size() != CSVParameters.values().length)
            throw new ParseException("Insufficient CSV values provided.");

        ArrayList<ReportValuesSearchSpec> searchSpecList = new ArrayList<>();
        ReportValuesSearchSpec searchSpec = new ReportValuesSearchSpec();
        String datesSpecs, fromYear, fromMonth, toYear, toMonth;
        String[] datesRange, datesSet;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        GregorianCalendar gCal = new GregorianCalendar();
        Date start, end;

        //Setting all the common aspects
        searchSpec.setCsvFile(csvFile);
        searchSpec.setRecordNumber(record.getRecordNumber());
        searchSpec.setSheetNumber(record.get(CSVParameters.SHEET_NUMBER_POSITION.GetValue()));
        searchSpec.setSectionTitle(record.get(CSVParameters.SECTION_TITLE_POSITION.GetValue()));
        searchSpec.setSystemCode(record.get(CSVParameters.SYSTEM_CODE_POSITION.GetValue()));
        searchSpec.setOrganizationCode(record.get(CSVParameters.ORGANIZATION_CODE_POSITION.GetValue()));
        searchSpec.setSurveyType(record.get(CSVParameters.SURVEY_TYPE_POSITION.GetValue()));
        searchSpec.setPatientType(record.get(CSVParameters.PATIENT_TYPE_POSITION.GetValue()));
        searchSpec.setItems(record.get(CSVParameters.ITEMS_POSITION.GetValue()));
        searchSpec.setEnvironments(record.get(CSVParameters.ENVIRONMENTS_POSITION.GetValue()));

        //Dates search specs
        try {
            datesSpecs = record.get(CSVParameters.DATES_POSITION.GetValue());

            //Format validation for date 'yyyy/MM' - set 'yyyy/MM-yyyy/MM' - range 'yyyy/MM:yyyy/MM'
            Pattern datesPattern = Pattern.compile("^\\d{4}/\\d{1,2}((-|:)\\d{4}/\\d{1,2})?$");
            Matcher datesMatcher = datesPattern.matcher(datesSpecs);

            if (!datesMatcher.matches()) {
                // throw exception.
                throw new ParseException();
            } else if (datesMatcher.group(1) == null) {
                //A specific date
                try {
                    start = dateFormat.parse(datesSpecs);
                    gCal.setTime(start);

                    fromYear = toYear = getYearAsStringFromCalendar(gCal);
                    fromMonth = toMonth = getMonthAsStringFromCalendar(gCal);

                    searchSpec.setFromMonthSpec(fromYear, fromMonth);
                    searchSpec.setToMonthSpec(toYear, toMonth);

                    searchSpecList.add(searchSpec);
                } catch (java.text.ParseException e) {
                    throw new ParseException("Date error.");
                }
            } else if ("-".equals(datesMatcher.group(2))) {
                //Each date
                try {
                    datesSet = datesSpecs.split("-");

                    start = dateFormat.parse(datesSet[0]);
                    end = dateFormat.parse(datesSet[1]);
                    gCal.setTime(start);

                    if (start.after(end)) {
                        while (!gCal.getTime().before(end)) {
                            searchSpec.setFromMonthSpec(getYearAsStringFromCalendar(gCal), getMonthAsStringFromCalendar(gCal));
                            searchSpec.setToMonthSpec(searchSpec.getFromMonthSpec());

                            searchSpecList.add(searchSpec.clone());

                            gCal.add(Calendar.MONTH, -1);
                        }
                    } else {
                        while (!gCal.getTime().after(end)) {
                            searchSpec.setFromMonthSpec(getYearAsStringFromCalendar(gCal), getMonthAsStringFromCalendar(gCal));
                            searchSpec.setToMonthSpec(searchSpec.getFromMonthSpec());

                            searchSpecList.add(searchSpec.clone());

                            gCal.add(Calendar.MONTH, 1);
                        }
                    }
                } catch (java.text.ParseException e) {
                    throw new ParseException("Dates set error.");
                }
            } else {
                try {
                    datesRange = datesSpecs.split(":");

                    start = dateFormat.parse(datesRange[0]);
                    end = dateFormat.parse(datesRange[1]);

                    if (start.after(end)) {
                        throw new ParseException("Invalid input order for dates range. Dates must be written as FROM:TO order.");
                    }

                    //From date to date
                    //From
                    gCal.setTime(start);

                    fromYear = getYearAsStringFromCalendar(gCal);
                    fromMonth = getMonthAsStringFromCalendar(gCal);

                    //To
                    gCal.setTime(end);

                    toYear = getYearAsStringFromCalendar(gCal);
                    toMonth = getMonthAsStringFromCalendar(gCal);

                    searchSpec.setFromMonthSpec(fromYear, fromMonth);
                    searchSpec.setToMonthSpec(toYear, toMonth);

                    searchSpecList.add(searchSpec);
                } catch (java.text.ParseException te) {
                    throw new ParseException("Dates range error.");
                }
            }

            return searchSpecList;
        } catch (ParseException fe) {
            throw new ParseException(fe.getMessage() +
                    " Invalid date format. Expecting format: (Range) 'yyyy/MM:yyyy/MM' or (Set) 'yyyy/MM-yyyy/MM' " +
                    " or (a particular date) 'yyyy/MM'."
            );
        }
    }

    private String getMonthAsStringFromCalendar(GregorianCalendar gCal) {
        return String.valueOf(gCal.get(Calendar.MONTH) + 1);
    }

    private String getYearAsStringFromCalendar(GregorianCalendar gCal) {
        return String.valueOf(gCal.get(Calendar.YEAR));
    }

    private int writeAllOverviewValues(Workbook workbook, Sheet sheet, int startingRowNumber, ReportValuesSearchSpec searchSpec, CrossEnvironmentReportValues crossEnvironmentReportValues, Date today) {
        boolean qualified = searchSpec.isQualifiedEnabled();
        DataSet dataSet = qualified ? DataSet.QUALIFIED : DataSet.ALL;

        int rowNumber = this.writeSectionHeader(workbook, sheet, startingRowNumber, searchSpec, today, LABEL_ALL_VALUES_A, LABEL_ALL_VALUES_B, ROW_HEIGHT_SURVEY_TYPE_ALL_VALUES, SURVEY_TYPE_LABEL_ALL_VALUES.replace("{surveyType}", searchSpec.getSurveyType()), qualified ? "" : SURVEY_TYPE_NOTES_ALL_VALUES_NO_QUALIFIED);
        int dateRangeRowNumber = rowNumber;
        this.writeRowDateRange(workbook, sheet, rowNumber++, searchSpec);

        // Respondents
        if ((searchSpec.isAllItems() && !SURVEY_TYPE_HCAHPS.equals(searchSpec.getSurveyType())) || searchSpec.getItems().contains(ITEM_NAME_TOTAL)) {
            Object count = this.getValue(crossEnvironmentReportValues, Environment.QA, dataSet, ITEM_NAME_TOTAL);
            this.writeDataSetCount(true, sheet, rowNumber++, count, null, "Respondents");
        }

        // %TB
        List<String> itemNames;
        if (searchSpec.isAllItems()) {
            try {
                itemNames = crossEnvironmentReportValues.get(Environment.QA).get(dataSet).getItemNames();
                itemNames.remove(ITEM_NAME_TOTAL);
            } catch (NullPointerException exception) {
                itemNames = Collections.emptyList();
            }
        } else {
            itemNames = searchSpec.getItems();
        }
        for (String itemName : itemNames) {
            Object value = this.getValue(crossEnvironmentReportValues, Environment.QA, dataSet, itemName);
            this.writeDataSetCount(true, sheet, rowNumber++, value, null, itemName);
        }

        --rowNumber;
        this.writeFinalBorders(workbook, sheet, startingRowNumber, dateRangeRowNumber, rowNumber++);

        return rowNumber;
    }

    private void writeCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue(STRING_VALUE_ERROR);
        } else if (value instanceof Long) {
            cell.setCellValue((long) value);
        } else if (value instanceof Float) {
            cell.setCellValue(Double.parseDouble(value.toString()));
        } else {
            cell.setCellValue(value.toString());
        }
    }

    private void writeDataSetCount(boolean fetch, Sheet sheet, int rowNumber, Object valueA, Object valueB, String itemName) {
        Row row = sheet.createRow(rowNumber);

        row.createCell(0).setCellValue(itemName);

        int formulaRow = rowNumber + 1;

        if (fetch) {
            writeCellValue(row.createCell(1), valueA);
            writeCellValue(row.createCell(2), valueB);
            row.createCell(3).setCellFormula("C" + formulaRow + "-B" + formulaRow);
        }

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        ConditionalFormattingRule cfRuleError = sheetCF.createConditionalFormattingRule("ISERROR($D$" + formulaRow + ")");
        PatternFormatting cfErrorPF = cfRuleError.createPatternFormatting();
        cfErrorPF.setFillBackgroundColor(IndexedColors.RED.getIndex());
        cfErrorPF.setFillPattern(CellStyle.SOLID_FOREGROUND);

        ConditionalFormattingRule cfRuleNoData = sheetCF.createConditionalFormattingRule("OR(ISBLANK($B$" + formulaRow + "), ISBLANK($C$" + formulaRow + "))");
        PatternFormatting cfNoDataPF = cfRuleNoData.createPatternFormatting();
        cfNoDataPF.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cfNoDataPF.setFillPattern(CellStyle.SOLID_FOREGROUND);

        ConditionalFormattingRule cfRuleNonZero = sheetCF.createConditionalFormattingRule("$D$" + formulaRow + "<>0");
        PatternFormatting cfNonZeroPF = cfRuleNonZero.createPatternFormatting();
        cfNonZeroPF.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        cfNonZeroPF.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cfRuleNonZero.createFontFormatting().setFontStyle(false, true);

        CellRangeAddress[] cfRangeNoData = new CellRangeAddress[]{new CellRangeAddress(rowNumber, rowNumber, 1, 4)};
        sheetCF.addConditionalFormatting(cfRangeNoData, cfRuleNoData);
        CellRangeAddress[] cfRangeOthers = new CellRangeAddress[]{new CellRangeAddress(rowNumber, rowNumber, 0, 3)};
        sheetCF.addConditionalFormatting(cfRangeOthers, cfRuleError);
        sheetCF.addConditionalFormatting(cfRangeOthers, cfRuleNonZero);
    }

    private void writeFinalBorders(final Workbook workbook, final Sheet sheet, final int firstRowNumber, final int dateRangeRowNumber, final int lastRowNumber) {
        new RegionFormatter(new CellRangeAddress(dateRangeRowNumber, lastRowNumber, 0, 4), sheet, workbook).setBorder(CellStyle.BORDER_MEDIUM).setInternalBorders(CellStyle.BORDER_THIN);
        new RegionFormatter(new CellRangeAddress(firstRowNumber, lastRowNumber, 0, 4), sheet, workbook).setBorder(CellStyle.BORDER_MEDIUM);
    }

    private void writeRowCurrentDate(final Workbook workbook, final Sheet sheet, final int rowNumber, final Date today) {
        Row currentDateRow = sheet.createRow(rowNumber);
        // DateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat currentDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Cell cell = currentDateRow.createCell(1);
        cell.setCellValue(currentDateFormat.format(today));
        /*
         * DataFormat dataFormat = workbook.createDataFormat();
         * dataFormat.getFormat("dd-MMM-yyyy"); formatCellValue(cell);
         */
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 1, 3), sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER).setBorder(CellStyle.BORDER_MEDIUM).mergeRegion();
    }

    private void writeRowDateRange(final Workbook workbook, final Sheet sheet, final int rowNumber, final ReportValuesSearchSpec searchSpec) {
        Row dateRangeRow = sheet.createRow(rowNumber);
        dateRangeRow.createCell(0).setCellValue(searchSpec.getHumanReadableMonthRange());
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 0, 0), sheet, workbook).setFont(redFont);
        // TODO: Find the way of doing this below properly
        dateRangeRow.getCell(0).getCellStyle().setFont(this.redFont);
    }

    private void writeRowLabels(final Workbook workbook, final Sheet sheet, final int rowNumber, final String columnA, final String columnB) {
        Row labelsRow = sheet.createRow(rowNumber);
        int col = 1;
        for (String label : new String[]{columnA, columnB, "Diff", "Notes"}) {
            labelsRow.createCell(col++).setCellValue(label);
        }
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 1, 4), sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER).setBorder(CellStyle.BORDER_MEDIUM).setInternalBorders(CellStyle.BORDER_MEDIUM);
    }

    private void writeRowSectionTitle(final Workbook workbook, final Sheet sheet, final int rowNumber, final ReportValuesSearchSpec searchSpec) {
        Row systemNameRow = sheet.createRow(rowNumber);
        CellRangeAddress systemNameRange = new CellRangeAddress(rowNumber, rowNumber, 0, 3);
        systemNameRow.createCell(0).setCellValue(searchSpec.getSectionTitle());
        new RegionFormatter(systemNameRange, sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER).setBorder(CellStyle.BORDER_MEDIUM).setFillPattern(CellStyle.SOLID_FOREGROUND).mergeRegion();
        // TODO: Find the way of doing this below properly
        ((XSSFCellStyle) systemNameRow.getCell(0).getCellStyle()).setFillForegroundColor(new XSSFColor(new java.awt.Color(169, 208, 142)));
    }

    private void writeRowSurveyType(final Workbook workbook, final Sheet sheet, final int rowNumber, final ReportValuesSearchSpec searchSpec, final float rowHeight, final String surveyTypeLabel, final String surveyTypeNotes) {
        Row surveyTypeRow = sheet.createRow(rowNumber);
        surveyTypeRow.setHeightInPoints(rowHeight);
        String surveyString = searchSpec.getSurveyType();
        if (!MULTI_SELECT_ALL.equals(searchSpec.getPatientType())) {
            surveyString += " " + searchSpec.getPatientType();
        }
        surveyTypeRow.createCell(0).setCellValue(surveyString);
        surveyTypeRow.createCell(1).setCellValue(surveyTypeLabel);
        surveyTypeRow.createCell(4).setCellValue(surveyTypeNotes);
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 0, 0), sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER).setBorder(CellStyle.BORDER_MEDIUM).setFont(this.boldRedFont);
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 1, 3), sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER).setBorder(CellStyle.BORDER_MEDIUM).setFillForegroundColor(IndexedColors.YELLOW.getIndex()).setFillPattern(CellStyle.SOLID_FOREGROUND).setWrapText(true).mergeRegion();
        new RegionFormatter(new CellRangeAddress(rowNumber, rowNumber, 4, 4), sheet, workbook).setAlignment(CellStyle.ALIGN_CENTER).setWrapText(true);
        // TODO: Find the way of doing this below properly
        surveyTypeRow.getCell(0).getCellStyle().setFont(this.boldRedFont);
    }

    /**
     * Writes section header (both tabs).
     *
     * @param workbook
     * @param sheet
     * @param startingRowNumber
     * @param searchSpec        S
     * @param today             Today's date.
     * @return Next row number to the last.
     */
    private int writeSectionHeader(final Workbook workbook, final Sheet sheet, final int startingRowNumber, final ReportValuesSearchSpec searchSpec, final Date today, final String labelA, final String labelB, final float surveyTypeRowHeight, final String surveyTypeLabel, final String surveyTypeNotes) {
        int rowNumber = startingRowNumber;
        this.writeRowSectionTitle(workbook, sheet, rowNumber++, searchSpec);
        this.writeRowCurrentDate(workbook, sheet, rowNumber++, today);
        this.writeRowLabels(workbook, sheet, rowNumber++, labelA, labelB);
        this.writeRowSurveyType(workbook, sheet, rowNumber++, searchSpec, surveyTypeRowHeight, surveyTypeLabel, surveyTypeNotes);
        return rowNumber;
    }

    /**
     * Writes totals (first tab).
     *
     * @param workbook                     Spreadsheet workbook.
     * @param sheet                        Workbook sheet.
     * @param startingRowNumber            Starting row number of the section.
     * @param searchSpec                   Search specification used to get the values.
     * @param crossEnvironmentReportValues Values to write.
     * @param today                        Today's date.
     * @return Next row to last (i.e., row to next section).
     */
    private int writeTotals(Workbook workbook, Sheet sheet, int startingRowNumber, ReportValuesSearchSpec searchSpec, CrossEnvironmentReportValues crossEnvironmentReportValues, Date today) {
        int rowNumber = this.writeSectionHeader(workbook, sheet, startingRowNumber, searchSpec, today, LABEL_TOTALS_A, LABEL_TOTALS_B, ROW_HEIGHT_SURVEY_TYPE_TOTALS, SURVEY_TYPE_LABEL_TOTALS, "");
        int dateRangeRowNumber = rowNumber;
        this.writeRowDateRange(workbook, sheet, rowNumber++, searchSpec);

        // Qualified
        boolean qualified = searchSpec.isQualifiedEnabled();
        Object valueA = null, valueB = null;
        if (qualified) {
            valueA = this.getValue(crossEnvironmentReportValues, Environment.PRODUCTION, DataSet.QUALIFIED, ITEM_NAME_TOTAL);
            valueB = this.getValue(crossEnvironmentReportValues, Environment.QA, DataSet.QUALIFIED, ITEM_NAME_TOTAL);
        }
        this.writeDataSetCount(qualified, sheet, rowNumber++, valueA, valueB, "Qualified");

        // All
        valueA = this.getValue(crossEnvironmentReportValues, Environment.PRODUCTION, DataSet.ALL, ITEM_NAME_TOTAL);
        valueB = this.getValue(crossEnvironmentReportValues, Environment.QA, DataSet.ALL, ITEM_NAME_TOTAL);
        this.writeDataSetCount(true, sheet, rowNumber, valueA, valueB, "All");

        this.writeFinalBorders(workbook, sheet, startingRowNumber, dateRangeRowNumber, rowNumber++);

        return rowNumber;
    }

    private void writeWorkbook(Workbook workbook, String outputFilename) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(outputFilename);
        workbook.write(fileOut);
        fileOut.close();
    }

    private static class DecoratedOverviewValues {
        public final int recordNumber;
        public final ReportValuesSearchSpec searchSpec;
        public final CrossEnvironmentReportValues crossEnvironmentReportValues;

        public DecoratedOverviewValues(final int recordNumber, final ReportValuesSearchSpec searchSpec, final CrossEnvironmentReportValues crossEnvironmentReportValues) {
            this.recordNumber = recordNumber;
            this.searchSpec = searchSpec;
            this.crossEnvironmentReportValues = crossEnvironmentReportValues;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("DecoratedOverviewValues{");
            sb.append("rec#=").append(recordNumber);
            sb.append(", searchSpec=").append(searchSpec);
            sb.append(", values=").append(crossEnvironmentReportValues);
            sb.append('}');
            return sb.toString();
        }
    }
}
