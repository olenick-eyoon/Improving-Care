package com.olenick.avatar.login.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.avatar.pages.Login;

public class LoginPage {

	WebDriver driver = new FirefoxDriver();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void openLoginPage(){
		Login loginPage = new Login(driver);
		loginPage.open(false);
		assertEquals("Intelligent Surveys Online Reporting", driver.getTitle());
	}
	
	@Test
	public void loginUser(){
		Login loginPage = new Login(driver);
		loginPage.open(false).login("dash@avatarsolutions.com", "password");
		assertEquals("Intelligent Surveys Online", driver.getTitle());
	}
	
	
	


}
