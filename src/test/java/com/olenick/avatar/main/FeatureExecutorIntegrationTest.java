package com.olenick.avatar.main;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.olenick.avatar.SeleniumTest;
import com.olenick.avatar.exceptions.FeatureExecutionException;
import com.olenick.avatar.exceptions.FeatureExecutorListenerException;
import com.olenick.avatar.reports.FeatureTimer;
import com.olenick.avatar.util.FilenameUtils;

/**
 * FeatureExecutor integration test cases.
 */
public class FeatureExecutorIntegrationTest extends SeleniumTest {
    private static final String HOSTNAME = "my-hostname";
    private static final String SCENARIO_SPECS_DIR = "scenario-specs/";
    private static final String FAILURE_BASENAME_NOEXT = "failure_integration_test";
    private static final String FAILURE_BASENAME = FAILURE_BASENAME_NOEXT
            + ".xml";
    private static final String FAILURE_FILENAME = SCENARIO_SPECS_DIR
            + FAILURE_BASENAME;
    private static final String FAILURE_BASENAME_SANITIZED = FAILURE_BASENAME_NOEXT
            + "_xml";
    private static final String SUCCESS_BASENAME_NOEXT = "full_integration_test";
    private static final String SUCCESS_BASENAME = SUCCESS_BASENAME_NOEXT
            + ".xml";
    private static final String SUCCESS_FILENAME = SCENARIO_SPECS_DIR
            + SUCCESS_BASENAME;
    private static final String[] FAILURE_SCENARIO_NAMES = { "XXX", "XXY",
            "XXZ" };
    private static final Map<String, String> FILENAMES_NOEXT = new HashMap<String, String>() {
        {
            put(FAILURE_FILENAME, FAILURE_BASENAME_SANITIZED);
        }
    };

    @Rule
    public TemporaryFolder csvTmpDir = new TemporaryFolder();
    @Rule
    public TemporaryFolder errorLogsTmpDir = new TemporaryFolder();
    @Rule
    public TemporaryFolder screenshotsTmpDir = new TemporaryFolder();

    @Test
    public void test_everything_works_as_expected()
            throws FeatureExecutionException, FeatureExecutorListenerException {
        this.do_test(SUCCESS_FILENAME);
    }

    @Test
    public void test_failure() throws FeatureExecutionException,
            FeatureExecutorListenerException {
        this.do_test(FAILURE_FILENAME);
    }

    private void do_test(String scenarioSpecsXMLFilename)
            throws FeatureExecutionException, FeatureExecutorListenerException {
        FeatureTimer featureTimer = new FeatureTimer(this.csvTmpDir.getRoot()
                .getAbsolutePath(), scenarioSpecsXMLFilename, HOSTNAME);
        FeatureExecutor featureExecutor = new FeatureExecutor(this.driver);
        featureExecutor.addListeners(featureTimer);
        URL xmlFileURL = this.getClass().getClassLoader()
                .getResource(SUCCESS_FILENAME);
        if (xmlFileURL == null) {
            throw new IllegalArgumentException("XML file could not be opened");
        }
        try {
            featureExecutor.execute(URL_ROOT_DEV, xmlFileURL);
        } catch (FeatureExecutionException | RuntimeException exception) {
            this.assertCSVFiles(scenarioSpecsXMLFilename);
            this.assertErrorLogsDir(1, scenarioSpecsXMLFilename);
            this.assertScreenshotsDir(2, FAILURE_SCENARIO_NAMES[0],
                    FAILURE_SCENARIO_NAMES[1]);
            throw exception;
        }
        this.assertCSVFiles(scenarioSpecsXMLFilename);
        this.assertEmptyErrorLogsDir();
        this.assertEmptyScreenshotsDir();
    }

    private void assertCSVFiles(String scenarioSpecsXMLFilename) {
        File[] files = this.csvTmpDir.getRoot().listFiles();
        Assert.assertNotNull(files);
        Assert.assertEquals(1, files.length);
        this.assertCSVFile(files[0], scenarioSpecsXMLFilename);
    }

    private void assertCSVFile(File csvFile, String scenarioSpecsXMLFilename) {
        Assert.assertTrue(csvFile.getName().startsWith(
                FilenameUtils.basename(scenarioSpecsXMLFilename).replace(
                        ".xml", "")
                        + "_" + HOSTNAME + "_"));
        Assert.assertTrue(csvFile.getName().endsWith(".csv"));
    }

    private void assertEmptyDir(TemporaryFolder dir) {
        File[] files = dir.getRoot().listFiles();
        Assert.assertNotNull(files);
        Assert.assertEquals(0, files.length);
    }

    private void assertEmptyErrorLogsDir() {
        this.assertEmptyDir(this.errorLogsTmpDir);
    }

    private void assertEmptyScreenshotsDir() {
        this.assertEmptyDir(this.screenshotsTmpDir);
    }

    private void assertErrorLogsDir(int numberOfFiles,
            String scenarioSpecsXMLFilename) {
        File[] errorLogFiles = this.errorLogsTmpDir.getRoot().listFiles();
        Assert.assertNotNull(errorLogFiles);
        Assert.assertEquals(numberOfFiles, errorLogFiles.length);
        if (numberOfFiles > 0) {
            for (File errorLogFile : errorLogFiles) {
                this.assertErrorLogFile(errorLogFile, scenarioSpecsXMLFilename);
            }
        }
    }

    private void assertErrorLogFile(File errorLogFile,
            String scenarioSpecsXMLFilename) {
        Assert.assertTrue(errorLogFile.getName().startsWith("error_"
                        + FILENAMES_NOEXT.get(scenarioSpecsXMLFilename)
                        + "_"));
        Assert.assertTrue(errorLogFile.getName().endsWith(".log"));
    }

    private void assertScreenshotsDir(int numberOfFiles,
            String... scenarioNames) {
        File[] screenshotFiles = this.screenshotsTmpDir.getRoot().listFiles();
        Assert.assertNotNull(screenshotFiles);
        Assert.assertEquals(numberOfFiles, screenshotFiles.length);
        if (numberOfFiles > 0) {
            int i = 0;
            for (File screenshotFile : screenshotFiles) {
                this.assertScreenshotFile(screenshotFile, scenarioNames[i++]);
            }
        }
    }

    private void assertScreenshotFile(File screenshotFile, String scenarioName) {
        Assert.assertTrue(screenshotFile.getName().startsWith(
                "screenshot_" + scenarioName + "_"));
        Assert.assertTrue(screenshotFile.getName().endsWith(".png"));
    }
}
