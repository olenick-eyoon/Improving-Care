package com.olenick.avatar.login.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	public void openPage() throws InterruptedException, ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid{
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
	
	@Test
	public void convertSystemElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("system");
		assertNotNull(patientExperiencePage.getSystemSelect());
	}
	
	@Test
	public void convertOrganizationElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("organization");
		assertNotNull(patientExperiencePage.getOrganizationSelect());
	}
	
	@Test
	public void convertDepartmentElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("department");
		assertNotNull(patientExperiencePage.getDepartmentSelect());
	}
	
	@Test
	public void convertLocationElementToSelect() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
		loginPage = new Login(driver);
		patientExperiencePage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems().accessPatientExperienceTab().detectFilters();
		patientExperiencePage.convertToSelect("location");
		assertNotNull(patientExperiencePage.getLocationSelect());
	}
	
	
}
