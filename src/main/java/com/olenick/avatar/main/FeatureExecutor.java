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

    public void execute(final String urlRoot, final String xmlFilename)
            throws FeatureExecutionException {
        PatientExperienceFeature feature;
        try {
            feature = this.getFeature(xmlFilename);
        } catch (IllegalArgumentException | ParseException exception) {
            throw new FeatureExecutionException("Error while parsing XML file",
                    exception);
        }
        this.notifyListenersOrFailFeature(FeatureExecutorEvent.FEATURE_STARTED);
        for (PatientExperienceScenario scenario : feature.getScenarios()) {
            try {
                this.runScenario(urlRoot, scenario);
            } catch (FeatureExecutorListenerException exception) {
                this.notifyListenersOrFailFeature(
                        FeatureExecutorEvent.SCENARIO_FAILED, scenario, "",
                        exception);
            }
            try {
                Thread.sleep(SLEEP_MILLIS_BETWEEN_SCENARIOS);
            } catch (InterruptedException ignored) {
            }
        }
        this.notifyListenersOrFailFeature(FeatureExecutorEvent.FEATURE_ENDED);
    }

    private void runScenario(final String urlRoot,
            final PatientExperienceScenario scenario)
            throws FeatureExecutorListenerException {
        String scenarioName = scenario.getName();

        this.notifyListenersOrFailScenario(
                FeatureExecutorEvent.SCENARIO_STARTED, scenarioName);

        this.notifyListenersOrFailScenario(FeatureExecutorEvent.LOGIN_PAGE_STARTED);
        LoginPage loginPage = new LoginPage(this.driver, urlRoot).open()
                .waitForElementsToLoad();
        this.notifyListenersOrFailScenario(FeatureExecutorEvent.LOGIN_PAGE_ENDED);

        LoggedInWelcomePage welcomePage = loginPage.login(scenario.getUser(),
                scenario.getPassword()).waitForElementsToLoad();

        this.notifyListenersOrFailScenario(FeatureExecutorEvent.PATIENT_EXPERIENCE_STARTED);
        PatientExperienceIFrame patientExperienceIFrame = welcomePage
                .navigateToPatientExperienceTab().waitForElementsToLoad();
        patientExperienceIFrame.openOverviewTab().waitForElementsToLoad();
        this.notifyListenersOrFailScenario(FeatureExecutorEvent.PATIENT_EXPERIENCE_ENDED);

        ReportFilter reportFilter = scenario.getReportFilter();
        patientExperienceIFrame.configureReportLevelFilter(reportFilter)
                .configureSurveySelectionFilter(reportFilter)
                .applyConfiguredFilters();

        for (ReportTabSpec tabSpec : reportFilter.getTabSpecs()) {
            ReportTab tab = tabSpec.getTab();
            this.notifyListenersOrFailScenario(
                    FeatureExecutorEvent.REPORT_TAB_STARTED, tab);
            ReportGraphsTabIFrame<?> iFrame = (ReportGraphsTabIFrame<?>) patientExperienceIFrame
                    .openReportTab(tab).waitForElementsToLoad();
            this.notifyListenersOrFailScenario(
                    FeatureExecutorEvent.REPORT_TAB_ENDED, tab);
            if (tab.isCapableOfExportingToPDF()) {

                this.notifyListenersOrFailScenario(
                        FeatureExecutorEvent.REPORT_TAB_EXPORT_TO_PDF_STARTED,
                        tab);
                iFrame.exportToPDF();
                this.notifyListenersOrFailScenario(
                        FeatureExecutorEvent.REPORT_TAB_EXPORT_TO_PDF_ENDED,
                        tab);

            }
        }

        this.notifyListenersOrFailScenario(FeatureExecutorEvent.SCENARIO_ENDED);
    }

    protected PatientExperienceFeature getFeature(final String xmlFilename)
            throws ParseException {
        URL xmlFileURL = this.getClass().getClassLoader()
                .getResource(xmlFilename);
        if (xmlFileURL == null) {
            throw new IllegalArgumentException("XML file could not be opened");
        }
        log.debug("Feature XML file: {}", xmlFileURL);
        return new XMLPatientExperienceFeatureParser().parse(xmlFileURL);
    }

    private void notifyListenersOrFailScenario(
            final FeatureExecutorEvent event, final Object... args)
            throws FeatureExecutorListenerException {
        for (FeatureExecutorListener listener : this.listeners) {
            log.trace("Notifying {} of {}", listener, event);
            listener.listen(event, args);
        }
    }

    private void notifyListenersOrFailFeature(final FeatureExecutorEvent event,
            final Object... args) throws FeatureExecutionException {
        try {
            this.notifyListenersOrFailScenario(event, args);
        } catch (FeatureExecutorListenerException exception) {
            StringBuilder argsSB = new StringBuilder();
            for (Object arg : args) {
                argsSB.append(", ").append(arg);
            }
            throw new FeatureExecutionException("Event: " + event
                    + argsSB.toString(), exception);
        }
    }
}
