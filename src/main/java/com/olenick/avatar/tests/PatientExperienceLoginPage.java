package com.olenick.avatar.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.avatar.pages.PatientExperienceLogin;

public class PatientExperienceLoginPage {

	WebDriver driver;
	PatientExperienceLogin peLoginPage = new PatientExperienceLogin();
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		if (driver != null) driver.quit();
	}

	@Test
	public void LoginPageObject() {
		assertNotNull(peLoginPage);
	}
	
	@Test
	public void openPeLoginPage() {
		driver = new FirefoxDriver();
		peLoginPage = new PatientExperienceLogin(driver);
		peLoginPage.open(true);
	}
	
	@Test
	public void detectElements() {
		driver = new FirefoxDriver();
		peLoginPage = new PatientExperienceLogin(driver);
		peLoginPage.open(true).detectElements();
	}


}
