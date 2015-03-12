package com.olenick.avatar.reports;

import com.olenick.avatar.exceptions.FeatureExecutorListenerException;
import com.olenick.avatar.model.ReportTab;
import com.olenick.avatar.uses.PatientExperienceFeature;
import com.olenick.avatar.uses.PatientExperienceScenario;

/**
 * FeatureExecutor listener.
 */
public abstract class FeatureExecutorListener {
    public void featureEnded() throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void featureFailed(PatientExperienceFeature feature, String message,
            Throwable cause) throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void featureStarted() throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void loginPageEnded() throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void loginPageStarted() throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void patientExperienceEnded()
            throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void patientExperienceStarted()
            throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void reportTabEnded(ReportTab tab)
            throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void reportTabStarted(ReportTab tab)
            throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void reportTabExportedToPDFEnded(ReportTab tab)
            throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void reportTabExportedToPDFStarted(ReportTab tab)
            throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void scenarioEnded() throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void scenarioFailed(PatientExperienceScenario scenario,
            String message, Throwable cause)
            throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void scenarioStarted(String scenarioName)
            throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public void warning(PatientExperienceScenario scenario, String message,
            Throwable cause) throws FeatureExecutorListenerException {
        // DO NOTHING
    }

    public final void listen(FeatureExecutorEvent event, Object... args)
            throws FeatureExecutorListenerException {
        // TODO: This is plain rubbish. Figure out a better way to do this.
        switch (event) {
            case FEATURE_STARTED:
                this.featureStarted();
                break;
            case FEATURE_ENDED:
                this.featureEnded();
                break;
            case FEATURE_FAILED:
                this.featureFailed((PatientExperienceFeature) args[0],
                        (String) args[1], (Throwable) args[2]);
                break;
            case SCENARIO_STARTED:
                this.scenarioStarted((String) args[0]);
                break;
            case SCENARIO_ENDED:
                this.scenarioEnded();
                break;
            case SCENARIO_FAILED:
                this.scenarioFailed((PatientExperienceScenario) args[0],
                        (String) args[1], (Throwable) args[2]);
                break;
            case LOGIN_PAGE_STARTED:
                this.loginPageStarted();
                break;
            case LOGIN_PAGE_ENDED:
                this.loginPageEnded();
                break;
            case PATIENT_EXPERIENCE_STARTED:
                this.patientExperienceStarted();
                break;
            case PATIENT_EXPERIENCE_ENDED:
                this.patientExperienceEnded();
                break;
            case REPORT_TAB_STARTED:
                this.reportTabStarted((ReportTab) args[0]);
                break;
            case REPORT_TAB_ENDED:
                this.reportTabEnded((ReportTab) args[0]);
                break;
            case REPORT_TAB_EXPORT_TO_PDF_STARTED:
                this.reportTabExportedToPDFStarted((ReportTab) args[0]);
                break;
            case REPORT_TAB_EXPORT_TO_PDF_ENDED:
                this.reportTabExportedToPDFEnded((ReportTab) args[0]);
                break;
            case WARNING:
                this.warning((PatientExperienceScenario) args[0],
                        (String) args[1], (Throwable) args[2]);
                break;
        }
    }
}
