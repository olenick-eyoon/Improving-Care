package com.olenick.avatar.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.olenick.avatar.exceptions.HomeLinkInvalid;
import com.olenick.avatar.exceptions.ICare2PageNotDisplayed;
import com.olenick.avatar.exceptions.PatientExperienceLinkInvalid;
import com.olenick.avatar.exceptions.SurveyControlCenterLinkInvalid;
import com.olenick.avatar.pages.ICare2;
import com.olenick.avatar.pages.Landing;
import com.olenick.avatar.pages.Login;
import com.olenick.avatar.pages.PatientExperience;
import com.olenick.avatar.parsers.xml.*;
import com.olenick.avatar.reports.ErrorHandler;
import com.olenick.avatar.reports.ReportGenerator;
import com.olenick.avatar.timer.Timer;

public class Avatar_v3_0 {
	
	/*
	 * Bloque de variables
	 */
	
		//Webdriver Declarations
		public static WebDriver driver = new FirefoxDriver();
		public static WebDriverWait wait = new WebDriverWait(driver, 240);
		public static String baseUrl = "";
		
		
		//XML Declarations
		public static String xmlFilePath = "./resources/XML Files/";
		public static Document xmlFile;
		public static XMLParser xmlParser = new XMLParser();
		
		
		//Model objects
		public static Login loginPage;
		public static Landing landingPage;
		public static ICare2 iCare2Page;
		public static PatientExperience patientExperiecePage;
		public static ReportGenerator reportGenerator;
		public static Timer timer = new Timer();
		public static ErrorHandler errorHandler= new ErrorHandler();
		
		private static boolean firstRun = true;
		public static long startTime = 0, endTime = 0;
		@SuppressWarnings("unused")
		private static boolean keepOverview = false;

		
	public static void main(String[] args) throws InterruptedException, ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, IOException {
		
		//Bloque de ejecución
	    	xmlFile = xmlParser.loadFile(xmlFilePath + args[0]); //Loads xml file from the command line arguments
			Element root = xmlFile.getRootElement(); //Generates the root element we will use during execution
			initializeReportGeneratorObject(args);
			errorHandler.initializePaths().setComputerName(reportGenerator.getComputerName());
			

		//Bloque de login
			timingLogin(args, root);
			timingIc1(root);
			
		//Bloque de acciones en IC1
			try{
				updateSystemAndOrganization(root);
				accessPatientExperience();
				for (Element patientDemographicElement : root.getChildren("patient-demographic")) {
					firstRunTrigger();
					patientExperiecePage.runSearch(patientDemographicElement);
					timingOverview();
					accessAndValidatePatientExperienceTabs(patientDemographicElement);
					reportGenerator.printLineToFile(buildReportLine(xmlParser.getScenario(patientDemographicElement)));
				}
			} catch (NoSuchElementException e) {
				handleExceptions(args, true, false, false, e);
			} catch (StaleElementReferenceException e2) {
				handleExceptions(args, false, true, false, e2);
			} catch (Exception e3) {
				handleExceptions(args, false, false, true, e3);
			}
			
		//Bloque de cierre
		cleanUpMess();
	}

	//METHODS

		private static void handleExceptions(String[] args, boolean NSEEx, boolean SEEx, boolean OEx, Exception e)
				throws FileNotFoundException, UnsupportedEncodingException,
				IOException {
			checkLog(args);
			errorHandler.takeScreenshot(driver);
			errorHandler.addEventToLog(NSEEx, SEEx, OEx, e);
		}
	
	
		private static void initializeReportGeneratorObject(String[] args)
				throws FileNotFoundException, UnsupportedEncodingException,
				UnknownHostException {
			reportGenerator = new ReportGenerator();
			reportGenerator.createWriter("./resources/" + reportGenerator.getComputerName() + "_" + args[0].replace(".xml", "")  + "_" + reportGenerator.getDate() + ".csv");
			reportGenerator.addHeader();
		}
		
		private static String buildReportLine(String scenario) throws UnknownHostException {
			String output = "";
			output = scenario + ";";
			if (timer.getOverviewTime() == 0){
				output += ";";
			} else {
				output += timer.getOverviewTime() + ";";
			}
			if (timer.getCompositeTime() == 0){
				output += ";";
			} else {
				output += timer.getCompositeTime() + ";";
			}
			if (timer.getSideBySideTime() == 0){
				output += ";";
			} else {
				output += timer.getSideBySideTime() + ";";
			}
			if (timer.getDemographicsTime() == 0){
				output += ";";
			} else {
				output += timer.getDemographicsTime() + ";";
			}
			if (timer.getExportCompositeTime() == 0){
				output += ";";
			} else {
				output += timer.getExportCompositeTime() + ";";
			}
			if (timer.getExportDemographicsTime() == 0){
				output += ";";
			} else {
				output += timer.getExportDemographicsTime() + ";";
			}
			if (timer.getExportSideBySideTime() == 0){
				output += ";";
			} else {
				output += timer.getExportSideBySideTime() + ";";
			}
			return output;
		}
	
		private static void cleanUpMess() {
			reportGenerator.wrapUpFile();
			errorHandler.wrapUpFile();
			if (driver != null) driver.quit();
		}

		private static void checkLog(String[] args) throws FileNotFoundException,
				UnsupportedEncodingException {
			if (errorHandler.getErrorLog() == null) { 
				errorHandler.setErrorTimeStamp(reportGenerator.getDate());
				errorHandler.initializeLog(args);
			}
		}
	
		private static void accessAndValidatePatientExperienceTabs(
				Element patientDemographicElement) throws InterruptedException {
			for (String tab : xmlParser.getTabs(patientDemographicElement)){
				if (tab.equalsIgnoreCase("overview")) keepOverview  = true;
				accessAndValidateTab(patientDemographicElement, tab);
				timingPdfExport(tab);
			}
			if (keepOverview = false) timer.setOverviewTime(0);
		}
	
		private static void timingPdfExport(String tab) throws InterruptedException {
			startTime = timer.setStartTime();
			patientExperiecePage.exportToPDF(tab);
			endTime = timer.setEndTime();
			switch (tab){
			case "composite":
				timer.setExportCompositeTime(endTime - startTime);
				break;
			case "side by side":
				timer.setExportSideBySideTime(endTime - startTime);
				break;
			case "demographics":
				timer.setExportDemographicsTime(endTime - startTime);
				break;
			}
		}
	
		private static void timingOverview() throws InterruptedException {
			timer.setStartTime();
			patientExperiecePage.accessOverviewTab().validateOverviewTabData();
			timer.setEndTime();
			timer.setOverviewTime(endTime - startTime);
		}
	
		private static void accessAndValidateTab(Element patientDemographicElement, String tab)
				throws InterruptedException {
			
			startTime = timer.setStartTime();
			patientExperiecePage.accessAndValidateTab(tab);
			endTime = timer.setEndTime();
			
			switch (tab){
			case "composite":
				timer.setCompositeTime(endTime - startTime);
				break;
			case "side by side":
				timer.setSideBySideTime(endTime - startTime);
				break;
			case "demographics":
				timer.setDemographicsTime(endTime - startTime);
				break;
			}
		}
	
		private static void timingIc1(Element root) throws InterruptedException {
			startTime = timer.setStartTime();
			landingPage = loginPage.login(root.getChildTextTrim("user"), root.getChildTextTrim("password"));
			endTime = timer.setEndTime();
			timer.setIc1Time(endTime - startTime);
		}
	
		private static void timingLogin(String[] args, Element root)
				throws InterruptedException {
			startTime = timer.setStartTime();
			loginPage = new Login(driver);
			loginPage.open(defineEnvironment(args));
			endTime = timer.setEndTime();
			timer.setLoginTime(endTime - startTime);
			
		}
	
		private static boolean defineEnvironment(String[] args) {
			if (args[1].equalsIgnoreCase("qa")){ 
	    		return true;
	    	} else {
	    		return false;
	    	}
		}
	
		private static void selectSystemAndOrganization(Element root) throws InterruptedException {
			if (xmlParser.getSystem(root).equalsIgnoreCase("-68") || xmlParser.getSystem(root).equalsIgnoreCase("-426") || xmlParser.getSystem(root).equalsIgnoreCase("-56") || xmlParser.getSystem(root).equalsIgnoreCase("731")) {
				landingPage.setSystem("0");
				landingPage.setOrganization(xmlParser.getSystem(root).replace("-", ""));
			} else {
				landingPage.setSystem(xmlParser.getSystem(root));
			}
			
			landingPage.submitOrgOrSystem();
			Thread.sleep(1500);
		}

		private static void accessPatientExperience() 
				throws HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, InterruptedException, ICare2PageNotDisplayed {
			landingPage = new Landing(driver, true);
			iCare2Page = landingPage.drillDownAdvancedReports().accessEnhancedReports().switchToNewWindow();
			
			timingPatientExperience();
			
		}

		private static void timingPatientExperience() throws HomeLinkInvalid,
				PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
				InterruptedException {
			startTime = timer.setStartTime();
			iCare2Page.switchToMainIFrame().detectMenuBarItems();
			patientExperiecePage = iCare2Page.accessPatientExperienceTab()
					.detectOverviewTab().detectCompositeTab().detectDemographicTab().detectSbsTab()
					.detectFilters().convertFiltersToSelect();
			patientExperiecePage = patientExperiecePage.accessOverviewTab();
			patientExperiecePage.validateOverviewTabData();
			endTime = timer.setEndTime();
			
			timer.setIc2Time(endTime - startTime);
		}

		private static void updateSystemAndOrganization(Element root)
				throws InterruptedException {
			landingPage.drillDownHome().openOrgOrSystemSelection();
			selectSystemAndOrganization(root);
		}
		
		private static void firstRunTrigger() throws UnknownHostException {
			reportGenerator.addText(reportGenerator.generateTerminalData());

			if (firstRun) {
				reportGenerator.addText(  timer.getLoginTime() + ";"
						+ timer.getIc1Time() + ";"
						+ timer.getIc2Time() + ";");
				firstRun = false;
			} else {
				reportGenerator.addText(";;;");
				keepOverview = false;
			}
		}
}