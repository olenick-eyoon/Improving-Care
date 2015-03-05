package com.olenick.avatar.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Port functionality.
 */
public class ErrorHandler {

    File outputScreenshot;

    String parentFolder = "", screenshotsFolder = "", logsFolder = "";
    String computerName, scenario, errorTimeStamp;
    PrintWriter errorLog;

    /**
     * @return the computerName
     */
    public String getComputerName() {
        return computerName;
    }

    /**
     * @param computerName the computerName to set
     */
    public ErrorHandler setComputerName(String computerName) {
        this.computerName = computerName;
        return this;
    }

    /**
     * @return the scenario
     */
    public String getScenario() {
        return scenario;
    }

    /**
     * @param scenario the scenario to set
     */
    public ErrorHandler setScenario(String scenario) {
        this.scenario = scenario;
        return this;
    }

    /**
     * @return the errorTimeStamp
     */
    public String getErrorTimeStamp() {
        return errorTimeStamp;
    }

    /**
     * @param errorTimeStamp the errorTimeStamp to set
     */
    public ErrorHandler setErrorTimeStamp(String errorTimeStamp) {
        this.errorTimeStamp = errorTimeStamp;
        return this;
    }

    /**
     * @return the errorLog
     */
    public PrintWriter getErrorLog() {
        return errorLog;
    }

    /**
     * @return the screenshotsFolder
     */
    public String getScreenshotsFolder() {
        return screenshotsFolder;
    }

    /**
     * @param screenshotsFolder the screenshotsFolder to set
     */
    public ErrorHandler setScreenshotsFolder(String screenshotsFolder) {
        this.screenshotsFolder = screenshotsFolder;
        return this;
    }

    /**
     * @return the logsFolder
     */
    public String getLogsFolder() {
        return logsFolder;
    }

    /**
     * @param logsFolder the logsFolder to set
     */
    public ErrorHandler setLogsFolder(String logsFolder) {
        this.logsFolder = logsFolder;
        return this;
    }

    /**
     * @return the parentFolder
     */
    public String getParentFolder() {
        return parentFolder;
    }

    /**
     * @param parentFolder the parentFolder to set
     */
    public ErrorHandler setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
        return this;
    }

    public ErrorHandler initializeLog(String[] args)
            throws FileNotFoundException, UnsupportedEncodingException {
        errorLog = new PrintWriter("./resources/errors/logs/ERROR_"
                + computerName + "_" + args[0].replace(".xml", "") + "_"
                + errorTimeStamp + ".log", "UTF-8");
        return this;
    }

    public ErrorHandler addEventToLog(boolean noSuchElement,
            boolean elementNotAttached, boolean otherException, Exception e) {

        if (noSuchElement) {
            errorLog.println(errorTimeStamp + " CANNOT FIND ELEMENT.");
        }

        if (elementNotAttached) {
            errorLog.println(errorTimeStamp
                    + " ELEMENT NO LONGER ATTACHED TO THE DOM.");
        }

        if (otherException) {
            errorLog.println(errorTimeStamp + " OTHER EXCEPTION.");
        }

        errorLog.println("Screenshot: " + outputScreenshot.getPath());
        errorLog.println("-----STACK TRACE------");
        e.printStackTrace(errorLog);
        errorLog.println("\n");

        return this;
    }

    public void closeLog() {
        errorLog.close();
    }

    public void takeScreenshot(WebDriver driver) throws IOException {
        outputScreenshot = new File(screenshotsFolder + "screenshot_"
                + errorTimeStamp + ".png");
        FileUtils.copyFile(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                outputScreenshot);
    }

    public ErrorHandler initializePaths() {
        setParentFolder("./resources//errors//");
        setLogsFolder(getParentFolder() + "logs//");
        setScreenshotsFolder(getParentFolder() + "screenshots//");
        return this;
    }

    public void wrapUpFile() {
        if (errorLog != null)
            closeLog();
    }

}
