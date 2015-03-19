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
import com.olenick.avatar.util.FilenameUtils;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Error Handler listener of FeatureExecutor.
 */
public class ErrorHandler extends FeatureExecutorListener {
    private static final Logger log = LoggerFactory
            .getLogger(ErrorHandler.class);

    private static final String FILE_PREFIX_SCREENSHOT = "screenshot_";
    private static final String FILE_PREFIX_ERROR_LOG = "error_";

    private SimpleDateFormat filenameDateFormat = new SimpleDateFormat(
            "yyyyddMM-HHmmss");
    private SimpleDateFormat logDateFormat = new SimpleDateFormat(
            "yyyy-dd-MM HH:mm:ss.SSS");

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
        this.logEvent("SCENARIO FAILED", message, cause);
    }

    @Override
    public void featureEnded() throws FeatureExecutorListenerException {
        this.closeWriter();
    }

    @Override
    public void featureFailed(PatientExperienceFeature feature, String message,
            Throwable cause) throws FeatureExecutorListenerException {
        this.logEvent("FEATURE FAILED", message, cause);
        this.closeWriter();
    }

    @Override
    public void warning(PatientExperienceScenario scenario, String message,
            Throwable cause) throws FeatureExecutorListenerException {
        this.logEvent("WARNING", message, cause);
    }

    private void closeWriter() {
        if (this.writer != null) {
            this.writer.flush();
            this.writer.close();
            this.writer = null;
        }
    }

    private String getScreenshotFilename() {
        return FilenameUtils.slashed(this.screenshotsDir)
                + FILE_PREFIX_SCREENSHOT
                + this.sanitizeForFilename(this.scenarioName) + "_"
                + this.filenameDateFormat.format(new Date()) + ".png";
    }

    private void initErrorLogFilename(String featureFilename) {
        this.errorLogFilename = FilenameUtils.slashed(this.errorLogsDir)
                + FILE_PREFIX_ERROR_LOG
                + FilenameUtils.basename(featureFilename).replace(".xml", "")
                + "_" + filenameDateFormat.format(new Date()) + ".log";
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

    private void logEvent(String logPrefix, String message, Throwable cause)
            throws FeatureExecutorListenerException {
        this.initializeWriter();
        this.writer.print(this.logDateFormat.format(new Date()));
        this.writer.write(" - ");
        this.writer.write(logPrefix);
        if (message != null && !message.isEmpty()) {
            this.writer.write(": ");
            this.writer.write(message);
        }
        this.writer.println();
        if (cause != null) {
            this.writer.write(cause.getClass().getCanonicalName());
            this.writer.write(": ");
            this.writer.write(cause.getMessage());
            this.writer.println();
            cause.printStackTrace(this.writer);
        } else {
            this.writer.println();
        }
        this.writer.println("Screenshot: "
                + this.takeScreenshot().getAbsolutePath());
        this.writer.flush();
    }

    private String sanitizeForFilename(String string) {
        return string.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    private File takeScreenshot() throws FeatureExecutorListenerException {
        File outputScreenshot = new File(this.getScreenshotFilename());
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
