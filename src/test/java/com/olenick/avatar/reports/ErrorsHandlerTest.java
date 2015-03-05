package com.olenick.avatar.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * TO GO.
 */
@Deprecated
@Ignore
public class ErrorsHandlerTest {
    ErrorHandler errorHandler = new ErrorHandler();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void createErrorHandler() {
        ErrorHandler errorHandler = new ErrorHandler();
        assertNotNull(errorHandler);
    }

    @Test
    public void setParentFolder() {
        errorHandler.setParentFolder(".\\resources\\errors");
        assertNotEquals(errorHandler.getParentFolder(), "");
    }

    @Test
    public void getParentFolder() {
        errorHandler.setParentFolder(".\\resources\\errors");
        assertEquals(errorHandler.getParentFolder(), ".\\resources\\errors");
    }

    @Test
    public void setScreenshotFolder() {
        errorHandler.setParentFolder(".\\resources\\errors");
        errorHandler.setScreenshotsFolder(errorHandler.getParentFolder()
                + "\\screenshots");
        assertNotEquals(errorHandler.getScreenshotsFolder(), "");
    }

    @Test
    public void getScreenshotFolder() {
        errorHandler.setParentFolder(".\\resources\\errors");
        errorHandler.setScreenshotsFolder(errorHandler.getParentFolder()
                + "\\screenshots");
        assertEquals(errorHandler.getScreenshotsFolder(),
                ".\\resources\\errors\\screenshots");
    }

    @Test
    public void setLogsFolder() {
        errorHandler.setParentFolder(".\\resources\\errors");
        errorHandler.setLogsFolder(errorHandler.getParentFolder() + "\\logs");
        assertNotEquals(errorHandler.getLogsFolder(), "");
    }

    @Test
    public void getLogsFolder() {
        errorHandler.setParentFolder(".\\resources\\errors");
        errorHandler.setLogsFolder(errorHandler.getParentFolder() + "\\logs");
        assertEquals(errorHandler.getLogsFolder(), ".\\resources\\errors\\logs");
    }

    @Test
    public void takeScreenshot() throws IOException {
        errorHandler.setErrorTimeStamp("20140101_143252");
        FirefoxDriver driver = new FirefoxDriver();
        driver.get("http://www.google.com");
        errorHandler.initializePaths();
        errorHandler.takeScreenshot(driver);
        driver.quit();
    }

    @Test
    public void initializePaths() {
        errorHandler.initializePaths();
        assertEquals(errorHandler.getParentFolder(), "./resources//errors//");
        assertEquals(errorHandler.getLogsFolder(),
                "./resources//errors//logs//");
        assertEquals(errorHandler.getScreenshotsFolder(),
                "./resources//errors//screenshots//");
    }
}
