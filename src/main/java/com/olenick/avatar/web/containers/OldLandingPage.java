package com.olenick.avatar.web.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

@Deprecated
public class OldLandingPage {
    // Menu Bar
    public WebElement home, orgReports, advancedReports;
    WebDriver driver;
    // Home Items
    WebElement selectOrganizationOrSystem;
    Select cmbOrganization, cmbSystem;

    // Org Reports Items
    WebElement matrixReport;

    // Advanced Reports Items
    WebElement customReport, enhancedPlatform;

    public OldLandingPage(WebDriver pDriver, boolean loaded) {
        driver = pDriver;
        if (loaded)
            detectLandingWebElements();

    }

    private void detectLandingWebElements() {
        // Menu Bar
        home = driver.findElement(By.linkText("Home"));
        orgReports = driver.findElement(By.linkText("Org Reports"));
        advancedReports = driver.findElement(By.linkText("Advanced Reports"));

        if (home == null || orgReports == null || advancedReports == null)
            System.out.println("Algo es null");
    }

    public OldLandingPage detectHomeSubMenuItems() {
        selectOrganizationOrSystem = driver.findElement(By
                .linkText("Select Organization or System"));
        return this;
    }

    public OldLandingPage detectSelectOrgWebElements()
            throws InterruptedException {
        Thread.sleep(1500);
        cmbOrganization = new Select(driver.findElement(By.id("org_id")));
        cmbSystem = new Select(driver.findElement(By.id("ol_id")));
        return this;
    }

    public void detectAdvReportsWebElements() {
        enhancedPlatform = driver.findElement(By.linkText("Enhanced Platform"));
        customReport = driver.findElement(By.linkText("Custom Report"));
    }

    public OldLandingPage drillDownHome() {
        home.click();
        detectHomeSubMenuItems();
        return this;
    }

    public OldLandingPage openOrgOrSystemSelection()
            throws InterruptedException {
        selectOrganizationOrSystem.click();
        detectSelectOrgWebElements();
        return this;
    }

    public OldLandingPage setOrganization(String organizationId) {
        cmbOrganization.selectByValue(organizationId);
        return this;
    }

    public OldLandingPage setSystem(String systemId) {
        cmbSystem.selectByValue(systemId);
        return this;
    }

    public OldLandingPage submitOrgOrSystem() throws InterruptedException {
        driver.findElement(By.xpath("//div[@id='selectbtn']/input")).click();
        Thread.sleep(1500);
        return this;
    }

    public OldLandingPage drillDownAdvancedReports() {
        advancedReports.click();
        detectAdvReportsWebElements();
        return this;
    }

    public OldICare2Page accessEnhancedReports() throws InterruptedException {
        enhancedPlatform.click();
        Thread.sleep(5000);
        OldICare2Page oldICare2Page = new OldICare2Page(driver, true);
        return oldICare2Page;
    }

}
