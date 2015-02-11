package com.olenick.avatar.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SurveyControlCenter {

	WebElement home, patientExperience, surveyControlCenter;
	WebDriver driver;
	
	public SurveyControlCenter (WebDriver driver, boolean loaded) {
		this.driver = driver;
	}
	
}
