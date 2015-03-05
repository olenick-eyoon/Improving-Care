package com.olenick.avatar.main;

public class Avatar_v3_0 {
    /*
     * // XML Declarations private static final String xmlFilePath =
     * "scenario-specs/"; // Webdriver Declarations public static WebDriver
     * driver = new FirefoxDriver(); public static
     * XMLPatientExperienceFeatureParser xmlPatientExperienceFeatureParser; //
     * Model objects public static ReportGenerator reportGenerator; public
     * static Timer timer = new Timer(); public static ErrorHandler errorHandler
     * = new ErrorHandler(); public static long startTime = 0, endTime = 0;
     * private static Document xmlFile; // Internal Variables private static
     * boolean firstRun = true; private static boolean keepOverview = false;
     * public static void main(String[] args) throws InterruptedException,
     * ICare2PageNotDisplayed, HomeLinkInvalid, PatientExperienceLinkInvalid,
     * SurveyControlCenterLinkInvalid, IOException, URISyntaxException,
     * ParseException { // Loads xml file from the command line arguments: if
     * (args.length < 1 || args[0] == null) { throw new
     * IllegalArgumentException("XML filename was not supplied"); } URL
     * xmlFileURL = Avatar_v3_0.class.getClassLoader().getResource( xmlFilePath
     * + args[0]); if (xmlFileURL == null) { throw new
     * IllegalArgumentException("XML file could not be opened"); }
     * xmlPatientExperienceFeatureParser = new
     * XMLPatientExperienceFeatureParser(); PatientExperienceFeature feature =
     * xmlPatientExperienceFeatureParser .parse(xmlFileURL);
     * PatientExperienceScenario useCase = feature.getScenarios().get(0);
     * initializeReportGeneratorObject(args);
     * errorHandler.initializePaths().setComputerName(
     * reportGenerator.getComputerName()); // Login - Note: To login through old
     * site, use timingLogin(args, root) // and timingIc1(root)
     * newTimingLogin(args, root); oldICare2Page =
     * peLogin.login(useCase.getUser(), useCase.getPassword()); // Actions in
     * IC1 - Note: To access through old site, use //
     * updateSystemAndOrganization(root), accessPatientExperience(). try {
     * timingPatientExperience(); PatientDemographics demographics =
     * useCase.getReportFilter() .getDemographics(); for (Element
     * patientDemographicElement : root .getChildren("patient-demographic")) {
     * firstRunTrigger();
     * patientExperiencePage.runSearch(patientDemographicElement);
     * timingOverview();
     * accessAndValidatePatientExperienceTabs(patientDemographicElement);
     * reportGenerator.addResults(xmlPatientExperienceUseCaseParser
     * .parse(patientDemographicElement), timer); System.out.println("Scenario "
     * + xmlPatientExperienceUseCaseParser .getName(patientDemographicElement) +
     * " completed..."); timer.resetTimer(); } } catch (NoSuchElementException
     * e) { handleExceptions(args, true, false, false, e); } catch
     * (StaleElementReferenceException e2) { handleExceptions(args, false, true,
     * false, e2); } catch (Exception e3) { handleExceptions(args, false, false,
     * true, e3); } // Closing cleanUpMess(); } private static void
     * handleExceptions(String[] args, boolean NSEEx, boolean SEEx, boolean OEx,
     * Exception e) throws IOException { checkLog(args);
     * errorHandler.takeScreenshot(driver); errorHandler.addEventToLog(NSEEx,
     * SEEx, OEx, e); } private static void
     * initializeReportGeneratorObject(String[] args) throws
     * FileNotFoundException, UnsupportedEncodingException, UnknownHostException
     * { reportGenerator = new ReportGenerator();
     * reportGenerator.createWriter("./resources/" +
     * reportGenerator.getComputerName() + "_" + args[0].replace(".xml", "") +
     * "_" + reportGenerator.getFileSuffixDate() + ".csv");
     * reportGenerator.addHeader(); } private static void cleanUpMess() {
     * reportGenerator.wrapUpFile(); errorHandler.wrapUpFile(); if (driver !=
     * null) driver.quit(); } private static void checkLog(String[] args) throws
     * FileNotFoundException, UnsupportedEncodingException { if
     * (errorHandler.getErrorLog() == null) {
     * errorHandler.setErrorTimeStamp(reportGenerator.getFileSuffixDate());
     * errorHandler.initializeLog(args); } } private static void
     * accessAndValidatePatientExperienceTabs( Element
     * patientDemographicElement) throws InterruptedException { for (String tab
     * : xmlPatientExperienceUseCaseParser .getTabs(patientDemographicElement))
     * { if (tab.equalsIgnoreCase("overview")) keepOverview = true;
     * accessAndValidateTab(patientDemographicElement, tab);
     * timingPdfExport(tab); } if (!keepOverview) timer.setOverviewTime(0); }
     * private static void timingPdfExport(String tab) throws
     * InterruptedException { startTime = timer.setStartTime();
     * oldPatientExperiencePagePage.exportToPDF(tab); endTime =
     * timer.setEndTime(); switch (tab) { case "composite":
     * timer.setExportCompositeTime(endTime - startTime); break; case
     * "side by side": timer.setExportSideBySideTime(endTime - startTime);
     * break; case "demographics": timer.setExportDemographicsTime(endTime -
     * startTime); break; } } private static void timingOverview() throws
     * InterruptedException { timer.setStartTime();
     * oldPatientExperiencePagePage.accessOverviewTab()
     * .validateOverviewTabData(); timer.setEndTime();
     * timer.setOverviewTime(endTime - startTime); } private static void
     * accessAndValidateTab(Element patientDemographicElement, String tab)
     * throws InterruptedException { startTime = timer.setStartTime();
     * oldPatientExperiencePagePage.accessAndValidateTab(tab); endTime =
     * timer.setEndTime(); switch (tab) { case "composite":
     * timer.setCompositeTime(endTime - startTime); break; case "side by side":
     * timer.setSideBySideTime(endTime - startTime); break; case "demographics":
     * timer.setDemographicsTime(endTime - startTime); break; } } private static
     * void newTimingLogin(String[] args, Element root) { startTime =
     * timer.setStartTime(); peLogin = new
     * OldPatientExperienceLoginPage(driver);
     * peLogin.open(defineEnvironment(args)).detectElements(); endTime =
     * timer.setEndTime(); timer.setLoginTime(endTime - startTime); } private
     * static boolean defineEnvironment(String[] args) { return
     * args[1].equalsIgnoreCase("qa"); } private static void
     * timingPatientExperience() throws HomeLinkInvalid,
     * PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
     * InterruptedException { startTime = timer.setStartTime();
     * oldICare2Page.switchToMainIFrame().detectMenuBarItems();
     * oldPatientExperiencePagePage = oldICare2Page
     * .accessPatientExperienceTab().detectOverviewTab()
     * .detectCompositeTab().detectDemographicTab().detectSbsTab()
     * .detectFilters().convertFiltersToSelect(); oldPatientExperiencePagePage =
     * oldPatientExperiencePagePage .accessOverviewTab();
     * oldPatientExperiencePagePage.validateOverviewTabData(); endTime =
     * timer.setEndTime(); timer.setIc2Time(endTime - startTime); } private
     * static void firstRunTrigger() throws UnknownHostException {
     * reportGenerator.addText(reportGenerator.generateTerminalData()); if
     * (firstRun) { reportGenerator.addText(timer.getLoginTime() + "," +
     * timer.getIc2Time() + ","); firstRun = false; } else {
     * reportGenerator.addText(",,"); keepOverview = false; } }
     */
}
