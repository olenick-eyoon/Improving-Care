package com.olenick.avatar.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.avatar.pages.ICare2;
import com.olenick.avatar.pages.Landing;
import com.olenick.avatar.pages.Login;

//@Ignore
public class LandingPage {

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

	@Test
	public void openLandingPage() throws InterruptedException {
		loginPage = new Login(driver);
		loginPage.open(true).login("rferrari@avatarsolutions.com", "password");
		assertEquals("Intelligent Surveys Online", driver.getTitle());
	}
	
	@Test
	public void openEnhancedReports() throws InterruptedException {
		loginPage = new Login(driver);
		iCare2Page = loginPage.open(true).login("rferrari@avatarsolutions.com", "password").drillDownAdvancedReports().accessEnhancedReports();
		assertNotNull(iCare2Page);
	}
	

}
