package com.olenick.avatar.pages;

import org.jdom2.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.olenick.avatar.parsers.xml.XMLParser;

public class PatientExperience {
	
	public boolean noDataAvailable;
	/**
	 * @return the noDataAvailable
	 */
	public boolean getNoDataAvailable() {
		return noDataAvailable;
	}

	/**
	 * @param noDataAvailable the noDataAvailable to set
	 */
	public void setNoDataAvailable(boolean noDataAvailable) {
		this.noDataAvailable = noDataAvailable;
	}

	XMLParser xmlParser = new XMLParser();
	
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
	WebElement admission, age, discharge, gender, language, length, race, serviceLine;

	
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
	Select admissionSelect, ageSelect, dischargeSelect, genderSelect, languageSelect, lengthSelect, raceSelect, serviceLineSelect;
	
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
	
	public PatientExperience detectFilters() throws InterruptedException {
		accessPanelFrame();
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
		case "admission":
			admissionSelect = new Select(admission);
			break;
		case "age":
			ageSelect = new Select(age);
			break;
		case "discharge":
			dischargeSelect = new Select(discharge);
			break;
		case "gender":
			genderSelect = new Select(gender);
			break;
		case "language":
			languageSelect = new Select(language);
			break;
		case "length":
			lengthSelect = new Select(length);
			break;
		case "race":
			raceSelect = new Select(race);
			break;
		case "serviceLine":
			serviceLineSelect = new Select(serviceLine);
			break;
		}
		return this;
	}

	public PatientExperience detectOverviewTab() throws InterruptedException {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("Panel_1_1"));
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

	public PatientExperience accessOverviewTab() throws InterruptedException {
		assurePDLoaded();
		accessPanelFrame();
		overviewTab.click();
		return this;
	}

	public PatientExperience accessCompositeTab() throws InterruptedException {
		//assurePDLoaded();
		accessPanelFrame();
		compositeTab.click();
		return this;
	}

	public PatientExperience accessSbsTab() throws InterruptedException {
		//assurePDLoaded();
		accessPanelFrame();
		sbsTab.click();
		return this;
	}

	public PatientExperience accessDemographicsTab() throws InterruptedException {
		//assurePDLoaded();
		accessPanelFrame();
		demographicTab.click();
		return this;
	}

	private void assurePDLoaded() throws InterruptedException {
		int i = 0;
	    try{
			while (driver.findElement(By.id("loadingiframe")).getAttribute("style").contains("block")){
				Thread.sleep(1000);
				++i;
				if (i >= 200) throw new java.util.NoSuchElementException("PATIENT DEMOGRAPHIC IS TAKING TOO LONG TO LOAD (200 seconds elapsed");
			}
	    } catch (NoSuchElementException e){
	    	System.out.println("NO LOADING FRAME");
	    }
	}

	public PatientExperience validateOverviewTabData() throws InterruptedException {
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("ovrvwframe"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("visframe"));
		waitForGraphs();
		
		validateResultsOnScreen();
		
		return this;
	}
	

	private boolean isElementPresent (String elementId) {
		try {
			if (!elementId.equalsIgnoreCase("center") && !elementId.equalsIgnoreCase("table")){
				driver.findElement(By.id(elementId));
				} else {
					driver.findElement(By.tagName(elementId));
				}
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public PatientExperience validateCompositeTabData() throws InterruptedException {
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("rptfactor"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportCGbar"));
		waitForGraphs();
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("rptfactor"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportCGbar1"));
		waitForGraphs();
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("rptfactor"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportCGline"));
		waitForGraphs();
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("rptfactor"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportCGgrid"));
		waitForGraphs();
		
		validateResultsOnScreen();
		
		return this;
	}
	
	public PatientExperience validateSbsTabData() throws InterruptedException {
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("sdframe"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("iframe91"));
		waitForGraphs();
		
		validateResultsOnScreen();
		
		return this;
		
	}

	private void validateResultsOnScreen() {
		setNoDataAvailable(false);
		if ((isElementPresent("table") || isElementPresent("jschart_HOLD_0"))) { 
			//System.out.println("Page displayed");
		} else if (isElementPresent("center")){
			setNoDataAvailable(true);
			//System.out.println("No data available");
		}
	}

	private void waitForGraphs() throws InterruptedException {
		int i = 0;
		while (!isElementPresent("center") && !(isElementPresent("table") || (isElementPresent("jschart_HOLD_0"))) && i < 60){
			Thread.sleep(1000);
			++i;
		}
	}
	
	private void accessPanelFrame() throws InterruptedException {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(0);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("Panel_1_1"));
	}

	public PatientExperience validateDemographicsTabData() throws InterruptedException {
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("report3"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("report1"));
		waitForGraphs();
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("report3"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("report2"));
		waitForGraphs();
		
		accessPanelFrame();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("report3"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("report33"));
		waitForGraphs();
		
		validateResultsOnScreen();
		
		return this;
		
	}

	public PatientExperience runSearch(Element patientDemographicElement) throws InterruptedException {
		accessPanelFrame();
		completeReportLevel(patientDemographicElement);
		completeSurveySelection(patientDemographicElement);
		driver.findElement(By.id("button2")).click();
		return this;
	}

	private void completeReportLevel(Element patientDemographicElement) throws NoSuchElementException, InterruptedException {
		scrollUp("system");
		systemSelect.selectByValue(xmlParser.getSystem(patientDemographicElement));
		setMultiselectListValue("organization", xmlParser.getOrganization(patientDemographicElement));
		setMultiselectListValue("department", xmlParser.getDepartment(patientDemographicElement));
		setMultiselectListValue("location",	xmlParser.getLocation(patientDemographicElement));
	}
	
	private void completeSurveySelection(Element rootElement) throws NoSuchElementException, InterruptedException {
		scrollUp("surveytype");
		//surveyTypeSelect.selectByValue(xmlParser.getSurveyType(rootElement));
		surveyType.sendKeys(xmlParser.getSurveyType(rootElement));
		setMultiselectListValue("patienttype", xmlParser.getPatientType(rootElement));
		setMultiselectListValue("factor", xmlParser.getFactorComposite(rootElement));	
		setMultiselectListValue("item", xmlParser.getItemQuestion(rootElement));	

		completeCalculationFilters(rootElement);

		scrollUp("system");

		fromMonth.sendKeys(resolveMonth(xmlParser.getFromMonth(rootElement)));
		scrollUp("system");
		fromYear.sendKeys(xmlParser.getFromYear(rootElement));
		scrollUp("system");
		toMonth.sendKeys(resolveMonth(xmlParser.getToMonth(rootElement)));
		scrollUp("system");
		toYear.sendKeys(xmlParser.getToYear(rootElement));
		scrollUp("system");
		
		//completeDemographicFilters(rootElement);	

	}
	
	
	private static String resolveMonth(String fromMonth) {

		String output = "";
		switch (fromMonth){
			case "01":
				output = "jan";
				break;
			case "02":
				output = "feb";
				break;
			case "03":
				output = "mar";
				break;
			case "04":
				output = "apr";
				break;
			case "05":
				output = "may";
				break;
			case "06":
				output = "jun";
				break;
			case "07":
				output = "jul";
				break;
			case "08":
				output = "aug";
				break;
			case "09":
				output = "sep";
				break;
			case "10":
				output = "oct";
				break;
			case "11":
				output = "nov";
				break;
			case "12":
				output = "dec";
				break;
		}
		return output;
	}
	
	
	private void completeDemographicFilters(Element rootElement) throws InterruptedException {
		accessPanelFrame();
		demographicLink.click();
		Thread.sleep(2000);
		
		setMultiselectListValue("ptadmission", xmlParser.getPatientAdmission(rootElement));
		setMultiselectListValue("ptage", xmlParser.getPatientAge(rootElement));		
		setMultiselectListValue("ptdischarge", xmlParser.getPatientDischarge(rootElement));
		setMultiselectListValue("ptgender",	xmlParser.getPatientGender(rootElement));	
		setMultiselectListValue("ptlanguage", xmlParser.getPatientLanguage(rootElement));	
		setMultiselectListValue("ptlength",	xmlParser.getPatientLength(rootElement));	
		setMultiselectListValue("ptrace", xmlParser.getPatientRace(rootElement));		
		setMultiselectListValue("ptservice", xmlParser.getPatientService(rootElement));
		
		driver.findElement(By.id("buttonadd")).click();
		
	}

	private void completeCalculationFilters(Element rootElement) {
		if (xmlParser.getCalculation(rootElement).equalsIgnoreCase("1")) {
			if (driver.findElement(By.id("top_box_0")).isDisplayed()) driver.findElement(By.id("top_box_0")).click();
		} else {
			if (driver.findElement(By.id("top_box_1")).isDisplayed()) driver.findElement(By.id("top_box_1")).click();
		}
		groupBySelect.selectByValue(xmlParser.getGroupBy(rootElement));
	}
	
	private void setMultiselectListValue(String id, String[] values) throws NoSuchElementException, InterruptedException {
		accessPanelFrame();
		
		WebElement elem = driver.findElement(By.id(id));
		elem.click();// Open dropdown list
		
		Select sel = new Select(elem);
		Thread.sleep(1250);

		sel.deselectAll();

		for (String value : values) {
			String path = "option[@value=\"" + value + "\"]";
			if (elem.findElement(By.xpath(path)) != null) {
				expandCombo(id);
				sel.selectByValue(value);
				scrollUp(id);
			}
		}
		WebElement elem2 = driver.findElement(By.id(id));
		elem2.sendKeys(Keys.RETURN);		// Close dropdown list
		
		scrollUp("system");
	}
	
	private void expandCombo(String id) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('" + id + "').setAttribute('size', '50')");
	}	
	
	private void scrollUp(String id) {
		if (!id.equalsIgnoreCase("rpt_type") || !id.equalsIgnoreCase("category")) 
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.id("text1")));
	}

	public PatientExperience accessAndValidateTab(String attributeFromXML) throws InterruptedException {
		accessPanelFrame();
		
		switch (attributeFromXML){
		case "overview":
			accessOverviewTab().validateOverviewTabData();
			break;
		case "side by side":
			accessSbsTab().validateSbsTabData();
			break;
		case "composite":
			accessCompositeTab().validateCompositeTabData();
			break;
		case "demographics":
			accessDemographicsTab().validateDemographicsTabData();
			break;
		}
		
		return this;
	}

	public PatientExperience exportToPDF(String tab) throws InterruptedException {
		accessPanelFrame();
		
		switch (tab){
		case "side by side":
			accessSbsTab();
			exportSBStoPDF();
			break;
		case "composite":
			accessCompositeTab();
			exportCompositeToPDF();
			break;
		case "demographics":
			accessDemographicsTab();
			exportDemographicsToPDF();
			break;
		}
		return this;
	}

	private void exportSBStoPDF() throws InterruptedException {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("sdframe"));
		driver.findElement(By.id("imgopt")).click();
		driver.findElement(By.id("compdf")).click();
		handlePDFNewWindow();
	}
	
	private void exportDemographicsToPDF() throws InterruptedException {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("report3"));
		driver.findElement(By.id("image45")).click();
		handlePDFNewWindow();
	}

	private void exportCompositeToPDF() throws InterruptedException {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("rptfactor"));
		driver.findElement(By.id("imageCGpdf")).click();
		handlePDFNewWindow();
	}
	
	private void handlePDFNewWindow() throws InterruptedException {
		//while (driver.getWindowHandles().size()==2){
		while (driver.getWindowHandles().size()==1){
	    	Thread.sleep(500);
	    }
	    //driver.switchTo().window(driver.getWindowHandles().toArray()[2].toString());
	    //driver.close();
	    //driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
	    //NEW VESION
	    driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
	    driver.close();
	    driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
	}

	public PatientExperience convertFiltersToSelect() throws InterruptedException {
		accessPanelFrame();
		convertToSelect("system");
		convertToSelect("organization");
		convertToSelect("department");
		convertToSelect("location");
		convertToSelect("surveyType");
		convertToSelect("patientType");
		convertToSelect("composite");
		convertToSelect("item");
		convertToSelect("fromMonth");
		convertToSelect("fromYear");
		convertToSelect("toMonth");
		convertToSelect("toYear");
		convertToSelect("groupBy");
		return this;
	}


}
