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

public class ICare2Page {

	WebDriver driver = new FirefoxDriver();
	Login loginPage;
	Landing landingPage;
	ICare2 iCare2Page;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Ignore
	@Test
	public void createIcare2Page() {
		iCare2Page = new ICare2(driver, false);
		assertNotNull(iCare2Page);
	}
	
	@Ignore
	@Test
	public void verifyTitle() throws InterruptedException, ICare2PageNotDisplayed {
		iCare2Page = new Login(driver).open(true).login("rferrari@avatarsolutions.com", "password")
				.drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow();
		
		assertEquals("iCare Enhanced Platform", driver.getTitle());
	}

	@Ignore
	@Test
	public void findMenuBar() throws InterruptedException, ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
		iCare2Page = new Login(driver).open(true).login("rferrari@avatarsolutions.com", "password")
				.drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow();
		iCare2Page.switchToMainIFrame().detectMenuBarItems();
		
	}
	
	@Ignore
	@Test
	public void accessHomeTab() throws InterruptedException, ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
		iCare2Page = new Login(driver).open(true).login("rferrari@avatarsolutions.com", "password")
				.drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
		if (iCare2Page==null) fail("Some of the menu items are null");
		iCare2Page.accessHomeTab();
		
	}
	
	@Test
	public void accessPatientExperienceTab() throws InterruptedException, ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
		iCare2Page = new Login(driver).open(true).login("rferrari@avatarsolutions.com", "password")
				.drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
		if (iCare2Page==null){
			System.out.println("No detecte algo");
			fail("Some if the menu items are null");
		} else {
			iCare2Page.accessPatientExperienceTab();
		}
		
	}
	
	@Test
	public void accessSurveyControlCenterTab() throws InterruptedException, ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
		iCare2Page = new Login(driver).open(true).login("rferrari@avatarsolutions.com", "password")
				.drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
		if (iCare2Page==null){
			System.out.println("No detecte algo");
			fail("Some if the menu items are null");
		} else {
			iCare2Page.accessSurveyControlCenterTab();
		}
	}
}
