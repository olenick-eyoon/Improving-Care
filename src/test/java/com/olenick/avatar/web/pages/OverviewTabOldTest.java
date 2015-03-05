package com.olenick.avatar.web.pages;

import com.olenick.avatar.web.pages.OldICare2Page;
import com.olenick.avatar.web.pages.OldLandingPage;
import com.olenick.avatar.web.pages.OldLoginPage;
import com.olenick.avatar.web.pages.OldPatientExperiencePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Ignore
public class OverviewTabOldTest {
    OldLoginPage loginPage;
    OldLandingPage oldLandingPage;
    OldICare2Page oldICare2Page;
    OldPatientExperiencePage oldPatientExperiencePagePage;
    OldPatientExperiencePage overviewTab;

    WebDriver driver = new FirefoxDriver();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    // @Ignore
    @Test
    public void loadingSignPresent() throws Exception {
        loginPage = new OldLoginPage(driver);
        oldLandingPage = loginPage.open(true).login(
                "rferrari@avatarsolutions.com", "password");
        oldICare2Page = oldLandingPage.drillDownAdvancedReports()
                .accessEnhancedReports().switchToNewWindow()
                .switchToMainIFrame().detectMenuBarItems();
        oldPatientExperiencePagePage = oldICare2Page.accessPatientExperienceTab()
                .detectFilters();
        overviewTab = oldPatientExperiencePagePage.detectOverviewTab()
                .accessOverviewTab();
    }

    /*
     * private static void assurePDLoaded() throws InterruptedException { int i
     * = 0; while
     * (driver.findElement(By.id("loadingiframe")).getAttribute("style"
     * ).contains("block")){ Thread.sleep(1000); ++i; if (i >= 200) throw new
     * java.util.NoSuchElementException(
     * "PATIENT DEMOGRAPHIC IS TAKING TOO LONG TO LOAD (200 seconds elapsed"); }
     * }
     */
}
