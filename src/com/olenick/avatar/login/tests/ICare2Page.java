package com.olenick.avatar.login.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.avatar.exceptions.ICare2PageNotDisplayed;
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

	@Test
	public void createIcare2Page() {
		iCare2Page = new ICare2(driver, false);
		assertNotNull(iCare2Page);
	}
	
	@Test
	public void verifyTitle() throws InterruptedException, ICare2PageNotDisplayed {
		iCare2Page = new Login(driver).open(true).login("rferrari@avatarsolutions.com", "password")
				.drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow();
		
		assertEquals("iCare Enhanced Platform", driver.getTitle());
	}

	@Test
	public void findMenuBar() throws InterruptedException, ICare2PageNotDisplayed {
		iCare2Page = new Login(driver).open(true).login("rferrari@avatarsolutions.com", "password")
				.drillDownAdvancedReports().accessEnhancedReports()
				.switchToNewWindow();
		Thread.sleep(15000);
		iCare2Page.switchToMainIFrame().detectMenuBarItems();
		
	}
	
}
