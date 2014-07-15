package com.olenick.avatar.main;

import org.jdom2.Document;
import org.jdom2.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

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

		
	public static void main(String[] args) throws InterruptedException, ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
		
		/*
		 * Bloque de ejecución
		 */
		
	    	xmlFile = xmlParser.loadFile(xmlFilePath + args[0]); //Loads xml file from the command line arguments
			Element root = xmlFile.getRootElement(); //Generates the root element we will use during execution
			
			//TODO: Inicialización del String Builder
			
		/*
		 * Bloque de login
		 */
			loginPage = new Login(driver);
			landingPage = loginPage.open(defineEnvironment(args)).login(root.getChildTextTrim("user"), root.getChildTextTrim("password"));
			
			
		/*
		 * Bloque de acciones en IC1
		 */
			
			//TODO: TRY CATCH
			//writerStartup(args); //Initializes the PrintWriter object
			landingPage.drillDownHome().openOrgOrSystemSelection();
			selectSystemAndOrganization(root);
			landingPage = new Landing(driver, true);
			iCare2Page = landingPage.drillDownAdvancedReports().accessEnhancedReports().switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
			patientExperiecePage = iCare2Page.accessPatientExperienceTab().detectOverviewTab().detectCompositeTab().detectDemographicTab().detectSbsTab();
			
			//patientExperiecePage.accessOverviewTab();
			//patientExperiecePage.validateOverviewTabData();
			//patientExperiecePage.accessCompositeTab();
			//patientExperiecePage.validateCompositeTabData();
			//patientExperiecePage.accessSbsTab();
			//patientExperiecePage.validateSbsTabData();
			//patientExperiecePage.accessDemographicsTab();
			//patientExperiecePage.validateDemographicsTabData();			
			
				
			System.out.println("Pase");
		
			
			
		/*
		 * Bloque de cierre
		 */
			//TODO: Clean up mess
			driver.quit();
			
			
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
}
