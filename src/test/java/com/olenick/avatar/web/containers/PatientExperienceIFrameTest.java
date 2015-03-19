package com.olenick.avatar.web.containers;

import java.time.Month;

import org.junit.Test;

import com.olenick.avatar.SeleniumTest;
import com.olenick.avatar.model.Calculation;
import com.olenick.avatar.model.DateKey;
import com.olenick.avatar.model.DateRangeGroupBy;
import com.olenick.avatar.model.MonthSpec;
import com.olenick.avatar.model.PatientDemographics;
import com.olenick.avatar.model.ProviderFilter;
import com.olenick.avatar.model.ProviderGrouping;
import com.olenick.avatar.model.ReportFilter;

public class PatientExperienceIFrameTest extends SeleniumTest {
    private static final String USERNAME = "performance.test3@avatarsolutions.com";
    private static final String PASSWORD = "password";

    @Test
    public void test_select() {
        ReportFilter reportFilter = new ReportFilter();

        reportFilter.setSystem("26");
        reportFilter.setOrganizations("252");
        reportFilter.setDepartments("5720");
        reportFilter.setLocations("_FOC_NULL");

        reportFilter.setSurveyType("Avatar");
        reportFilter.setPatientTypes("_FOC_NULL");
        reportFilter.setFactorComposites("Billing", "Expectations");
        reportFilter.setItemQuestions("_FOC_NULL");

        reportFilter.setDateKey(DateKey.SURVEY_RETURN);
        reportFilter.setFrom(new MonthSpec(Month.MARCH, "2013"));
        reportFilter.setTo(new MonthSpec(Month.MAY, "2013"));
        reportFilter.setGroupBy(DateRangeGroupBy.QUARTERLY);

        reportFilter.setCalculation(Calculation.TOP_BOX);

        PatientDemographics demographics = new PatientDemographics();
        demographics.setAdmissionSources("_FOC_NULL");
        demographics.setAges("_FOC_NULL");
        demographics.setDischargeStatuses("_FOC_NULL");
        demographics.setGenders("_FOC_NULL");
        demographics.setLanguages("_FOC_NULL");
        demographics.setLengthsOfStay("_FOC_NULL");
        demographics.setRaces("_FOC_NULL");
        demographics.setServiceLines("_FOC_NULL");
        reportFilter.setDemographics(demographics);

        ProviderFilter providerFilter = new ProviderFilter();
        providerFilter.setGrouping(ProviderGrouping.PRIMARY_SPECIALTY);
        providerFilter.setGroupingElements("_FOC_NULL");
        providerFilter.setProviders("_FOC_NULL");
        reportFilter.setProviderFilter(providerFilter);

        new LoginPage(this.driver, URL_ROOT_DEV).open()
                .login(USERNAME, PASSWORD).waitForElementsToLoad()
                .navigateToPatientExperienceTab().waitForElementsToLoad()
                .configureFilters(reportFilter);
    }
}
