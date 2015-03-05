/**
 *  Login Page
 */
package com.olenick.avatar.web.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * WHERE IS THIS??
 * 
 * @author dpiriz
 */
@Deprecated
public class OldLoginPage {
    WebDriver driver;
    WebElement txtUser, txtPassword;
    String prodBaseUrl = "http://www.improvingcare.com";
    String devBaseUrl = "http://icare-dev";

    public OldLoginPage(WebDriver pDriver) {
        driver = pDriver;
    }

    public OldLoginPage open(boolean dev) {
        if (dev) {
            driver.get(devBaseUrl);
        } else {
            driver.get(prodBaseUrl);
        }
        detectWebElements();
        return this;
    }

    public OldLandingPage login(String user, String pass)
            throws InterruptedException {
        setUser(user);
        setPassword(pass);
        txtPassword.submit();
        return new OldLandingPage(driver, true);
    }

    private void detectWebElements() {
        txtUser = driver.findElement(By.id("userIDTF"));
        txtPassword = driver.findElement(By.id("passwordTF"));
    }

    private void setPassword(String pass) {
        txtPassword.clear();
        txtPassword.sendKeys(pass);
    }

    private void setUser(String user) {
        txtUser.clear();
        txtUser.sendKeys(user);
    }
}
