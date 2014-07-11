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

public class OverviewTab {

	Login loginPage;
	Landing landingPage;
	ICare2 iCare2Page; 
	PatientExperience patientExperiencePage;
	PatientExperience overviewTab;
	
	WebDriver driver = new FirefoxDriver();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	//@Ignore
	@Test
	public void loadingSignPresent() throws Exception {
		loginPage = new Login(driver);
		landingPage = loginPage.open(true).login("rferrari@avatarsolutions.com", "password");
		iCare2Page = landingPage.drillDownAdvancedReports().accessEnhancedReports().switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
		patientExperiencePage = iCare2Page.accessPatientExperienceTab().detectFilters();
		overviewTab = patientExperiencePage.detectOverviewTab().accessOverviewTab();
		overviewTab.isLoadingSingPresent();
	}

	
	/*
		private static void assurePDLoaded() throws InterruptedException {
		int i = 0;
	    while (driver.findElement(By.id("loadingiframe")).getAttribute("style").contains("block")){
			Thread.sleep(1000);
			++i;
			if (i >= 200) throw new java.util.NoSuchElementException("PATIENT DEMOGRAPHIC IS TAKING TOO LONG TO LOAD (200 seconds elapsed");
		}
	}
	*/
}
