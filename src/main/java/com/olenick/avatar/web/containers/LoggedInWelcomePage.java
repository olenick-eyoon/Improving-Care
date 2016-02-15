package com.olenick.avatar.web.containers;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.olenick.selenium.containers.WebContainer;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;
import com.olenick.selenium.elements.ExtendedWebElement;

/**
 * Improving Care Welcome page when logged in.
 */
public class LoggedInWelcomePage extends WebContainer<LoggedInWelcomePage> {
    private static final long TIMEOUT_SWITCH_TO_FRAME = 240;
    private static final String ELEMENT_NAME_MAIN_IFRAME = "main-iframe";
    //TODO: Move this to properties file, before was ...135, now ...133
    private static final String XPATH_MENU_BAR = "//div[@id='BiHBox-133']";
    //private static final String XPATH_HOME_TAB = XPATH_MENU_BAR + "/div[1]";
    //private static final String XPATH_PATIENT_EXPERIENCE_TAB = XPATH_MENU_BAR + "/div[2]";
    //private static final String XPATH_CONTROL_CENTER_TAB = XPATH_MENU_BAR + "/div[3]";
    private static final String XPATH_HOME_TAB = "(//div[text()[contains(.,'HOME')]]/..)[position()=1]";
    private static final String XPATH_PATIENT_EXPERIENCE_TAB = "(//div[text()[contains(.,'PATIENT EXPERIENCE')]]/..)[position()=1]";
    private static final String XPATH_CONTROL_CENTER_TAB = "(//div[text()[contains(.,'CONTROL CENTER')]]/..)[position()=1]";
    private ExtendedWebElement homeTab, patientExperienceTab, controlCenterTab;
    private HomeIFrame homeFrame;
    private PatientExperienceIFrame patientExperienceFrame;
    private ControlCenterIFrame controlCenterFrame;

    public LoggedInWelcomePage(@NotNull ExtendedRemoteWebDriver driver) {
        super(driver);
        this.homeTab = new ExtendedWebElement(this);
        this.patientExperienceTab = new ExtendedWebElement(this);
        this.controlCenterTab = new ExtendedWebElement(this);
        this.homeFrame = new HomeIFrame(this.driver);
        this.patientExperienceFrame = new PatientExperienceIFrame(this.driver,
                this);
        this.controlCenterFrame = new ControlCenterIFrame(this.driver);
    }

    public HomeIFrame navigateToHomeTab() {
        //TODO: This is not working well
        //this.homeTab.click();
        this.homeTab.sendKeys(Keys.ENTER);
        return this.homeFrame;
    }

    public PatientExperienceIFrame navigateToPatientExperienceTab() {
        //TODO: This is not working well
        //this.patientExperienceTab.click();
        this.patientExperienceTab.sendKeys(Keys.ENTER);
        return this.patientExperienceFrame;
    }

    public ControlCenterIFrame navigateToControlCenterTab() {
        //TODO: This is not working well
        //this.controlCenterTab.click();
        this.controlCenterTab.sendKeys(Keys.ENTER);
        return this.controlCenterFrame;
    }

    public LoggedInWelcomePage switchToMainIFrame() {
        //TODO: Why is needed the following line?
        this.driver.switchTo().defaultContent();
        new WebDriverWait(this.driver, TIMEOUT_SWITCH_TO_FRAME)
                .until(ExpectedConditions
                        .frameToBeAvailableAndSwitchToIt(By.name(ELEMENT_NAME_MAIN_IFRAME)));
        return this;
    }

    @Override
    public LoggedInWelcomePage waitForElementsToLoad() {
        this.switchToMainIFrame();
        this.homeTab.setUnderlyingWebElement(this.driver.findElement(ExpectedConditions.elementToBeClickable(By
                .xpath(XPATH_HOME_TAB))));
        this.patientExperienceTab.setUnderlyingWebElement(this.driver
                .findElement(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_PATIENT_EXPERIENCE_TAB))));
        this.controlCenterTab.setUnderlyingWebElement(this.driver
                .findElement(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_CONTROL_CENTER_TAB))));
        return this;
    }
}
