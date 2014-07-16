package com.olenick.avatar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.olenick.avatar.exceptions.HomeLinkInvalid;
import com.olenick.avatar.exceptions.ICare2PageNotDisplayed;
import com.olenick.avatar.exceptions.PatientExperienceLinkInvalid;
import com.olenick.avatar.exceptions.SurveyControlCenterLinkInvalid;

public class ICare2 {

	
	WebElement home, patientExperience, surveyControlCenter;
	String menuBarXpath = "//div[@id='BiHBox-135']";
	WebDriver driver;

	int i = 0;
	
	public ICare2 (WebDriver driver, boolean loaded) {
		this.driver = driver;
		
	}

	public ICare2 switchToNewWindow() throws InterruptedException, ICare2PageNotDisplayed {
		while (getWindows() == 2 && ++i > 60){ Thread.sleep(500);}
	    
		if (i > 60) throw new ICare2PageNotDisplayed();
		
		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		driver.manage().window().maximize();

		Thread.sleep(17000);
		
		return this;
	}
	
	public int getWindows() {
		return driver.getWindowHandles().size();
	}

	public ICare2 detectMenuBarItems() throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException {
		Thread.sleep(10000);
		//assurePDLoaded();
		home = driver.findElement(By.xpath(menuBarXpath+"/div[1]"));
		patientExperience = driver.findElement(By.xpath(menuBarXpath+"/div[2]"));
		surveyControlCenter = driver.findElement(By.xpath(menuBarXpath+"/div[3]"));
		if (home == null) throw new HomeLinkInvalid();
		if (patientExperience == null) throw new PatientExperienceLinkInvalid();
		if (surveyControlCenter == null) throw new SurveyControlCenterLinkInvalid();
		
		return this;
	}
	
	public ICare2 switchToMainIFrame(){
		WebDriverWait wait = new WebDriverWait(driver, 240);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("main-iframe"));
		//driver.switchTo().frame("main-iframe");
		return this;
	}

	public ICare2 accessHomeTab() {
		home.click();
		return this;
	}

	public PatientExperience accessPatientExperienceTab() throws InterruptedException {
		
		patientExperience.click();
		PatientExperience patientExperiencePage = new PatientExperience(driver, true);
		
		
		
		return patientExperiencePage;
	}
	
	public SurveyControlCenter accessSurveyControlCenterTab() { 
		surveyControlCenter.click();
		SurveyControlCenter surveyControlCenterPage = new SurveyControlCenter(driver, true);
		return surveyControlCenterPage;
	}
	
	
	/*
	private void assurePDLoaded() throws InterruptedException {
		int i = 0;
	    while (driver.findElement(By.id("loadingiframe")).getAttribute("style").contains("block")){
			Thread.sleep(1000);
			++i;
			if (i >= 200) throw new java.util.NoSuchElementException("PATIENT DEMOGRAPHIC IS TAKING TOO LONG TO LOAD (200 seconds elapsed");
		}
	}
	*/
	
}
