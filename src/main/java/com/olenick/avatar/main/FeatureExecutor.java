package com.olenick.avatar.main;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.exceptions.FeatureExecutionException;
import com.olenick.avatar.exceptions.FeatureExecutorListenerException;
import com.olenick.avatar.exceptions.ParseException;
import com.olenick.avatar.model.ReportFilter;
import com.olenick.avatar.model.ReportTab;
import com.olenick.avatar.model.ReportTabSpec;
import com.olenick.avatar.parsers.xml.XMLPatientExperienceFeatureParser;
import com.olenick.avatar.reports.FeatureExecutorEvent;
import com.olenick.avatar.reports.FeatureExecutorListener;
import com.olenick.avatar.uses.PatientExperienceFeature;
import com.olenick.avatar.uses.PatientExperienceScenario;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;
import com.olenick.avatar.web.containers.LoggedInWelcomePage;
import com.olenick.avatar.web.containers.LoginPage;
import com.olenick.avatar.web.containers.PatientExperienceIFrame;
import com.olenick.avatar.web.containers.ReportGraphsTabIFrame;

/**
 * Feature Executor
 */
public class FeatureExecutor {
    private static final Logger log = LoggerFactory
            .getLogger(FeatureExecutor.class);

    private static final int SLEEP_MILLIS_BETWEEN_SCENARIOS = 1000;

    private final ExtendedRemoteWebDriver driver;
    private final List<FeatureExecutorListener> listeners;

    public FeatureExecutor(final ExtendedRemoteWebDriver driver)
            throws FeatureExecutionException {
        this.listeners = new LinkedList<>();
        this.driver = driver;
    }

    public void addListeners(final FeatureExecutorListener... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    public void execute(final String urlRoot, final URL xmlFileURL)
            throws FeatureExecutionException {
        PatientExperienceFeature feature;
        try {
            feature = this.getFeature(xmlFileURL);
        } catch (IllegalArgumentException | ParseException exception) {
            throw new FeatureExecutionException("Error while parsing XML file",
                    exception);
        }
        try {
            this.notifyListeners(FeatureExecutorEvent.FEATURE_STARTED);
            for (PatientExperienceScenario scenario : feature.getScenarios()) {
                try {
                    this.runScenario(urlRoot, scenario);
                } catch (FeatureExecutorListenerException | RuntimeException exception) {
                    this.notifyListeners(FeatureExecutorEvent.SCENARIO_FAILED,
                            scenario, "", exception);
                }
                try {
                    Thread.sleep(SLEEP_MILLIS_BETWEEN_SCENARIOS);
                } catch (InterruptedException ignored) {
                }
            }
            this.notifyListeners(FeatureExecutorEvent.FEATURE_ENDED);
        } catch (FeatureExecutorListenerException exception) {
            this.notifyListenersQuietly(FeatureExecutorEvent.FEATURE_FAILED,
                    feature, "", exception);
        }
    }

    private void runScenario(final String urlRoot,
            final PatientExperienceScenario scenario)
            throws FeatureExecutorListenerException {
        this.notifyListeners(FeatureExecutorEvent.SCENARIO_STARTED,
                scenario.getName());

        LoggedInWelcomePage welcomePage = this.login(urlRoot, scenario);
        PatientExperienceIFrame patientExperienceIFrame = this
                .loadPatientExperience(welcomePage);
        ReportFilter reportFilter = this.configureReportFilter(scenario,
                patientExperienceIFrame);
        this.loadReports(patientExperienceIFrame, reportFilter);

        this.notifyListeners(FeatureExecutorEvent.SCENARIO_ENDED);
    }

    private ReportFilter configureReportFilter(
            PatientExperienceScenario scenario,
            PatientExperienceIFrame patientExperienceIFrame) {
        ReportFilter reportFilter = scenario.getReportFilter();
        patientExperienceIFrame.configureReportLevelFilter(reportFilter)
                .configureSurveySelectionFilter(reportFilter)
                .applyConfiguredFilters();
        return reportFilter;
    }

    private void exportToPDF(ReportTab tab, ReportGraphsTabIFrame<?> iFrame)
            throws FeatureExecutorListenerException {
        this.notifyListeners(
                FeatureExecutorEvent.REPORT_TAB_EXPORT_TO_PDF_STARTED, tab);
        iFrame.exportToPDF();
        this.notifyListeners(
                FeatureExecutorEvent.REPORT_TAB_EXPORT_TO_PDF_ENDED, tab);
    }

    private PatientExperienceFeature getFeature(final URL xmlFileURL)
            throws ParseException {
        log.debug("Feature XML file: {}", xmlFileURL);
        return new XMLPatientExperienceFeatureParser().parse(xmlFileURL);
    }

    private PatientExperienceIFrame loadPatientExperience(
            LoggedInWelcomePage welcomePage)
            throws FeatureExecutorListenerException {
        this.notifyListeners(FeatureExecutorEvent.PATIENT_EXPERIENCE_STARTED);
        PatientExperienceIFrame patientExperienceIFrame = welcomePage
                .navigateToPatientExperienceTab().waitForElementsToLoad();
        patientExperienceIFrame.openOverviewTab().waitForElementsToLoad();
        this.notifyListeners(FeatureExecutorEvent.PATIENT_EXPERIENCE_ENDED);
        return patientExperienceIFrame;
    }

    private void loadReports(PatientExperienceIFrame patientExperienceIFrame,
            ReportFilter reportFilter) throws FeatureExecutorListenerException {
        for (ReportTabSpec tabSpec : reportFilter.getTabSpecs()) {
            ReportTab tab = tabSpec.getTab();
            ReportGraphsTabIFrame<?> iFrame = this.loadReportTab(
                    patientExperienceIFrame, tab);
            if (tab.isCapableOfExportingToPDF()) {
                this.exportToPDF(tab, iFrame);
            }
        }
    }

    private ReportGraphsTabIFrame<?> loadReportTab(
            PatientExperienceIFrame patientExperienceIFrame, ReportTab tab)
            throws FeatureExecutorListenerException {
        this.notifyListeners(FeatureExecutorEvent.REPORT_TAB_STARTED, tab);
        ReportGraphsTabIFrame<?> iFrame = (ReportGraphsTabIFrame<?>) patientExperienceIFrame
                .openReportTab(tab).waitForElementsToLoad();
        this.notifyListeners(FeatureExecutorEvent.REPORT_TAB_ENDED, tab);
        return iFrame;
    }

    private LoggedInWelcomePage login(String urlRoot,
            PatientExperienceScenario scenario)
            throws FeatureExecutorListenerException {
        this.notifyListeners(FeatureExecutorEvent.LOGIN_PAGE_STARTED);
        LoginPage loginPage = new LoginPage(this.driver, urlRoot).open()
                .waitForElementsToLoad();
        this.notifyListeners(FeatureExecutorEvent.LOGIN_PAGE_ENDED);

        return loginPage.login(scenario.getUser(), scenario.getPassword())
                .waitForElementsToLoad();
    }

    private void notifyListeners(final FeatureExecutorEvent event,
            final Object... args) throws FeatureExecutorListenerException {
        for (FeatureExecutorListener listener : this.listeners) {
            log.trace("Notifying {} of {}", listener, event);
            listener.listen(event, args);
        }
    }

    private void notifyListenersQuietly(final FeatureExecutorEvent event,
            final Object... args) {
        try {
            this.notifyListeners(event, args);
        } catch (FeatureExecutorListenerException ignored) {
        }
    }
}
