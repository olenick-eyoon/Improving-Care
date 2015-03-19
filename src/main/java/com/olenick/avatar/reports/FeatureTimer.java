package com.olenick.avatar.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.exceptions.FeatureExecutorListenerException;
import com.olenick.avatar.model.ReportTab;
import com.olenick.avatar.uses.PatientExperienceFeature;
import com.olenick.avatar.uses.PatientExperienceScenario;
import com.olenick.avatar.util.FilenameUtils;

/**
 * FeatureExecutor listener: Timer.
 */
public class FeatureTimer extends FeatureExecutorListener {
    private static final Logger log = LoggerFactory
            .getLogger(FeatureTimer.class);

    private static final ReportTab[] TAB_ORDER = new ReportTab[] {
            ReportTab.OVERVIEW, ReportTab.COMPOSITE, ReportTab.SIDE_BY_SIDE,
            ReportTab.DEMOGRAPHICS };

    private DateFormat fileSuffixDateFormat = new SimpleDateFormat(
            "yyyyMMdd-HHmmss");
    private DateFormat csvDateFormat = new SimpleDateFormat(
            "MM-dd-yyyy  HH:mm:ss");

    private PrintWriter writer;

    private Date startTime, endTime;
    private Date loginPageStartTime, loginPageEndTime;
    private Date patientExperienceStartTime, patientExperienceEndTime;
    private EnumMap<ReportTab, Date> reportTabStartTimes, reportTabEndTimes;
    private EnumMap<ReportTab, Date> reportTabExportToPDFStartTimes,
            reportTabExportToPDFEndTimes;
    private String filename;
    private String hostName;
    private String scenarioName;

    public FeatureTimer(String csvDirName, String xmlSpecFilename,
            String hostName) throws FeatureExecutorListenerException {
        this.reportTabStartTimes = new EnumMap<>(ReportTab.class);
        this.reportTabEndTimes = new EnumMap<>(ReportTab.class);
        this.reportTabExportToPDFStartTimes = new EnumMap<>(ReportTab.class);
        this.reportTabExportToPDFEndTimes = new EnumMap<>(ReportTab.class);
        this.filename = this.initCSVFilename(csvDirName, xmlSpecFilename,
                hostName);
        this.hostName = hostName;
    }

    @Override
    public void featureStarted() throws FeatureExecutorListenerException {
        log.debug("Feature started");
        this.initializeWriter();
        this.writeHeader();
    }

    @Override
    public void featureEnded() throws FeatureExecutorListenerException {
        log.debug("Feature ended");
        this.writer.flush();
        this.clearScenario();
        this.writer.close();
        this.writer = null;
    }

    @Override
    public void featureFailed(PatientExperienceFeature feature, String message,
            Throwable cause) throws FeatureExecutorListenerException {
        if (message != null && !message.isEmpty()) {
            log.debug("Feature failed: " + message);
        } else {
            log.debug("Feature failed");
        }
        this.writer.flush();
        this.clearScenario();
        this.writer.close();
        this.writer = null;
    }

    @Override
    public void scenarioStarted(String scenarioName)
            throws FeatureExecutorListenerException {
        log.debug("[{}] Scenario started", this.scenarioName);
        this.setScenarioName(scenarioName);
        this.checkTimeMemberReady(this.startTime);
        this.startTime = new Date();
    }

    @Override
    public void scenarioEnded() throws FeatureExecutorListenerException {
        this.checkTimeMemberReady(this.endTime);
        this.endTime = new Date();
        log.debug("[{}] Scenario ended. Took: {}ms.", this.scenarioName,
                this.getScenarioTimeInMillis());
        this.writeLine();
        this.clearScenario();
    }

    @Override
    public void scenarioFailed(PatientExperienceScenario scenario,
            String message, Throwable cause)
            throws FeatureExecutorListenerException {
        this.endTime = new Date();
        if (message != null && !message.isEmpty()) {
            log.debug("[{}] Scenario failed: {}. Ran for {}ms.", message,
                    this.scenarioName, this.getScenarioTimeInMillis());
        } else {
            log.error("[{}] Scenario failed. Ran for {}ms.", this.scenarioName,
                    this.getScenarioTimeInMillis());
        }
        this.writeLine(); // TODO: Check if this is OK to be done.
        this.clearScenario();
    }

    public long getScenarioTimeInMillis() {
        return this.getSpanInMillis(this.startTime, this.endTime);
    }

    @Override
    public void loginPageStarted() throws FeatureExecutorListenerException {
        log.debug("[{}] Login page started", this.scenarioName);
        this.checkTimeMemberReady(this.loginPageStartTime);
        this.loginPageStartTime = new Date();
    }

    @Override
    public void loginPageEnded() throws FeatureExecutorListenerException {
        this.checkTimeMemberReady(this.loginPageEndTime);
        this.loginPageEndTime = new Date();
        log.debug("[{}] Login page ended", this.scenarioName);
    }

    public long getLoginTimeInMillis() {
        return this.getSpanInMillis(this.loginPageStartTime,
                this.loginPageEndTime);
    }

    @Override
    public void patientExperienceStarted()
            throws FeatureExecutorListenerException {
        log.debug("[{}] Patient Experience page started", this.scenarioName);
        this.checkTimeMemberReady(this.patientExperienceStartTime);
        this.patientExperienceStartTime = new Date();
    }

    @Override
    public void patientExperienceEnded()
            throws FeatureExecutorListenerException {
        this.checkTimeMemberReady(this.patientExperienceEndTime);
        this.patientExperienceEndTime = new Date();
        log.debug("[{}] Patient Experience page ended", this.scenarioName);
    }

    public long getPatientExperienceTimeInMillis() {
        return this.getSpanInMillis(this.patientExperienceStartTime,
                this.patientExperienceEndTime);
    }

    @Override
    public void reportTabStarted(ReportTab tab)
            throws FeatureExecutorListenerException {
        log.debug("[{}] Report tab {} started", this.scenarioName, tab);
        this.checkTimeMemberReady(this.reportTabStartTimes.get(tab));
        this.reportTabStartTimes.put(tab, new Date());
    }

    @Override
    public void reportTabEnded(ReportTab tab)
            throws FeatureExecutorListenerException {
        this.checkTimeMemberReady(this.reportTabEndTimes.get(tab));
        this.reportTabEndTimes.put(tab, new Date());
        log.debug("[{}] Report tab {} ended", this.scenarioName, tab);
    }

    public long getReportTabTimeInMillis(ReportTab tab) {
        return this.getSpanInMillis(this.reportTabStartTimes.get(tab),
                this.reportTabEndTimes.get(tab));
    }

    @Override
    public void reportTabExportedToPDFStarted(ReportTab tab)
            throws FeatureExecutorListenerException {
        log.debug("[{}] Tab {} Export all to PDF started", this.scenarioName,
                tab);
        this.checkTimeMemberReady(this.reportTabExportToPDFStartTimes.get(tab));
        this.reportTabExportToPDFStartTimes.put(tab, new Date());
    }

    @Override
    public void reportTabExportedToPDFEnded(ReportTab tab)
            throws FeatureExecutorListenerException {
        this.checkTimeMemberReady(this.reportTabExportToPDFEndTimes.get(tab));
        this.reportTabExportToPDFEndTimes.put(tab, new Date());
        log.debug("[{}] Tab {} Export all to PDF ended", this.scenarioName, tab);
    }

    public long getReportTabExportToPDFTimeInMillis(ReportTab tab) {
        return this.getSpanInMillis(
                this.reportTabExportToPDFStartTimes.get(tab),
                this.reportTabExportToPDFEndTimes.get(tab));
    }

    private void checkTimeMemberReady(Date timeMember)
            throws FeatureExecutorListenerException {
        if (timeMember != null) {
            throw new FeatureExecutorListenerException("Time was already set");
        }
    }

    private void clearScenario() {
        this.scenarioName = null;
        this.startTime = null;
        this.endTime = null;
        this.loginPageStartTime = null;
        this.loginPageEndTime = null;
        this.patientExperienceStartTime = null;
        this.patientExperienceEndTime = null;
        this.reportTabExportToPDFStartTimes.clear();
        this.reportTabExportToPDFEndTimes.clear();
        this.reportTabStartTimes.clear();
        this.reportTabEndTimes.clear();
    }

    private long getSpanInMillis(Date start, Date end) {
        if (start != null && end != null) {
            return end.getTime() - start.getTime();
        } else {
            return 0L;
        }
    }

    private String initCSVFilename(String csvDirName, String xmlSpecFilename,
            String hostName) {
        return FilenameUtils.slashed(csvDirName)
                + FilenameUtils.basename(xmlSpecFilename).replace(".xml", "")
                + "_" + hostName + "_"
                + this.fileSuffixDateFormat.format(new Date()) + ".csv";
    }

    private void initializeWriter() throws FeatureExecutorListenerException {
        log.debug("Initializing CSV file writer");
        try {
            this.writer = new PrintWriter(new File(this.filename));
        } catch (FileNotFoundException exception) {
            throw new FeatureExecutorListenerException("Could not open file "
                    + this.filename, exception);
        }
    }

    private void setScenarioName(String scenarioName)
            throws FeatureExecutorListenerException {
        this.scenarioName = scenarioName;
        log.debug("Scenario name: {}", this.scenarioName);
    }

    private void writeHeader() {
        log.debug("Writing header");
        this.writer.println("_Computer Name, " + "_Date and Time, "
                + "_Login Page, " + "_iCare2 Page, " + "_Scenario Name, "
                + "_Overview, " + "_Composite, " + "_Side By Side, "
                + "_Demographics, " + "_Export Composite Tab, "
                + "_Export Side By Side, " + "_Export Demographics Tab");
        this.writer.flush();
    }

    private void writeLine() {
        log.debug("Writing line");
        this.writer.printf("%s, %s, %d, %d, %s", this.hostName,
                this.csvDateFormat.format(this.startTime),
                this.getLoginTimeInMillis(),
                this.getPatientExperienceTimeInMillis(), this.scenarioName);
        for (ReportTab tab : TAB_ORDER) {
            this.writer.print(", " + this.getReportTabTimeInMillis(tab));
        }
        for (ReportTab tab : TAB_ORDER) {
            this.writer.print(", "
                    + this.getReportTabExportToPDFTimeInMillis(tab));
        }
        this.writer.println();
        this.writer.flush();
    }

    @Override
    protected void finalize() throws Throwable {
        if (this.writer != null) {
            this.writer.flush();
            this.writer.close();
        }
        super.finalize();
    }
}
