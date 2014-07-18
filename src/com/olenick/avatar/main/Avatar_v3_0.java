package com.olenick.avatar.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.jdom2.Document;
import org.jdom2.Element;
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
		
		private static boolean firstRun;
		public static long startTime = 0, endTime = 0;
		@SuppressWarnings("unused")
		private static boolean keepOverview = false;

		
	public static void main(String[] args) throws InterruptedException, ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid, FileNotFoundException, UnsupportedEncodingException, UnknownHostException {
		
		/*
		 * Bloque de ejecución
		 */
		
	    	xmlFile = xmlParser.loadFile(xmlFilePath + args[0]); //Loads xml file from the command line arguments
			Element root = xmlFile.getRootElement(); //Generates the root element we will use during execution
			
			reportGenerator = new ReportGenerator();
			reportGenerator.createWriter("./resources/testFile.txt");
			
		/*
		 * Bloque de login
		 */
			timingLogin(args, root);
			timingIc1(root);
			
		/*
		 * Bloque de acciones en IC1
		 */
			
			reportGenerator.addHeader();
			reportGenerator.printLineToFile(reportGenerator.getContent());
			
			try{
				updateSystemAndOrganization(root);
				accessPatientExperience();
				for (Element patientDemographicElement : root.getChildren("patient-demographic")) {
					firstRunTrigger();
					reportGenerator.addText(xmlParser.getScenario(patientDemographicElement)+";"); //Scenario Name
					patientExperiecePage.runSearch(patientDemographicElement);
					timingOverview();
					accessAndValidatePatientExperienceTabs(patientDemographicElement);
							
							System.out.println("nodo.");
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			/*
			} catch (NoSuchElementException e){
				System.out.println("CANNOT FIND ELEMENT: " + globalId);
				takeScreenshot();
	    		
	    		if (errorLog == null){
	    			errorLog = createErrorLog(args);
	    		}
	    		
	    		errorLog.println(getTimeStamp() + " CANNOT FIND ELEMENT: " + globalId);
	    		errorLog.println("Screenshot: " + screenshotName);
				errorLog.println("-----STACK TRACE------");
				e.printStackTrace(errorLog);
				errorLog.println("\n");
	    		
	    	} catch (StaleElementReferenceException e3) {
	    		System.out.println("ELEMENT NO LONGER ATTACHED TO THE DOM: " + globalId);
	    		takeScreenshot();
	    		
	    		if (errorLog == null){
	    			errorLog = createErrorLog(args);
	    		}
	    		
				errorLog.println(getTimeStamp() + " ELEMENT NO LONGER ATTACHED TO THE DOM: " + globalId);
				errorLog.println("-----STACK TRACE------");
				e3.printStackTrace(errorLog);
				errorLog.println("\n");
	    		
			} catch (Exception e2){
				System.out.println("OTHER EXCEPTION");
				takeScreenshot();
				
				if (errorLog == null){
	    			errorLog = createErrorLog(args);
	    		}
				
				errorLog.println(getTimeStamp() + " OTHER EXCEPTION: " + globalId);
	    		errorLog.println("Screenshot: " + screenshotName);
				errorLog.println("-----STACK TRACE------");
				e2.printStackTrace(errorLog);
				errorLog.println("\n");
	    		*/
			}
			
			
			/*
			patientExperiecePage = iCare2Page.accessPatientExperienceTab().detectOverviewTab().detectCompositeTab().detectDemographicTab().detectSbsTab();

			patientExperiecePage.accessOverviewTab();
			patientExperiecePage.validateOverviewTabData();
			patientExperiecePage.accessCompositeTab();
			patientExperiecePage.validateCompositeTabData();
			patientExperiecePage.accessSbsTab();
			patientExperiecePage.validateSbsTabData();
			patientExperiecePage.accessDemographicsTab();
			patientExperiecePage.validateDemographicsTabData();			
			*/
				
			System.out.println("Pase");
			System.out.println("Login: " + timer.getLoginTime());
			System.out.println("Icare1: " + timer.getIc1Time());
			System.out.println("Icare2: " + timer.getIc2Time());
			System.out.println("Overview: " + timer.getOverviewTime());
			System.out.println("Composite: " + timer.getCompositeTime());
			System.out.println("Export Composite: " + timer.getExportCompositeTime());
			System.out.println("Side By Side: " + timer.getSideBySideTime());
			System.out.println("Export Side By Side: " + timer.getExportSideBySideTime());
			System.out.println("Demographics: " + timer.getDemographicsTime());
			System.out.println("Export Demographics: " + timer.getExportDemographicsTime());
			
			
			
			
		/*
		 * Bloque de cierre
		 */
			//TODO: Clean up mess
			reportGenerator.wrapUpFile();
			driver.quit();
			
			
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


	/*
	 * Bloque de métodos
	 */

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
		
		/*
		private static boolean isElementPresent (String elementId) {
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
		
		
		private static void accessPanelFrame() throws InterruptedException {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(0);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("Panel_1_1"));
		}
		
		
		private static void assurePDLoaded() throws InterruptedException {
			int i = 0;
		    while (driver.findElement(By.id("loadingiframe")).getAttribute("style").contains("block")){
				Thread.sleep(1000);
				++i;
				if (i >= 200) throw new java.util.NoSuchElementException("PATIENT DEMOGRAPHIC IS TAKING TOO LONG TO LOAD (200 seconds elapsed");
			}
		}
		
		private static void runSearch(Element rootElement) throws NoSuchElementException, InterruptedException {
			
			//noDataAvailable = false;
			
			//setGlobalTab(rootElement); 
			//selectTab(tab);

			accessPanelFrame();
			completeReportLevel(rootElement);
			completeSurveySelection(rootElement);
			
			submitForm();
			Thread.sleep(5000);
		    //assurePDLoaded();
			//Thread.sleep(5000);

		}

*/
		
		/*
		 * LOCAL METHODS
		 */
		private static void firstRunTrigger() {
			if (!firstRun) {
				reportGenerator.addText(";;;;;");
				keepOverview = false;
			}
		}
		
		
}
