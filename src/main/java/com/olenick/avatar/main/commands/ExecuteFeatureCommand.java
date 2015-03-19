package com.olenick.avatar.main.commands;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.avatar.exceptions.FeatureExecutionException;
import com.olenick.avatar.exceptions.FeatureExecutorListenerException;
import com.olenick.avatar.main.FeatureExecutor;
import com.olenick.avatar.reports.ErrorHandler;
import com.olenick.avatar.reports.FeatureTimer;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Execute feature command.
 */
public class ExecuteFeatureCommand implements Command {
    private static final String DIR_CSV = "";
    private static final String DIR_ERROR_LOGS = "";
    private static final String DIR_SCREENSHOTS = "";
    private static final String HOSTNAME = "my-hostname";
    private static final String URL_ROOT_DEV = "http://172.16.20.210:8080/ibi_apps";
    private static final String XML_FILE_PATH = "scenario-specs/";

    private final String xmlFilename;

    public ExecuteFeatureCommand(String xmlFilename) {
        this.xmlFilename = xmlFilename;
    }

    public void execute() throws Exception {
        URL xmlFileURL = getXMLFileURL(this.xmlFilename);

        boolean finishedWithException = false;
        ExtendedRemoteWebDriver driver = new ExtendedRemoteWebDriver(
                new FirefoxDriver());
        try {
            FeatureTimer featureTimer = new FeatureTimer(DIR_CSV, xmlFilename,
                    HOSTNAME);
            ErrorHandler errorHandler = new ErrorHandler(driver,
                    DIR_ERROR_LOGS, DIR_SCREENSHOTS, xmlFilename);
            FeatureExecutor featureExecutor = new FeatureExecutor(driver);
            featureExecutor.addListeners(featureTimer, errorHandler);
            featureExecutor.execute(URL_ROOT_DEV, xmlFileURL);
        } catch (FeatureExecutionException | FeatureExecutorListenerException
                | RuntimeException exception) {
            finishedWithException = true;
            throw exception;
        } finally {
            try {
                driver.quit();
            } catch (RuntimeException exception) {
                if (!finishedWithException) {
                    /**
                     * This is NOT masking the exceptions that can occur in the
                     * try/catch section. If anyone knows what the correct @SuppressWarnings
                     * value is, please, add it.
                     */
                    throw exception;
                }
            }
        }
    }

    private URL getXMLFileURL(final String xmlFilename)
            throws MalformedURLException {
        URL xmlFileURL;
        File xmlFile = new File(xmlFilename);
        if (xmlFile.exists()) {
            xmlFileURL = xmlFile.toURI().toURL();
        } else {
            xmlFileURL = this.getClass().getClassLoader()
                    .getResource(XML_FILE_PATH + xmlFilename);
            if (xmlFileURL == null) {
                throw new IllegalArgumentException(
                        "XML file could not be opened");
            }
        }
        return xmlFileURL;
    }
}
