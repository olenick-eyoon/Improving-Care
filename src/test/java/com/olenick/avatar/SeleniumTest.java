package com.olenick.avatar;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;

public class SeleniumTest {
    protected static final String URL_ROOT_DEV = "http://172.16.20.210:8080/ibi_apps";
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
