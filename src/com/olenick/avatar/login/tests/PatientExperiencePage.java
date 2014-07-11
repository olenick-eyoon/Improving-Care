package com.olenick.avatar.login.tests;

import static org.junit.Assert.*;

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
import com.olenick.avatar.pages.ICare2;
import com.olenick.avatar.pages.Landing;
import com.olenick.avatar.pages.Login;
import com.olenick.avatar.pages.PatientExperience;

public class PatientExperiencePage {

	Login loginPage;
	Landing landingPage;
	ICare2 iCare2Page; 
	PatientExperience patientExperiencePage;
	
	
	WebDriver driver = new FirefoxDriver();

	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	@Ignore
	@Test
	public void openPage() throws Exception{
		loginPage = new Login(driver);
		landingPage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password");
		iCare2Page = landingPage.drillDownAdvancedReports().accessEnhancedReports().switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
		patientExperiencePage = iCare2Page.accessPatientExperienceTab();
		assertNotNull(patientExperiencePage);
	}
	
	@Ignore
	@Test
	public void detectFilters() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		/*landingPage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password");
		iCare2Page = landingPage.drillDownAdvancedReports().accessEnhancedReports().switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
		patientExperiencePage = iCare2Page.accessPatientExperienceTab();
		*/
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab();
		patientExperiencePage.detectFilters();
		
		assertNotNull(patientExperiencePage.getSystem());
		assertNotNull(patientExperiencePage.getOrganization());
		assertNotNull(patientExperiencePage.getDepartment());
		assertNotNull(patientExperiencePage.getLocation());
		assertNotNull(patientExperiencePage.getSurveyType());
		assertNotNull(patientExperiencePage.getPatientType());
		assertNotNull(patientExperiencePage.getComposite());
		assertNotNull(patientExperiencePage.getItem());
		
	}
	
	@Ignore
	@Test
	public void convertSystemElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("system");
		assertNotNull(patientExperiencePage.getSystemSelect());
	}
	
	@Ignore
	@Test
	public void convertOrganizationElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("organization");
		assertNotNull(patientExperiencePage.getOrganizationSelect());
	}
	
	@Ignore
	@Test
	public void convertDepartmentElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("department");
		assertNotNull(patientExperiencePage.getDepartmentSelect());
	}
	
	@Ignore
	@Test
	public void convertLocationElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("location");
		assertNotNull(patientExperiencePage.getLocationSelect());
	}
	
	@Ignore
	@Test
	public void convertSurveyTypeElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("surveyType");
		assertNotNull(patientExperiencePage.getSurveyTypeSelect());
	}

	@Ignore
	@Test
	public void convertPatientTypeElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("patientType");
		assertNotNull(patientExperiencePage.getPatientTypeSelect());
	}

	@Ignore
	@Test
	public void convertCompositeElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("composite");
		assertNotNull(patientExperiencePage.getCompositeSelect());
	}

	@Ignore
	@Test
	public void convertItemElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("item");
		assertNotNull(patientExperiencePage.getItemSelect());
	}
	

	/*
	 * DATE RANGE
	 */
	@Ignore
	@Test
	public void detectDateRangeItems() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		assertNotNull(patientExperiencePage.getFromMonth());
		assertNotNull(patientExperiencePage.getFromYear());
		assertNotNull(patientExperiencePage.getToMonth());
		assertNotNull(patientExperiencePage.getToYear());
		assertNotNull(patientExperiencePage.getGroupBy());
		
	}
	
	@Ignore
	@Test
	public void convertDateRangeItemsToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("fromMonth");
		patientExperiencePage.convertToSelect("fromYear");
		patientExperiencePage.convertToSelect("toMonth");
		patientExperiencePage.convertToSelect("toYear");
		patientExperiencePage.convertToSelect("groupBy");
		
		assertNotNull(patientExperiencePage.getFromMonthSelect());
		assertNotNull(patientExperiencePage.getFromYearSelect());
		assertNotNull(patientExperiencePage.getToMonthSelect());
		assertNotNull(patientExperiencePage.getToYearSelect());
		assertNotNull(patientExperiencePage.getGroupBySelect());
	}
	
	
	/*
	 * CALCULATION
	 */

	@Ignore
	@Test
	public void detectCalculationRadios() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		assertNotNull(patientExperiencePage.getMean());
		assertNotNull(patientExperiencePage.getTopBox());
	}
	
	/*
	 * BUTTONS
	 */
	
	@Ignore
	@Test
	public void detectButtons() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		assertNotNull(patientExperiencePage.getApplyButton());
	}
	
	
	/*
	 * ADVANCED FILTERS
	 */
	@Ignore
	@Test
	public void detectDemographicFiltersLink() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		assertNotNull(patientExperiencePage.getDemographicLink());
	}
	
	/*
	 * TABS!
	 * 
	 */
	
	@Ignore
	@Test
	public void detectOverviewTab() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.detectOverviewTab();
		assertNotNull(patientExperiencePage.getOverviewTab());
	}
	
	@Ignore
	@Test
	public void detectCompositeTab() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.detectCompositeTab();
		assertNotNull(patientExperiencePage.getCompositeTab());
	}
	
	@Ignore
	@Test
	public void detectSbsTab() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.detectSbsTab();
		assertNotNull(patientExperiencePage.getSbsTab());
	}
	
	@Ignore
	@Test
	public void detectDemographicTab() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.detectDemographicTab();
		assertNotNull(patientExperiencePage.getDemographicTab());
	}
	
	@Ignore
	@Test
	public void accessOverviewTab() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters().detectOverviewTab();
		patientExperiencePage.accessOverviewTab();
		
	}
	
	@Ignore
	@Test
	public void accessCompositeTab() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters().detectCompositeTab();
		patientExperiencePage.accessCompositeTab();
		
	}
	
	@Ignore
	@Test
	public void accessSbsTab() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters().detectSbsTab();
		patientExperiencePage.accessSbsTab();
		
	}
	
	@Ignore
	@Test
	public void accessDemographicsTab() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters().detectDemographicTab();
		patientExperiencePage.accessDemographicsTab();
		
	}
	
	
}
