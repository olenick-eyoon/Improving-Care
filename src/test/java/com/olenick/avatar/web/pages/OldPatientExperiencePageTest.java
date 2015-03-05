package com.olenick.avatar.web.pages;

import static org.junit.Assert.assertNotNull;

import com.olenick.avatar.web.pages.OldICare2Page;
import com.olenick.avatar.web.pages.OldLandingPage;
import com.olenick.avatar.web.pages.OldLoginPage;
import com.olenick.avatar.web.pages.OldPatientExperiencePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.avatar.exceptions.HomeLinkInvalid;
import com.olenick.avatar.exceptions.ICare2PageNotDisplayed;
import com.olenick.avatar.exceptions.PatientExperienceLinkInvalid;
import com.olenick.avatar.exceptions.SurveyControlCenterLinkInvalid;

@Ignore
public class OldPatientExperiencePageTest {
    OldLoginPage loginPage;
    OldLandingPage oldLandingPage;
    OldICare2Page oldICare2Page;
    OldPatientExperiencePage oldPatientExperiencePagePage;

    WebDriver driver = new FirefoxDriver();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Ignore
    @Test
    public void openPage() throws Exception {
        loginPage = new OldLoginPage(driver);
        oldLandingPage = loginPage.open(true).login(
                "rferrari@avatarsolutions.com", "password");
        oldICare2Page = oldLandingPage.drillDownAdvancedReports()
                .accessEnhancedReports().switchToNewWindow()
                .switchToMainIFrame().detectMenuBarItems();
        oldPatientExperiencePagePage = oldICare2Page.accessPatientExperienceTab();
        assertNotNull(oldPatientExperiencePagePage);
    }

    @Ignore
    @Test
    public void detectFilters() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        /*
         * landingPage =
         * loginPage.open(true).login("rferrari@avatarsolutions.com",
         * "password"); iCare2Page =
         * landingPage.drillDownAdvancedReports().accessEnhancedReports
         * ().switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
         * patientExperiencePage = iCare2Page.accessPatientExperienceTab();
         */
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab();
        oldPatientExperiencePagePage.detectFilters();

        assertNotNull(oldPatientExperiencePagePage.getSystem());
        assertNotNull(oldPatientExperiencePagePage.getOrganization());
        assertNotNull(oldPatientExperiencePagePage.getDepartment());
        assertNotNull(oldPatientExperiencePagePage.getLocation());
        assertNotNull(oldPatientExperiencePagePage.getSurveyType());
        assertNotNull(oldPatientExperiencePagePage.getPatientType());
        assertNotNull(oldPatientExperiencePagePage.getComposite());
        assertNotNull(oldPatientExperiencePagePage.getItem());

    }

    @Ignore
    @Test
    public void convertSystemElementToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("system");
        assertNotNull(oldPatientExperiencePagePage.getSystemSelect());
    }

    @Ignore
    @Test
    public void convertOrganizationElementToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("organization");
        assertNotNull(oldPatientExperiencePagePage.getOrganizationSelect());
    }

    @Ignore
    @Test
    public void convertDepartmentElementToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("department");
        assertNotNull(oldPatientExperiencePagePage.getDepartmentSelect());
    }

    @Ignore
    @Test
    public void convertLocationElementToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("location");
        assertNotNull(oldPatientExperiencePagePage.getLocationSelect());
    }

    @Ignore
    @Test
    public void convertSurveyTypeElementToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("surveyType");
        assertNotNull(oldPatientExperiencePagePage.getSurveyTypeSelect());
    }

    @Ignore
    @Test
    public void convertPatientTypeElementToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("patientType");
        assertNotNull(oldPatientExperiencePagePage.getPatientTypeSelect());
    }

    @Ignore
    @Test
    public void convertCompositeElementToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("composite");
        assertNotNull(oldPatientExperiencePagePage.getCompositeSelect());
    }

    @Ignore
    @Test
    public void convertItemElementToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("item");
        assertNotNull(oldPatientExperiencePagePage.getItemSelect());
    }

    /*
     * DATE RANGE
     */
    @Ignore
    @Test
    public void detectDateRangeItems() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        assertNotNull(oldPatientExperiencePagePage.getFromMonth());
        assertNotNull(oldPatientExperiencePagePage.getFromYear());
        assertNotNull(oldPatientExperiencePagePage.getToMonth());
        assertNotNull(oldPatientExperiencePagePage.getToYear());
        assertNotNull(oldPatientExperiencePagePage.getGroupBy());

    }

    @Ignore
    @Test
    public void convertDateRangeItemsToSelect() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.convertToSelect("fromMonth");
        oldPatientExperiencePagePage.convertToSelect("fromYear");
        oldPatientExperiencePagePage.convertToSelect("toMonth");
        oldPatientExperiencePagePage.convertToSelect("toYear");
        oldPatientExperiencePagePage.convertToSelect("groupBy");

        assertNotNull(oldPatientExperiencePagePage.getFromMonthSelect());
        assertNotNull(oldPatientExperiencePagePage.getFromYearSelect());
        assertNotNull(oldPatientExperiencePagePage.getToMonthSelect());
        assertNotNull(oldPatientExperiencePagePage.getToYearSelect());
        assertNotNull(oldPatientExperiencePagePage.getGroupBySelect());
    }

    /*
     * CALCULATION
     */

    @Ignore
    @Test
    public void detectCalculationRadios() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        assertNotNull(oldPatientExperiencePagePage.getMean());
        assertNotNull(oldPatientExperiencePagePage.getTopBox());
    }

    /*
     * BUTTONS
     */

    @Ignore
    @Test
    public void detectButtons() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        assertNotNull(oldPatientExperiencePagePage.getApplyButton());
    }

    /*
     * ADVANCED FILTERS
     */
    @Ignore
    @Test
    public void detectDemographicFiltersLink() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        assertNotNull(oldPatientExperiencePagePage.getDemographicLink());
    }

    /*
     * TABS!
     */

    @Ignore
    @Test
    public void detectOverviewTab() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.detectOverviewTab();
        assertNotNull(oldPatientExperiencePagePage.getOverviewTab());
    }

    @Ignore
    @Test
    public void detectCompositeTab() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.detectCompositeTab();
        assertNotNull(oldPatientExperiencePagePage.getCompositeTab());
    }

    @Ignore
    @Test
    public void detectSbsTab() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.detectSbsTab();
        assertNotNull(oldPatientExperiencePagePage.getSbsTab());
    }

    @Ignore
    @Test
    public void detectDemographicTab() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters();
        oldPatientExperiencePagePage.detectDemographicTab();
        assertNotNull(oldPatientExperiencePagePage.getDemographicTab());
    }

    @Ignore
    @Test
    public void accessOverviewTab() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters()
                .detectOverviewTab();
        oldPatientExperiencePagePage.accessOverviewTab();

    }

    @Ignore
    @Test
    public void accessCompositeTab() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters()
                .detectCompositeTab();
        oldPatientExperiencePagePage.accessCompositeTab();

    }

    @Ignore
    @Test
    public void accessSbsTab() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters().detectSbsTab();
        oldPatientExperiencePagePage.accessSbsTab();

    }

    @Ignore
    @Test
    public void accessDemographicsTab() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException, ICare2PageNotDisplayed {
        loginPage = new OldLoginPage(driver);
        oldPatientExperiencePagePage = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems()
                .accessPatientExperienceTab().detectFilters()
                .detectDemographicTab();
        oldPatientExperiencePagePage.accessDemographicsTab();

    }
}
