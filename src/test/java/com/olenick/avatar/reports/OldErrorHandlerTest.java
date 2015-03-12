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
public class OldErrorHandlerTest {
    OldErrorHandler oldErrorHandler = new OldErrorHandler();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void createErrorHandler() {
        OldErrorHandler oldErrorHandler = new OldErrorHandler();
        assertNotNull(oldErrorHandler);
    }

    @Test
    public void setParentFolder() {
        oldErrorHandler.setParentFolder(".\\resources\\errors");
        assertNotEquals(oldErrorHandler.getParentFolder(), "");
    }

    @Test
    public void getParentFolder() {
        oldErrorHandler.setParentFolder(".\\resources\\errors");
        assertEquals(oldErrorHandler.getParentFolder(), ".\\resources\\errors");
    }

    @Test
    public void setScreenshotFolder() {
        oldErrorHandler.setParentFolder(".\\resources\\errors");
        oldErrorHandler.setScreenshotsFolder(oldErrorHandler.getParentFolder()
                + "\\screenshots");
        assertNotEquals(oldErrorHandler.getScreenshotsFolder(), "");
    }

    @Test
    public void getScreenshotFolder() {
        oldErrorHandler.setParentFolder(".\\resources\\errors");
        oldErrorHandler.setScreenshotsFolder(oldErrorHandler.getParentFolder()
                + "\\screenshots");
        assertEquals(oldErrorHandler.getScreenshotsFolder(),
                ".\\resources\\errors\\screenshots");
    }

    @Test
    public void setLogsFolder() {
        oldErrorHandler.setParentFolder(".\\resources\\errors");
        oldErrorHandler.setLogsFolder(oldErrorHandler.getParentFolder() + "\\logs");
        assertNotEquals(oldErrorHandler.getLogsFolder(), "");
    }

    @Test
    public void getLogsFolder() {
        oldErrorHandler.setParentFolder(".\\resources\\errors");
        oldErrorHandler.setLogsFolder(oldErrorHandler.getParentFolder() + "\\logs");
        assertEquals(oldErrorHandler.getLogsFolder(), ".\\resources\\errors\\logs");
    }

    @Test
    public void takeScreenshot() throws IOException {
        oldErrorHandler.setErrorTimeStamp("20140101_143252");
        FirefoxDriver driver = new FirefoxDriver();
        driver.get("http://www.google.com");
        oldErrorHandler.initializePaths();
        oldErrorHandler.takeScreenshot(driver);
        driver.quit();
    }

    @Test
    public void initializePaths() {
        oldErrorHandler.initializePaths();
        assertEquals(oldErrorHandler.getParentFolder(), "./resources//errors//");
        assertEquals(oldErrorHandler.getLogsFolder(),
                "./resources//errors//logs//");
        assertEquals(oldErrorHandler.getScreenshotsFolder(),
                "./resources//errors//screenshots//");
    }
}
