package com.olenick.avatar.web.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.olenick.avatar.exceptions.HomeLinkInvalid;
import com.olenick.avatar.exceptions.ICare2PageNotDisplayed;
import com.olenick.avatar.exceptions.PatientExperienceLinkInvalid;
import com.olenick.avatar.exceptions.SurveyControlCenterLinkInvalid;

/**
 * DONE WITH... BUT THERE IS SOME STUFF WITH THE WINDOWS THAT I DON'T KNOW WHAT
 * TO MAKE OF.
 */
@Deprecated
public class OldICare2Page {
    private static final String XPATH_MENU_BAR = "//div[@id='BiHBox-135']";
    WebElement home, patientExperience, surveyControlCenter;
    WebDriver driver;

    int i = 0;

    public OldICare2Page(WebDriver driver, boolean loaded) {
        this.driver = driver;

    }

    public OldICare2Page switchToNewWindow() throws InterruptedException,
            ICare2PageNotDisplayed {
        while (getWindows() == 2 && ++i > 60) {
            Thread.sleep(500);
        }

        if (i > 60)
            throw new ICare2PageNotDisplayed();

        driver.switchTo().window(
                driver.getWindowHandles().toArray()[1].toString());
        driver.manage().window().maximize();

        Thread.sleep(17000);

        return this;
    }

    public int getWindows() {
        return driver.getWindowHandles().size();
    }

    public OldICare2Page detectMenuBarItems() throws HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid,
            InterruptedException {
        Thread.sleep(45000);
        // assurePDLoaded();
        home = driver.findElement(By.xpath(XPATH_MENU_BAR + "/div[1]"));
        patientExperience = driver.findElement(By.xpath(XPATH_MENU_BAR
                + "/div[2]"));
        surveyControlCenter = driver.findElement(By.xpath(XPATH_MENU_BAR
                + "/div[3]"));
        if (home == null)
            throw new HomeLinkInvalid();
        if (patientExperience == null)
            throw new PatientExperienceLinkInvalid();
        if (surveyControlCenter == null)
            throw new SurveyControlCenterLinkInvalid();

        return this;
    }

    public OldICare2Page switchToMainIFrame() {
        WebDriverWait wait = new WebDriverWait(driver, 240);
        wait.until(ExpectedConditions
                .frameToBeAvailableAndSwitchToIt("main-iframe"));
        // driver.switchTo().frame("main-iframe");
        return this;
    }

    public OldICare2Page accessHomeTab() {
        home.click();
        return this;
    }

    public OldPatientExperiencePage accessPatientExperienceTab()
            throws InterruptedException {

        patientExperience.click();
        OldPatientExperiencePage oldPatientExperiencePage = new OldPatientExperiencePage(
                driver, true);

        return oldPatientExperiencePage;
    }

    public OldSurveyControlCenterPage accessSurveyControlCenterTab() {
        surveyControlCenter.click();
        OldSurveyControlCenterPage oldSurveyControlCenterPage = new OldSurveyControlCenterPage(
                driver, true);
        return oldSurveyControlCenterPage;
    }

    /*
     * private void assurePDLoaded() throws InterruptedException { int i = 0;
     * while
     * (driver.findElement(By.id("loadingiframe")).getAttribute("style").contains
     * ("block")){ Thread.sleep(1000); ++i; if (i >= 200) throw new
     * java.util.NoSuchElementException
     * ("PATIENT DEMOGRAPHIC IS TAKING TOO LONG TO LOAD (200 seconds elapsed");
     * } }
     */
}
