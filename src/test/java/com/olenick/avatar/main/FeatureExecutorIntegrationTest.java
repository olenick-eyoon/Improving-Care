package com.olenick.avatar.main;

import org.junit.Test;

import com.olenick.avatar.SeleniumTest;
import com.olenick.avatar.exceptions.FeatureExecutionException;
import com.olenick.avatar.exceptions.FeatureExecutorListenerException;
import com.olenick.avatar.reports.FeatureTimer;

/**
 * FeatureExecutor integration test cases.
 */
public class FeatureExecutorIntegrationTest extends SeleniumTest {
    private static final String HOSTNAME = "my-hostname";
    private static final String REPORT_FILENAME = "full_integration_test.csv";
    private static final String SCENARIO_SPECS_XML_FILENAME = "scenario-specs/full_integration_test.xml";

    @Test
    public void test_everything_works_as_expected()
            throws FeatureExecutionException, FeatureExecutorListenerException {
        FeatureTimer featureTimer = new FeatureTimer(REPORT_FILENAME, HOSTNAME);
        FeatureExecutor featureExecutor = new FeatureExecutor(this.driver);
        featureExecutor.addListeners(featureTimer);
        featureExecutor.execute(URL_ROOT_DEV, SCENARIO_SPECS_XML_FILENAME);
    }
}
