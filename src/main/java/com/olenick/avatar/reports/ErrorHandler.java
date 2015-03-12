package com.olenick.avatar.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.exceptions.FeatureExecutorListenerException;
import com.olenick.avatar.uses.PatientExperienceFeature;
import com.olenick.avatar.uses.PatientExperienceScenario;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Error Handler listener of FeatureExecutor.
 */
public class ErrorHandler extends FeatureExecutorListener {
    private static final Logger log = LoggerFactory
            .getLogger(ErrorHandler.class);

    private SimpleDateFormat filenameDateFormat = new SimpleDateFormat(
            "YYYYDDMM-hhmmss-S");
    private SimpleDateFormat logDateFormat = new SimpleDateFormat(
            "YYYY-DD-MM hh:mm:ss.S");

    private String errorLogsDir, errorLogFilename, screenshotsDir;
    private String scenarioName;
    private ExtendedRemoteWebDriver driver;
    private PrintWriter writer;

    public ErrorHandler(ExtendedRemoteWebDriver driver, String errorLogsDir,
            String screenshotsDir, String featureFilename) {
        this.driver = driver;
        this.errorLogsDir = errorLogsDir;
        this.screenshotsDir = screenshotsDir;
        this.initErrorLogFilename(featureFilename);
    }

    @Override
    public void scenarioStarted(String scenarioName)
            throws FeatureExecutorListenerException {
        this.scenarioName = scenarioName;
    }

    @Override
    public void scenarioFailed(PatientExperienceScenario scenario,
            String message, Throwable cause)
            throws FeatureExecutorListenerException {
        this.logEvent("SCENARIO FAILED", cause);
    }

    @Override
    public void featureEnded() throws FeatureExecutorListenerException {
        this.closeWriter();
    }

    @Override
    public void featureFailed(PatientExperienceFeature feature, String message,
            Throwable cause) throws FeatureExecutorListenerException {
        this.logEvent("WARNING", cause);
        this.closeWriter();
    }

    @Override
    public void warning(PatientExperienceScenario scenario, String message,
            Throwable cause) throws FeatureExecutorListenerException {
        this.logEvent("WARNING", cause);
    }

    private void closeWriter() {
        if (this.writer != null) {
            this.writer.flush();
            this.writer.close();
            this.writer = null;
        }
    }

    private void initErrorLogFilename(String featureFilename) {
        File featureFile = new File(featureFilename);
        this.errorLogFilename = this.errorLogsDir + "error_"
                + featureFile.getName() + "_"
                + filenameDateFormat.format(new Date()) + ".log";
    }

    private void initializeWriter() throws FeatureExecutorListenerException {
        if (this.writer == null) {
            log.debug("Initializing CSV file writer");
            try {
                this.writer = new PrintWriter(new File(this.errorLogFilename));
            } catch (FileNotFoundException exception) {
                throw new FeatureExecutorListenerException(
                        "Could not open file " + this.errorLogFilename,
                        exception);
            }
        }
    }

    private void logEvent(String logPrefix, Throwable cause)
            throws FeatureExecutorListenerException {
        this.initializeWriter();
        this.writer.print(this.logDateFormat.format(new Date()));
        this.writer.write(" - ");
        this.writer.write(logPrefix);
        this.writer.write(": ");
        if (cause != null) {
            this.writer.write(cause.getMessage());
            this.writer.println();
            cause.printStackTrace(this.writer);
        }
        this.writer.println();
        this.writer.println("Screenshot: "
                + this.takeScreenshot().getAbsolutePath());
        this.writer.flush();
    }

    private String sanitizeForFilename(String string) {
        return string.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    private File takeScreenshot() throws FeatureExecutorListenerException {
        File outputScreenshot = new File(this.screenshotsDir + "screenshot_"
                + this.sanitizeForFilename(this.scenarioName) + "_"
                + this.filenameDateFormat.format(new Date()) + ".png");
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(outputScreenshot);
            stream.write(this.driver.getScreenshotAs(OutputType.BYTES));
        } catch (IOException exception) {
            throw new FeatureExecutorListenerException(
                    "Exception while taking screenshot", exception);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException exception) {
                    // Nothing sane to do
                }
            }
        }
        return outputScreenshot;
    }

    @Override
    protected void finalize() throws Throwable {
        this.closeWriter();
        super.finalize();
    }
}
