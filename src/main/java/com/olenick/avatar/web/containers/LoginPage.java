package com.olenick.avatar.web.containers;

import org.openqa.selenium.By;

import com.olenick.selenium.containers.WebContainer;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;
import com.olenick.selenium.elements.ExtendedWebElement;

/**
 * ImprovingCare Login Page.
 * <p>
 * Not thread-safe.
 * </p>
 */
public class LoginPage extends WebContainer<LoginPage> {
    public static final String ELEMENT_ID_INVALID_CREDENTIALS_ERROR_MESSAGE = "InvalidSignonError503";
    private static final String ELEMENT_ID_PASSWORD_INPUT = "SignonPassName";
    private static final String ELEMENT_ID_SUBMIT_BUTTON = "SignonbtnLogin";
    private static final String ELEMENT_ID_USERNAME_INPUT = "SignonUserName";
    private static final String RELATIVE_URL = "/signin";

    private ExtendedWebElement usernameInputField, passwordInputField,
            submitButton;
    private String urlRoot;

    public LoginPage(ExtendedRemoteWebDriver driver, String urlRoot) {
        super(driver);
        this.driver = driver;
        this.urlRoot = urlRoot;
        this.usernameInputField = new ExtendedWebElement(this);
        this.passwordInputField = new ExtendedWebElement(this);
        this.submitButton = new ExtendedWebElement(this);
    }

    public LoginPage open() {
        this.driver.get(this.urlRoot + RELATIVE_URL);
        return this;
    }

    public LoginPage sendUsername(String username) {
        this.usernameInputField.clear();
        this.usernameInputField.sendKeys(username);
        return this;
    }

    public LoginPage sendPassword(String password) {
        this.passwordInputField.clear();
        this.passwordInputField.sendKeys(password);
        return this;
    }

    public LoginPage submit() {
        this.submitButton.click();
        return this;
    }

    public LoggedInWelcomePage login(String username, String password) {
        this.sendUsername(username);
        this.sendPassword(password);
        this.submit();
        return new LoggedInWelcomePage(this.driver);
    }

    public LoginPage loginFailure(String username, String password) {
        this.login(username, password);
        this.driver.findVisibleElement(By
                .id(ELEMENT_ID_INVALID_CREDENTIALS_ERROR_MESSAGE));
        return this;
    }

    @Override
    public LoginPage waitForElementsToLoad() {
        this.usernameInputField.setUnderlyingWebElement(driver.findElement(By
                .id(ELEMENT_ID_USERNAME_INPUT)));
        this.passwordInputField.setUnderlyingWebElement(driver.findElement(By
                .id(ELEMENT_ID_PASSWORD_INPUT)));
        this.submitButton.setUnderlyingWebElement(driver.findElement(By
                .id(ELEMENT_ID_SUBMIT_BUTTON)));
        return this;
    }
}
