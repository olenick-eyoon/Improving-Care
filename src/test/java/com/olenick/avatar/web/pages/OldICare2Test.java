package com.olenick.avatar.web.pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.avatar.exceptions.HomeLinkInvalid;
import com.olenick.avatar.exceptions.ICare2PageNotDisplayed;
import com.olenick.avatar.exceptions.PatientExperienceLinkInvalid;
import com.olenick.avatar.exceptions.SurveyControlCenterLinkInvalid;

@Deprecated
@Ignore
public class OldICare2Test {

    WebDriver driver = new FirefoxDriver();
    OldICare2Page oldICare2Page;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    // @Ignore
    @Test
    public void createIcare2Page() {
        oldICare2Page = new OldICare2Page(driver, false);
        assertNotNull(oldICare2Page);
    }

    // @Ignore
    @Test
    public void verifyTitle() throws InterruptedException,
            ICare2PageNotDisplayed {
        oldICare2Page = new OldLoginPage(driver).open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow();

        assertEquals("iCare Enhanced Platform", driver.getTitle());
    }

    // @Ignore
    @Test
    public void findMenuBar() throws InterruptedException,
            ICare2PageNotDisplayed, HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
        oldICare2Page = new OldLoginPage(driver).open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow();
        oldICare2Page.switchToMainIFrame().detectMenuBarItems();

    }

    // @Ignore
    @Test
    public void accessHomeTab() throws InterruptedException,
            ICare2PageNotDisplayed, HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
        oldICare2Page = new OldLoginPage(driver).open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
        if (oldICare2Page == null)
            fail("Some of the menu items are null");
        oldICare2Page.accessHomeTab();

    }

    @Test
    public void accessPatientExperienceTab() throws InterruptedException,
            ICare2PageNotDisplayed, HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
        oldICare2Page = new OldLoginPage(driver).open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
        if (oldICare2Page == null) {
            System.out.println("No detecte algo");
            fail("Some if the menu items are null");
        } else {
            oldICare2Page.accessPatientExperienceTab();
        }

    }

    @Test
    public void accessSurveyControlCenterTab() throws InterruptedException,
            ICare2PageNotDisplayed, HomeLinkInvalid,
            PatientExperienceLinkInvalid, SurveyControlCenterLinkInvalid {
        oldICare2Page = new OldLoginPage(driver).open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports()
                .switchToNewWindow().switchToMainIFrame().detectMenuBarItems();
        if (oldICare2Page == null) {
            System.out.println("No detecte algo");
            fail("Some if the menu items are null");
        } else {
            oldICare2Page.accessSurveyControlCenterTab();
        }
    }
}
