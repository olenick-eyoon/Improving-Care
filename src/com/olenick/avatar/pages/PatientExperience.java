package com.olenick.avatar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PatientExperience {
	
	/*
	 * TABS!
	 */
	WebElement overviewTab, compositeTab, sbsTab, demographicTab;
	
	/**
	 * @return the overviewTab
	 */
	public WebElement getOverviewTab() {
		return overviewTab;
	}

	/**
	 * @param overviewTab the overviewTab to set
	 */
	public void setOverviewTab(WebElement overviewTab) {
		this.overviewTab = overviewTab;
	}

	/**
	 * @return the compositeTab
	 */
	public WebElement getCompositeTab() {
		return compositeTab;
	}

	/**
	 * @param compositeTab the compositeTab to set
	 */
	public void setCompositeTab(WebElement compositeTab) {
		this.compositeTab = compositeTab;
	}

	/**
	 * @return the sbsTab
	 */
	public WebElement getSbsTab() {
		return sbsTab;
	}

	/**
	 * @param sbsTab the sbsTab to set
	 */
	public void setSbsTab(WebElement sbsTab) {
		this.sbsTab = sbsTab;
	}

	/**
	 * @return the demographicTab
	 */
	public WebElement getDemographicTab() {
		return demographicTab;
	}

	/**
	 * @param demographicTab the demographicTab to set
	 */
	public void setDemographicTab(WebElement demographicTab) {
		this.demographicTab = demographicTab;
	}

	/*
	 * RESTO!
	 * 
	 */
	WebElement home, patientExperience, surveyControlCenter;
	WebDriver driver;
	WebDriverWait wait;
	
	/**
	 * @return the home
	 */
	public WebElement getHome() {
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public void setHome(WebElement home) {
		this.home = home;
	}

	/**
	 * @return the system
	 */
	public WebElement getSystem() {
		return system;
	}

	/**
	 * @param system the system to set
	 */
	public void setSystem(WebElement system) {
		this.system = system;
	}

	/**
	 * @return the organization
	 */
	public WebElement getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(WebElement organization) {
		this.organization = organization;
	}

	/**
	 * @return the department
	 */
	public WebElement getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(WebElement department) {
		this.department = department;
	}

	/**
	 * @return the location
	 */
	public WebElement getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(WebElement location) {
		this.location = location;
	}

	/**
	 * @return the surveyType
	 */
	public WebElement getSurveyType() {
		return surveyType;
	}

	/**
	 * @param surveyType the surveyType to set
	 */
	public void setSurveyType(WebElement surveyType) {
		this.surveyType = surveyType;
	}

	/**
	 * @return the patientType
	 */
	public WebElement getPatientType() {
		return patientType;
	}

	/**
	 * @param patientType the patientType to set
	 */
	public void setPatientType(WebElement patientType) {
		this.patientType = patientType;
	}

	/**
	 * @return the composite
	 */
	public WebElement getComposite() {
		return composite;
	}

	/**
	 * @param composite the composite to set
	 */
	public void setComposite(WebElement composite) {
		this.composite = composite;
	}

	/**
	 * @return the item
	 */
	public WebElement getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(WebElement item) {
		this.item = item;
	}

	/**
	 * @return the fromMonth
	 */
	public WebElement getFromMonth() {
		return fromMonth;
	}

	/**
	 * @param fromMonth the fromMonth to set
	 */
	public void setFromMonth(WebElement fromMonth) {
		this.fromMonth = fromMonth;
	}

	/**
	 * @return the toMonth
	 */
	public WebElement getToMonth() {
		return toMonth;
	}

	/**
	 * @param toMonth the toMonth to set
	 */
	public void setToMonth(WebElement toMonth) {
		this.toMonth = toMonth;
	}

	/**
	 * @return the fromYear
	 */
	public WebElement getFromYear() {
		return fromYear;
	}

	/**
	 * @param fromYear the fromYear to set
	 */
	public void setFromYear(WebElement fromYear) {
		this.fromYear = fromYear;
	}

	/**
	 * @return the toYear
	 */
	public WebElement getToYear() {
		return toYear;
	}

	/**
	 * @param toYear the toYear to set
	 */
	public void setToYear(WebElement toYear) {
		this.toYear = toYear;
	}

	/**
	 * @return the mean
	 */
	public WebElement getMean() {
		return mean;
	}

	/**
	 * @param mean the mean to set
	 */
	public void setMean(WebElement mean) {
		this.mean = mean;
	}

	/**
	 * @return the topBox
	 */
	public WebElement getTopBox() {
		return topBox;
	}

	/**
	 * @param topBox the topBox to set
	 */
	public void setTopBox(WebElement topBox) {
		this.topBox = topBox;
	}

	WebElement system, organization, department, location;
	WebElement surveyType, patientType, composite, item;
	/**
	 * @return the groupBy
	 */
	public WebElement getGroupBy() {
		return groupBy;
	}

	WebElement fromMonth, toMonth, fromYear, toYear, groupBy;
	WebElement mean, topBox, demographicLink, applyButton;
	/**
	 * @return the applyButton
	 */
	public WebElement getApplyButton() {
		return applyButton;
	}

	/**
	 * @return the demographicLink
	 */
	public WebElement getDemographicLink() {
		return demographicLink;
	}

	Select systemSelect, organizationSelect, departmentSelect, locationSelect;
	/**
	 * @return the surveyTypeSelect
	 */
	public Select getSurveyTypeSelect() {
		return surveyTypeSelect;
	}

	/**
	 * @return the patientTypeSelect
	 */
	public Select getPatientTypeSelect() {
		return patientTypeSelect;
	}

	/**
	 * @return the compositeSelect
	 */
	public Select getCompositeSelect() {
		return compositeSelect;
	}

	/**
	 * @return the itemSelect
	 */
	public Select getItemSelect() {
		return itemSelect;
	}

	Select surveyTypeSelect, patientTypeSelect, compositeSelect, itemSelect;
	Select fromMonthSelect, toMonthSelect, fromYearSelect, toYearSelect, groupBySelect;
	
	
	/**
	 * @return the fromMonthSelect
	 */
	public Select getFromMonthSelect() {
		return fromMonthSelect;
	}

	/**
	 * @return the toMonthSelect
	 */
	public Select getToMonthSelect() {
		return toMonthSelect;
	}

	/**
	 * @return the fromYearSelect
	 */
	public Select getFromYearSelect() {
		return fromYearSelect;
	}

	/**
	 * @return the toYearSelect
	 */
	public Select getToYearSelect() {
		return toYearSelect;
	}

	/**
	 * @return the groupBySelect
	 */
	public Select getGroupBySelect() {
		return groupBySelect;
	}

	/**
	 * @return the organizationSelect
	 */
	public Select getOrganizationSelect() {
		return organizationSelect;
	}

	/**
	 * @return the departmentSelect
	 */
	public Select getDepartmentSelect() {
		return departmentSelect;
	}

	/**
	 * @return the locationSelect
	 */
	public Select getLocationSelect() {
		return locationSelect;
	}

	/**
	 * @return the systemSelect
	 */
	public Select getSystemSelect() {
		return systemSelect;
	}

	public PatientExperience(WebDriver driver, boolean loaded) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 240);

	}
	
	public PatientExperience detectFilters() {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("Panel_1_1")));
		system = driver.findElement(By.id("system"));
		organization = driver.findElement(By.id("organization"));
		department = driver.findElement(By.id("department"));
		location = driver.findElement(By.id("location"));
		
		surveyType = driver.findElement(By.id("surveytype"));
		patientType = driver.findElement(By.id("patienttype"));
		composite = driver.findElement(By.id("factor"));
		item = driver.findElement(By.id("item"));
		
		
		fromMonth = driver.findElement(By.id("from_mnth"));
		toMonth = driver.findElement(By.id("to_mnth"));
		fromYear = driver.findElement(By.id("from_year"));
		toYear = driver.findElement(By.id("to_year"));
		groupBy = driver.findElement(By.id("date_option"));

		mean = driver.findElement(By.id("top_box_0"));
		topBox = driver.findElement(By.id("top_box_1"));
		
		demographicLink = driver.findElement(By.name("DEMOGRPH"));
		
		applyButton = driver.findElement(By.id("button2"));
		
		return this;
	}

	public PatientExperience convertToSelect(String field) {
		switch (field){
		case "system":
			systemSelect = new Select(system);
			break;
		case "organization":
			organizationSelect = new Select(organization);
			break;
		case "department":
			departmentSelect = new Select(department);
			break;
		case "location":
			locationSelect = new Select(location);
			break;
		
		case "surveyType":
			surveyTypeSelect = new Select(surveyType);
			break;
		case "patientType":
			patientTypeSelect = new Select(patientType);
			break;
		case "composite":
			compositeSelect = new Select(composite);
			break;
		case "item":
			itemSelect = new Select(item);
			break;
		case "fromMonth":
			fromMonthSelect = new Select(fromMonth);
			break;
		case "toMonth":
			toMonthSelect = new Select(toMonth);
			break;
		case "fromYear":
			fromYearSelect = new Select(fromYear);
			break;
		case "toYear":
			toYearSelect = new Select(toYear);
			break;
		case "groupBy":
			groupBySelect = new Select(groupBy);
			break;
			
		}
		return this;
	}

	public PatientExperience detectOverviewTab() {
		setOverviewTab(driver.findElement(By.id("tabitem4")));
		return this;
	}

	public PatientExperience detectCompositeTab() {
		setCompositeTab(driver.findElement(By.id("tabitem2")));
		return this;
	}
	
	public PatientExperience detectSbsTab() {
		setSbsTab(driver.findElement(By.id("tabitem3")));
		return this;
	}
	
	public PatientExperience detectDemographicTab() {
		setDemographicTab(driver.findElement(By.id("tabitem1")));
		return this;
	}

	public PatientExperience accessOverviewTab() {
		overviewTab.click();
		return this;
	}

	public PatientExperience accessCompositeTab() {
		compositeTab.click();
		return this;
	}

	public PatientExperience accessSbsTab() {
		sbsTab.click();
		return this;
	}

	public PatientExperience accessDemographicsTab() {
		demographicTab.click();
		return this;
	}

	public boolean isLoadingSingPresent() {
		int i = 0;
		if (driver.findElement(By.id("loadingiframe")).getAttribute("style").contains("block")){
			return true;
		} else {
			return false;
		}
	    
	}
	
	
	
}
