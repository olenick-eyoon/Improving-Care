package com.olenick.avatar.web.pages;

import static org.junit.Assert.assertNotNull;

import com.olenick.avatar.web.pages.OldPatientExperienceLoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Ignore
public class OldPatientExperienceLoginTest {

    WebDriver driver;
    OldPatientExperienceLoginPage peLoginPage = new OldPatientExperienceLoginPage();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void LoginPageObject() {
        assertNotNull(peLoginPage);
    }

    @Test
    public void openPeLoginPage() {
        driver = new FirefoxDriver();
        peLoginPage = new OldPatientExperienceLoginPage(driver);
        peLoginPage.open(true);
    }

    @Test
    public void detectElements() {
        driver = new FirefoxDriver();
        peLoginPage = new OldPatientExperienceLoginPage(driver);
        peLoginPage.open(true).detectElements();
    }

}
