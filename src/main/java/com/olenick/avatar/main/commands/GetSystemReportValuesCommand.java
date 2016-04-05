package com.olenick.avatar.main.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
import com.olenick.avatar.model.SurveyType;
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
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
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
    private static final int NUMBER_OF_AVAILABLE_USERS = 4;
    private static final long TIMEOUT_SECS_FETCH_VALUES = 900L;
    private static final int NUMBER_OF_RECORD_THREADS = 3;
    private static final int RETRIES = 3;
    private static final int ALERT_RETRIES = 3;
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
    private static final String ITEM_NAME_TOTAL_AVATAR = "Core Total";
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
    private static final String OUTPUT_FOLDER_NAME = "Output\\";
    private static final String SCREENSHOTS_FOLDER_NAME = "Screenshots\\";
    private static final String LOGS_FOLDER_NAME = "Logs\\";

    private final String inputCSVFilename;
    private final String outputXLSXFilename;
    private final String screenshotsFolderName;
    private final String logsFolderName;
    private final String logFileName;

    private final Map<Environment, BlockingQueue<User>> users;

    private Font boldRedFont;
    private Font redFont;

    public GetSystemReportValuesCommand(@NotNull final String inputCSVFilename, @NotNull final String outputXLSXFilename) {
        String dataChecksStartTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        this.inputCSVFilename = inputCSVFilename;
        this.outputXLSXFilename = outputXLSXFilename;
        this.users = new HashMap<>();

        int maxUsersAvailable = Math.min(NUMBER_OF_RECORD_THREADS, NUMBER_OF_AVAILABLE_USERS);

        for (Environment environment : Environment.values()) {
            this.users.put(environment, new ArrayBlockingQueue<User>(maxUsersAvailable));

            for (int i = 1; i <= maxUsersAvailable; ++i) {
                this.users.get(environment).add(new User(String.format(USERNAME_TEMPLATE, i), PASSWORD));
            }
        }

        this.logsFolderName = String.format("%s%s", OUTPUT_FOLDER_NAME, LOGS_FOLDER_NAME);
        this.logFileName = String.format("%s%s-%s", this.logsFolderName,
            dataChecksStartTime, this.inputCSVFilename.replace(".csv", ".txt"));

        this.screenshotsFolderName = String.format("%s%s%s-%s", OUTPUT_FOLDER_NAME, SCREENSHOTS_FOLDER_NAME,
                dataChecksStartTime, this.inputCSVFilename.replace(".csv", ""));

        File screenshots = new File(this.screenshotsFolderName);
        File logs = new File(this.logsFolderName);
        File logFile = new File(this.logFileName);

        if (!screenshots.exists())
            if (!screenshots.mkdirs()) {
                log.error("Screenshots folder creation failed.");
            }
        if (!logs.exists())
            if (!logs.mkdirs()) {
                log.error("Logs folder creation failed.");
            }

/*
        try {
            if (logFile.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(logFile);
                PrintStream ps = new PrintStream(fos);
                System.setOut(ps);
            }
        } catch (IOException exception) {
            log.warn("Redirect of console output to local file was not possible.");
        }
*/
    }

    /**
     * Get a map of SearchSpecs records with their line numbers in CSV
     * @return HashMap<Integer, ReportValuesSearchSpec>
     * @throws Exception
     */
    private Map<Environment, Map<String, List<ReportValuesSearchSpec>>> loadAndValidateInputCSV() throws ParseException, IOException {
        boolean parsingErrorFound = false;

        File inputCSVFile = new File(this.inputCSVFilename);
        CSVParser parser = CSVParser.parse(inputCSVFile, Charset.forName("UTF-8"), CSVFormat.EXCEL);
        List<CSVRecord> records = parser.getRecords();

        long rowNumberInCSV = 1;
        long recordNumber = 1;
        Map<Environment, Map<String, List<ReportValuesSearchSpec>>> listMapPerEnvironment = new EnumMap<>(Environment.class);

        //Sort by system only for now
        Collections.sort(records, new Comparator<CSVRecord>() {
            @Override
            public int compare(CSVRecord r1, CSVRecord r2) {
                return r1.get(CSVParameters.SYSTEM_CODE_POSITION.GetValue()).compareTo(
                        r2.get(CSVParameters.SYSTEM_CODE_POSITION.GetValue()));
            }
        });

        for (CSVRecord record : records) {
            try {
                for (ReportValuesSearchSpec searchSpec : this.getSearchSpec(inputCSVFile, record)) {
                    PopulateEnvironmentsMap(rowNumberInCSV, recordNumber, listMapPerEnvironment, searchSpec);

                    recordNumber++;
                }

                rowNumberInCSV++;
            }
            catch (ParseException e) {
                parsingErrorFound = true;
                log.error("CSV File: " + inputCSVFile + ", record #" + rowNumberInCSV + " - " + e.getMessage());
            }
        }

        if (parsingErrorFound) throw new ParseException("Invalid input CSV.");

        return listMapPerEnvironment;
    }

    private void PopulateEnvironmentsMap(long rowNumberInCSV, long recordNumber, Map<Environment, Map<String, List<ReportValuesSearchSpec>>> listMapPerEnvironment, ReportValuesSearchSpec searchSpec) throws ParseException {
        searchSpec.setRowNumberInCSV(rowNumberInCSV);
        searchSpec.setRecordNumber(recordNumber);
        String systemCode = searchSpec.getSystemCode();

        for (Environment environment : searchSpec.getEnvironments()) {
            if (!listMapPerEnvironment.containsKey(environment))
                listMapPerEnvironment.put(environment, new HashMap<String, List<ReportValuesSearchSpec>>());

            ReportValuesSearchSpec clone = searchSpec.clone();
            clone.setEnvironments(environment.name());

            if (!listMapPerEnvironment.get(environment).containsKey(systemCode))
                listMapPerEnvironment.get(environment).put(systemCode, new ArrayList<ReportValuesSearchSpec>());

            listMapPerEnvironment.get(environment).get(systemCode).add(clone);
        }
    }

    ValueFetcherThreadPool threadPool = new ValueFetcherThreadPool();

    public List<Future<Map<ReportValuesSearchSpec, DataSetReportValues>>> Submit(Map<Environment, Map<String, List<ReportValuesSearchSpec>>> environmentSearchSpecs) {
        List<Future<Map<ReportValuesSearchSpec, DataSetReportValues>>> results = new ArrayList<>();

        for (Map.Entry<Environment, Map<String, List<ReportValuesSearchSpec>>> envEntry : environmentSearchSpecs.entrySet()) {
            Environment env = envEntry.getKey();
            Map<String, List<ReportValuesSearchSpec>> systemSearchSpecs = envEntry.getValue();

            for (Map.Entry<String, List<ReportValuesSearchSpec>> sysEntry : systemSearchSpecs.entrySet()) {
                String sys = sysEntry.getKey();
                List<ReportValuesSearchSpec> list = sysEntry.getValue();

                Future<Map<ReportValuesSearchSpec, DataSetReportValues>> result = threadPool.submit(env, sys, list);

                results.add(result);
            }
        }

        return results;
    }

    public Map<Long, DecoratedOverviewValues> collate(List<Future<Map<ReportValuesSearchSpec, DataSetReportValues>>> results) throws FetchSystemTotalsException {
//    public List<DecoratedOverviewValues> collate(List<Future<Map<ReportValuesSearchSpec, DataSetReportValues>>> results) throws FetchSystemTotalsException {
//        Set<DecoratedOverviewValues> collatedResults = new TreeSet<>();
        Environment env = null;

        Map<Long, DecoratedOverviewValues> groupedResults = new HashMap<>();

        for (Future<Map<ReportValuesSearchSpec, DataSetReportValues>> futureMap : results) {
            try {
                for (Map.Entry<ReportValuesSearchSpec, DataSetReportValues> entry : futureMap.get().entrySet()) {
                    ReportValuesSearchSpec searchSpec = entry.getKey();
                    DataSetReportValues dataSetReportValues = entry.getValue();
                    env = searchSpec.getEnvironments().get(0);

//                    CrossEnvironmentReportValues crossEnvironmentReportValues = new CrossEnvironmentReportValues();
//                    crossEnvironmentReportValues.set(env, dataSetReportValues);

//                    collatedResults.add(new DecoratedOverviewValues(searchSpec, crossEnvironmentReportValues));

                    if (!groupedResults.containsKey(searchSpec.getRecordNumber())) {
                        CrossEnvironmentReportValues crossEnvironmentReportValues = new CrossEnvironmentReportValues();
                        crossEnvironmentReportValues.set(env, dataSetReportValues);
                        groupedResults.put(searchSpec.getRecordNumber(), new DecoratedOverviewValues(searchSpec, crossEnvironmentReportValues));
                    } else {
                        DecoratedOverviewValues pair = groupedResults.get(searchSpec.getRecordNumber());
                        pair.searchSpec.getEnvironments().addAll(searchSpec.getEnvironments());
                        pair.crossEnvironmentReportValues.set(env, dataSetReportValues);
                    }
                }
            } catch (ExecutionException exception) {
                log.warn("Error while fetching values for a record", exception);
            } catch (InterruptedException exception) {
                throw new FetchSystemTotalsException("While fetching a record's values", exception);
            }
        }

//        return new ArrayList<>(collatedResults);
        return groupedResults;
    }

    /**
     * TODO: Split this method.
     *
     * @throws Exception
     */
    @Override
    public void execute() throws Exception {
        log.info("Avatar Data Checks execution started...");

        Map<Environment, Map<String, List<ReportValuesSearchSpec>>> environmentSearchSpecs = loadAndValidateInputCSV();

        threadPool.init(environmentSearchSpecs);

//        final int environmentExecs = environmentSearchSpecs.size();

        Workbook workbook = new XSSFWorkbook();
        Sheet totalsSheet = this.createSheet(workbook, SHEET_NAME_TOTALS, COLUMN_WIDTH_1_TOTALS);
        Sheet allValuesSheet = this.createSheet(workbook, SHEET_NAME_ALL_VALUES, COLUMN_WIDTH_1_ALL_VALUES);
        this.createFonts(workbook);

//        final ExecutorService recordThreadPool = Executors.newFixedThreadPool(Environment.values().length);
//
        List<Future<Map<ReportValuesSearchSpec, DataSetReportValues>>> futures = Submit(environmentSearchSpecs);

/*        for (final Map.Entry<Environment, Map<String, List<ReportValuesSearchSpec>>> environmentSearchMap : environmentSearchSpecs.entrySet()) {
            //By environment
            futures.add(recordThreadPool.submit(new Callable<List<DecoratedOverviewValues>>() {
                @Override
                public List<DecoratedOverviewValues> call() throws Exception {
                    boolean done = false;
                    int trial = 0;

                    List<DecoratedOverviewValues> values = null;

                    while (!done && trial++ < RETRIES) {
                        try {
                            values = fetchValuesPerEnvironment(environmentSearchMap);

                            done = true;
                        } catch (Exception e) {
                            done = false;
                            log.error("Trial #: " + trial + ". Failed fetching values for environment thread " + environmentSearchMap.getKey(), e);
                        }
                    }

                    if (values == null) {
                        recordThreadPool.shutdown();
                        log.error("Failed fetching values for environment <X>");
                        throw new FetchSystemTotalsException("Error fetching values for environment " + environmentSearchMap.getKey());
                    }

                    return values;
                }
            }));
        }*/

        Map<Long, DecoratedOverviewValues> collatedResults;
        try {
            collatedResults = collate(futures);
        } finally {
            threadPool.shutdown();
        }

        Date today = new Date();
        int totalsTabRowNumber = 0;
        int specificOrgsTabRowNumber = 0;

        List<Long> keys = new ArrayList<>(collatedResults.keySet());
        keys.sort(new Comparator<Long>() {
            @Override
            public int compare(Long r1, Long r2) {
                return r1.compareTo(r2);
            }
        });

//        for (Future<List<DecoratedOverviewValues>> future : futures) {
//            try {
//                List<DecoratedOverviewValues> decoratedOverviewValues = future.get();

//                for (DecoratedOverviewValues values : decoratedOverviewValues) {
                for (Long key : keys) {
                    DecoratedOverviewValues decor = collatedResults.get(key);
                    if (decor.searchSpec.getSheetNumber() == 0) {
                        totalsTabRowNumber = this.writeTotals(workbook, totalsSheet, totalsTabRowNumber, decor.searchSpec, decor.crossEnvironmentReportValues, today);
                    } else {
                        specificOrgsTabRowNumber = this.writeAllOverviewValues(workbook, allValuesSheet, specificOrgsTabRowNumber, decor.searchSpec, decor.crossEnvironmentReportValues, today);
                    }

                    try {
                        this.writeWorkbook(workbook, outputXLSXFilename);
                    } catch (FileNotFoundException e) {
                        log.warn("The output file probably exists and it's taken by another process. Trying saving with and incremented renaming.");
                        log.warn("You have one retry only, please release the file and hit a key to try to save again.");
                        System.in.read();
                        this.writeWorkbook(workbook, outputXLSXFilename);
                    }
                }
//            } catch (ExecutionException exception) {
//                log.warn("Error while fetching values for a record");
//            } catch (IOException | InterruptedException exception) {
//                recordThreadPool.shutdown();
//                throw new FetchSystemTotalsException("While fetching a record's values", exception);
//            }
//        }
//        recordThreadPool.shutdown();

        log.info("...Avatar Data Checks execution finished.");
    }

    private List<DecoratedOverviewValues> fetchValuesPerEnvironment(Map.Entry<Environment, Map<String, List<ReportValuesSearchSpec>>> searchSpecListByEnvironment) throws FetchSystemTotalsException {
        List<DecoratedOverviewValues> values = new ArrayList<>();

        //Predefined threads count
        final ExecutorService envThreadPool = Executors.newFixedThreadPool(NUMBER_OF_RECORD_THREADS);
        //One future for each system
        HashMap<String, Future<Map<ReportValuesSearchSpec, DataSetReportValues>>> futures = new HashMap<>();

        //Web driver engine
        //TODO: Send this to a property, even consider the possibility of choosing which driver to use per search spec
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\eyoon\\Downloads\\Selenium\\Drivers\\Chrome\\chromedriver.exe");
        //driver = new ExtendedRemoteWebDriver(new FirefoxDriver());

        final GetSystemReportValuesCommand self = this;

        for (final Map.Entry<String, List<ReportValuesSearchSpec>> systemSearchEntry : searchSpecListByEnvironment.getValue().entrySet()) {
            futures.put(systemSearchEntry.getKey(), envThreadPool.submit(new Callable<Map<ReportValuesSearchSpec, DataSetReportValues>>() {
                @Override
                public Map<ReportValuesSearchSpec, DataSetReportValues> call() throws Exception {
                    Map<ReportValuesSearchSpec, DataSetReportValues> result = new HashMap<>();
                    boolean done = false;

                    int trial = 0;
                    int successRecordIndex = 0;

                    List<ReportValuesSearchSpec> searchSpecList = systemSearchEntry.getValue();
                    Environment searchEnvironment = searchSpecList.get(0).getEnvironments().get(0);
                    ReportValuesSearchSpec searchSpec = null;

                    //Tries per system
                    while (!done && trial++ < RETRIES) {
                        User user = users.get(searchEnvironment).poll(TIMEOUT_SECS_FETCH_VALUES, TimeUnit.SECONDS);

                        //TODO: Part of the previous TODO, set the web driver and configs by properties
                        final ExtendedRemoteWebDriver driver = new ExtendedRemoteWebDriver(new ChromeDriver());

                        PatientExperienceIFrame patientExperienceIFrame = self.loadPatientExperience(driver, searchEnvironment.getURLRoot(), user.getUsername(), user.getPassword(), searchEnvironment);

                        try {
                            for (int recordIndex = successRecordIndex; recordIndex < searchSpecList.size(); recordIndex++) {
                                searchSpec = searchSpecList.get(recordIndex);

                                final ReportFilter reportFilter = self.buildReportFilter(searchSpec);

                                //TODO: This should be implemented correctly in page objects for QA
                                final ReportFilter reportFilterByEnvironment = reportFilter.clone();

                                if (searchSpec.getSurveyType().compareToIgnoreCase("Avatar") == 0 && searchSpec.getEnvironments().contains(Environment.QA)) {
                                    reportFilterByEnvironment.setItemsToIncludeCore(true);
                                    reportFilterByEnvironment.setItemsToIncludeCustom(false);
                                    reportFilterByEnvironment.setItemsToIncludeAncillary(false);
                                }

                                result.put(searchSpec, fetchValues(driver, patientExperienceIFrame, searchSpec, reportFilterByEnvironment));

                                successRecordIndex++;
                            }
                            done = true;
                        } catch (ElementNotLoadedException
                                | WebDriverException exception) {
                            if (exception instanceof UnhandledAlertException) {
                                log.error("Error by unexpected alert open. Must retry.");
                                log.warn("Trial #: " + trial + ". Error fetching values for " + searchSpec);
                            } else if (exception instanceof NoSuchElementException) {

                            } else
                                log.warn("Trial #: " + trial + ". Error fetching values for " + searchSpec, exception);
                        } catch (Exception exception) {
                            log.error("Failed fetching values for " + searchSpec, exception);
                            throw exception;
                        } finally {
                            try {
                                users.get(searchEnvironment).offer(user);

                                if (driver != null) {
                                    driver.quit();
                                }
                            } catch (WebDriverException ignored) {
                                log.error("Unable to make driver quit.");
                            }
                        }
                    }

                    if (result == null) {
                        log.error("Failed fetching values for " + searchSpec);
                        throw new FetchSystemTotalsException("Error fetching values for " + searchSpec);
                    }

                    return result;
                }
            }));
        }

        for (Map.Entry<String, Future<Map<ReportValuesSearchSpec, DataSetReportValues>>> futureEntry : futures.entrySet()) {
            String ifExceptionMessage = "Can't iterate report values";
            try {
                for (Map.Entry<ReportValuesSearchSpec, DataSetReportValues> data : futureEntry.getValue().get().entrySet()) {
                    ReportValuesSearchSpec searchSpec = data.getKey();
                    ifExceptionMessage = searchSpec.toString();

                    CrossEnvironmentReportValues crossEnvironmentReportValues = new CrossEnvironmentReportValues();
                    crossEnvironmentReportValues.set(searchSpec.getEnvironments().get(0), data.getValue());
                    values.add(new DecoratedOverviewValues(searchSpec, crossEnvironmentReportValues));
                }
            } catch (ExecutionException exception) {
                log.error("Error while fetching a pair of value maps for " + ifExceptionMessage);
            } catch (InterruptedException exception) {
                throw new FetchSystemTotalsException("While fetching " + ifExceptionMessage, exception);
            }
        }
        envThreadPool.shutdown();

        return values.size() > 0? values : null;
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

    /**
     * TODO: Split this method.
     *
     * @param searchSpec   Search specification for overview values.
     * @param reportFilter "Template" report filter.
     * @return Overview values for all data-sets.
     */
    private DataSetReportValues fetchValues(ExtendedRemoteWebDriver driver, PatientExperienceIFrame patientExperienceIFrame, ReportValuesSearchSpec searchSpec, ReportFilter reportFilter) {
        log.info("Fetching values for " + searchSpec);
        DataSetReportValues result = new DataSetReportValues();

        ReportTab reportTab = this.getReportTab(searchSpec);
        DataSet dataSet = DataSet.ALL;
        ReportFilter myReportFilter = reportFilter.clone();
        myReportFilter.setDataSet(dataSet);
        Environment searchEnvironment = searchSpec.getEnvironments().get(0);
        ReportValues valuesAll = patientExperienceIFrame.accessPanelFrame().changeSystem(myReportFilter).configureFilters(myReportFilter, searchEnvironment).applyConfiguredFilters().waitForElementsToLoad(searchEnvironment).openReportTab(reportTab).waitForElementsToLoad().getValues(searchSpec);
        result.set(dataSet, valuesAll);
        log.info("ReportValues (" + dataSet + "), " + searchSpec.toStringShort() + " = " + valuesAll);
        this.takeScreenshot(driver, searchSpec, searchEnvironment, dataSet);
        if (searchSpec.isQualifiedEnabled()) {
            dataSet = DataSet.QUALIFIED;
            myReportFilter.setDataSet(dataSet);
            ReportValues valuesQualified = patientExperienceIFrame.configureCalculationFilter(myReportFilter, searchEnvironment).applyConfiguredFilters().waitForElementsToLoad(searchEnvironment).openReportTab(reportTab).waitForElementsToLoad().getValues(searchSpec);
            result.set(dataSet, valuesQualified);
            log.info("ReportValues (" + dataSet + "), " + searchSpec.toStringShort() + " = " + valuesQualified);
            this.takeScreenshot(driver, searchSpec, searchEnvironment, dataSet);
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
        String csvFilename = csvFile.getName();
        StringBuilder screenshotFilename = new StringBuilder(csvFilename.replace(".csv", "-"));
        screenshotFilename.append(searchSpec.getRecordNumber()).append('-').
            append(environment).append('-').
            append(dataSet).append('-').
            append(searchSpec.getSectionTitle()).append('-').
            append(searchSpec.getSurveyType()).append('-').
            append(searchSpec.getFromMonthSpec().getYear() +
                String.format("%02d", searchSpec.getFromMonthSpec().getMonth().getValue()) + '-').
            append(searchSpec.getToMonthSpec().getYear() +
                String.format("%02d", searchSpec.getToMonthSpec().getMonth().getValue()) + '-').
            append(".png");
        try {
            driver.takeScreenshot(new File(this.screenshotsFolderName, screenshotFilename.toString()));
        } catch (IOException exception) {
            log.error("Failed taking screenshot for " + searchSpec, exception);
        }
    }

    private PatientExperienceIFrame loadPatientExperience(final ExtendedRemoteWebDriver driver, final String urlRoot, final String username, final String password) {
        return loadPatientExperience(driver, urlRoot, username, password, Environment.PRODUCTION);
    }

    private PatientExperienceIFrame loadPatientExperience(final ExtendedRemoteWebDriver driver, final String urlRoot, final String username, final String password, Environment env) {
        PatientExperienceIFrame patientExperienceIFrame = new LoginPage(driver, urlRoot).open().login(username, password).navigateToPatientExperienceTab().waitForElementsToLoad(env);
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
            valueA = this.getValue(crossEnvironmentReportValues, Environment.PRODUCTION, DataSet.QUALIFIED,
                    ITEM_NAME_TOTAL);
            valueB = this.getValue(crossEnvironmentReportValues, Environment.QA, DataSet.QUALIFIED,
                    searchSpec.getSurveyType().compareToIgnoreCase(SurveyType.AVATAR.getValue()) == 0? ITEM_NAME_TOTAL_AVATAR : ITEM_NAME_TOTAL);
        }
        this.writeDataSetCount(qualified, sheet, rowNumber++, valueA, valueB, "Qualified");

        // All
        valueA = this.getValue(crossEnvironmentReportValues, Environment.PRODUCTION, DataSet.ALL,
                ITEM_NAME_TOTAL);
        valueB = this.getValue(crossEnvironmentReportValues, Environment.QA, DataSet.ALL,
                searchSpec.getSurveyType().compareToIgnoreCase(SurveyType.AVATAR.getValue()) == 0? ITEM_NAME_TOTAL_AVATAR : ITEM_NAME_TOTAL);
        this.writeDataSetCount(true, sheet, rowNumber, valueA, valueB, "All");

        this.writeFinalBorders(workbook, sheet, startingRowNumber, dateRangeRowNumber, rowNumber++);

        return rowNumber;
    }

    private void writeWorkbook(Workbook workbook, String outputFilename) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(outputFilename);
        workbook.write(fileOut);
        fileOut.close();
    }

    private static class DecoratedOverviewValues implements Comparable<DecoratedOverviewValues> {
        public final long recordNumber;
        public final ReportValuesSearchSpec searchSpec;
        public final CrossEnvironmentReportValues crossEnvironmentReportValues;

        public DecoratedOverviewValues(final ReportValuesSearchSpec searchSpec, final CrossEnvironmentReportValues crossEnvironmentReportValues) {
            this.recordNumber = searchSpec.getRecordNumber();
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

        @Override
        public int compareTo(DecoratedOverviewValues other) {
            final int BEFORE = -1;
            final int EQUAL = 0;
            final int AFTER = 1;

            if (this == other) return EQUAL;

            if (this.recordNumber < other.recordNumber) return BEFORE;
            if (this.recordNumber > other.recordNumber) return AFTER;

            assert this.equals(other) : "compareTo inconsistent with equals.";

            return EQUAL;
        }
    }

    private class ValueFetcherThreadPool {
        Map<Environment, ExecutorService> threadPools = new HashMap<>();

        public void init(Map<Environment, Map<String, List<ReportValuesSearchSpec>>> environmentSearchSpecs) {
            for (Map.Entry<Environment, Map<String, List<ReportValuesSearchSpec>>> envEntry : environmentSearchSpecs.entrySet()) {
                Environment env = envEntry.getKey();
                Map<String, List<ReportValuesSearchSpec>> systemSearchSpecs = envEntry.getValue();

                threadPools.put(env, Executors.newFixedThreadPool(users.get(env).size()));
            }
        }

        private ExecutorService getPool(Environment env, String sys) {
            return threadPools.get(env);
        }

        public void shutdown() {
            for (ExecutorService executor : threadPools.values()) {
                executor.shutdown();
            }
        }

        Future<Map<ReportValuesSearchSpec, DataSetReportValues>> submit(Environment env, String sys, List<ReportValuesSearchSpec> searchSpecs) {
            return getPool(env, sys).submit(new ValueFetcherPerEnvSys(env, sys, searchSpecs));
        }
    }

    class ValueFetcherPerEnvSys implements Callable<Map<ReportValuesSearchSpec, DataSetReportValues>> {
        final Environment env;
        final String sys;
        final List<ReportValuesSearchSpec> searchSpecs;

        public ValueFetcherPerEnvSys(Environment env, String sys, List<ReportValuesSearchSpec> searchSpecs) {
            this.env = env;
            this.sys = sys;
            this.searchSpecs = searchSpecs;
        }

        private ExtendedRemoteWebDriver initWebDriver() {
            return new ExtendedRemoteWebDriver(new ChromeDriver());
        }

        private PatientExperienceIFrame GetPatientExperience(ExtendedRemoteWebDriver driver, User user) {
            return loadPatientExperience(driver, env.getURLRoot(), user.getUsername(), user.getPassword(), env);
        }

        private boolean isAlertPresent(ExtendedRemoteWebDriver driver) {
            try {
                Alert alert = driver.switchTo().alert();
                log.warn("Closing the following alert message: " + alert.getText());

                return true;
            } catch (NoAlertPresentException Exception) {
                return false;
            }
        }

        private void CloseAlerts(ExtendedRemoteWebDriver driver) {
            while(isAlertPresent(driver))
            {
                try
                {
                    driver.switchTo().alert().accept();
                }
                catch (Exception exception)
                {
                    log.error("Couldn't close alert message/s.", exception);
                }
            }
        }

        @Override
        public Map<ReportValuesSearchSpec, DataSetReportValues> call() throws Exception {
            User user = users.get(env).poll(TIMEOUT_SECS_FETCH_VALUES, TimeUnit.SECONDS);
            int recordsCounter = -1;

            ExtendedRemoteWebDriver driver = initWebDriver();

            Map<ReportValuesSearchSpec, DataSetReportValues> results = new HashMap<>();
            DataSetReportValues fetchResult = null;
            PatientExperienceIFrame patientExperience = null;

            for (ReportValuesSearchSpec searchSpec : searchSpecs) {
                boolean done = false;
                int trial = 0;
                int alertTrial = 0;
                recordsCounter++;

                while (!done && trial++ < RETRIES && alertTrial < ALERT_RETRIES) {
                    try {
                        if(recordsCounter == 0)
                            patientExperience = GetPatientExperience(driver, user);

                        //TODO: This should be implemented correctly in page objects for QA
                        final ReportFilter reportFilterByEnvironment = buildReportFilter(searchSpec);

                        if (searchSpec.getSurveyType().compareToIgnoreCase("Avatar") == 0 && searchSpec.getEnvironments().contains(Environment.QA)) {
                            reportFilterByEnvironment.setItemsToIncludeCore(true);
                            reportFilterByEnvironment.setItemsToIncludeCustom(false);
                            reportFilterByEnvironment.setItemsToIncludeAncillary(false);
                        }

                        fetchResult = fetchValues(driver, patientExperience, searchSpec, reportFilterByEnvironment);

                        done = true;
                    } catch (ElementNotLoadedException
                            | WebDriverException exception) {
                        if (exception instanceof UnhandledAlertException) {
                            log.warn("(1)Error by unexpected alert open. Must retry.");
                            //Alert exception does not count as a retry iteration.
                            trial = Math.max(0, --trial);
                            alertTrial++;

                            CloseAlerts(driver);

                            log.warn("Retrying...");
                            log.warn("Fetching values for " + searchSpec.toStringShort());
                        } else
                            log.warn("(2)Trial #: " + trial + ". Error fetching values for " + searchSpec.toStringShort(), exception);

                        users.get(env).offer(user);

                        try {
                            if (driver != null) {
                                if (trial < 3) {
                                    patientExperience = GetPatientExperience(driver, user);
                                }
                            }
                        } catch (WebDriverException ignored) {
                            log.error("Unable to make driver quit.");
                        }
                    } catch (Exception exception) {
                        log.error("(3)Trial #: " + trial + ". Failed fetching values for " + searchSpec.toStringShort(), exception);

                        users.get(env).offer(user);

                        try {
                            if (driver != null) {
                                if (trial < 3) {
                                    patientExperience = GetPatientExperience(driver, user);
                                }
                            }
                        } catch (WebDriverException ignored) {
                            log.error("Unable to make driver quit.");
                        }
                    }
                }

                if (fetchResult == null) {
                    fetchResult =  new DataSetReportValues();
                }

                results.put(searchSpec, fetchResult);
            }

            users.get(env).offer(user);

            if (driver != null) {
                driver.quit();
            }

            return results;
        }
    }
}
