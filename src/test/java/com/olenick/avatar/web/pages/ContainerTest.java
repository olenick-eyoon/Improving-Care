package com.olenick.avatar.web.pages;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.avatar.web.ExtendedRemoteWebDriver;

public class ContainerTest {
    protected ExtendedRemoteWebDriver driver;

    @Before
    public void setUp() {
        this.driver = new ExtendedRemoteWebDriver(new FirefoxDriver());
    }

    @After
    public void tearDown() {
        this.driver.quit();
    }
}
