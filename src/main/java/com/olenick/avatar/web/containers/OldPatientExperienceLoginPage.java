package com.olenick.avatar.web.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * DONE WITH.
 */
@Deprecated
public class OldPatientExperienceLoginPage {
    private static final String ELEMENT_ID_USERNAME_INPUT = "SignonUserName";
    private static final String ELEMENT_ID_PASSWORD_INPUT = "SignonPassName";
    private static final String ELEMENT_ID_SUBMIT_BUTTON = "SignonbtnLogin";
    private static final String URL_ROOT_DEV = "http://172.16.20.210:8080/ibi_apps/signin";

    WebDriver driver;
    WebElement username, password, submit;

    public OldPatientExperienceLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public OldPatientExperienceLoginPage() {}

    public OldPatientExperienceLoginPage detectElements() {
        try {
            this.username = driver
                    .findElement(By.id(ELEMENT_ID_USERNAME_INPUT));
            this.password = driver
                    .findElement(By.id(ELEMENT_ID_PASSWORD_INPUT));
            this.submit = driver.findElement(By.id(ELEMENT_ID_SUBMIT_BUTTON));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * @return the username
     */
    public WebElement getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(WebElement username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public WebElement getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(WebElement password) {
        this.password = password;
    }

    /**
     * @return the submit
     */
    public WebElement getSubmit() {
        return submit;
    }

    /**
     * @param submit the submit to set
     */
    public void setSubmit(WebElement submit) {
        this.submit = submit;
    }

    public OldPatientExperienceLoginPage open(boolean dev) {
        if (dev) {
            driver.get(URL_ROOT_DEV);
        } else {
            driver.get("http://www.google.com");
        }
        return this;
    }

    public OldPatientExperienceLoginPage setUser(String user) {
        username.clear();
        username.sendKeys(user);
        return this;
    }

    public OldPatientExperienceLoginPage setPass(String pass) {
        password.clear();
        password.sendKeys(pass);
        return this;
    }

    public OldICare2Page login(String user, String pass) {
        setUser(user);
        setPass(pass);
        submit.click();
        OldICare2Page oldICare2Page = new OldICare2Page(driver, true);
        // PatientExperience patientExperiencePage = new
        // PatientExperience(driver, true);
        return oldICare2Page;
    }
}
