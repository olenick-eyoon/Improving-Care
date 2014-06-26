package com.olenick.avatar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Landing {

	WebDriver driver;
	//Menu Bar
	WebElement home, orgReports, advancedReports;
	
	//Home Items
	WebElement selectOrganizationOrSystem;
	Select cmbOrganization, cmbSystem;
	
	//Org Reports Items
	WebElement matrixReport;
	
	//Advanced Reports Items
	WebElement customReport, enhancedPlatform; 
	
	public Landing(WebDriver pDriver, boolean loaded){
		driver = pDriver;
		if (loaded) detectLandingWebElements(); 
		
	}

	private void detectLandingWebElements() {
		//Menu Bar
		home = driver.findElement(By.linkText("Home"));
		orgReports = driver.findElement(By.linkText("Org Reports"));
		advancedReports = driver.findElement(By.linkText("Advanced Reports"));
	}
	
	public void detectHomeSubMenuItems() {
		selectOrganizationOrSystem = driver.findElement(By.linkText("Select Organization or System"));
		
	}
	
	public void detectSelectOrgWebElements() {
		cmbOrganization = new Select(driver.findElement(By.id("org_id")));
		cmbSystem = new Select(driver.findElement(By.id("ol_id")));
	}
	
	public void detectAdvReportsWebElements() {
		enhancedPlatform = driver.findElement(By.linkText("Enhanced Platform"));
		customReport = driver.findElement(By.linkText("Custom Report"));
	}
	
	public Landing drillDownHome() {
		home.click();
		return this;
	}
	
	public Landing openOrgOrSystemSelection() {
		selectOrganizationOrSystem.click();
		return this;
	}
	
	public Landing setOrganization(String organizationId) {
		cmbOrganization.selectByValue(organizationId);
		return this;
	}
	
	public Landing setSystem(String systemId) {
		cmbSystem.selectByValue(systemId);
		return this;
	}
	
	public Landing submitOrgOrSystem() {
		driver.findElement(By.xpath("//div[@id='selectbtn']/input")).click();
		return this;
	}

	public Landing drillDownAdvancedReports() {
		advancedReports.click();
		detectAdvReportsWebElements();
		return this;
	}

	public ICare2 accessEnhancedReports() throws InterruptedException {
		enhancedPlatform.click();
		Thread.sleep(5000);
		ICare2 iCare2Page = new ICare2(driver, true);
		return iCare2Page;
	}
	
}
