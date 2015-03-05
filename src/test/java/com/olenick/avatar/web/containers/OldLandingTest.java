package com.olenick.avatar.web.containers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Deprecated
@Ignore
public class OldLandingTest {

    WebDriver driver = new FirefoxDriver();
    OldLoginPage loginPage;
    OldICare2Page oldICare2Page;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void openLandingPage() throws InterruptedException {
        loginPage = new OldLoginPage(driver);
        loginPage.open(true).login("rferrari@avatarsolutions.com", "password");
        assertEquals("Intelligent Surveys Online", driver.getTitle());
    }

    @Test
    public void openEnhancedReports() throws InterruptedException {
        loginPage = new OldLoginPage(driver);
        oldICare2Page = loginPage.open(true)
                .login("rferrari@avatarsolutions.com", "password")
                .drillDownAdvancedReports().accessEnhancedReports();
        assertNotNull(oldICare2Page);
    }

}
