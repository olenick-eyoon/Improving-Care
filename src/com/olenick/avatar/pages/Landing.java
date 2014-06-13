package com.olenick.avatar.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Landing {

	WebDriver driver;
	WebElement home, orgReports, advancedReports, avatarDB, tools, support, logout;
	
	//Home Items
	WebElement selectOrganizationOrSystem, cmbOrganization, cmbSystem;
	
	//Org Reports Items
	WebElement matrixReport;
	
	//Advanced Reports Items
	WebElement customReport, enhancedPlatform; 
	
	public Landing(WebDriver pDriver){
		driver = pDriver;
		detectWebElements();
		
	}

	private void detectWebElements() {
		
		
	}
	
}
