package com.olenick.avatar.web.containers;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Deprecated
@Ignore
public class OldLoginPageTest {

    WebDriver driver = new FirefoxDriver();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void openLoginPage() {
        OldLoginPage loginPage = new OldLoginPage(driver);
        loginPage.open(false);
        assertEquals("Intelligent Surveys Online Reporting", driver.getTitle());
    }

    @Test
    public void loginUser() throws InterruptedException {
        OldLoginPage loginPage = new OldLoginPage(driver);
        loginPage.open(false).login("dash@avatarsolutions.com", "password");
        assertEquals("Intelligent Surveys Online", driver.getTitle());
    }

}
