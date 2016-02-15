package com.olenick.avatar.web.containers;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.selenium.containers.WebContainer;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;

/**
 * Welcome page iframe.
 */
public abstract class WelcomePageIFrame<T extends WebContainer<T>> extends
        WebContainer<T> {
    private static final Logger log = LoggerFactory
            .getLogger(WelcomePageIFrame.class);

    private static final long TIMEOUT_SECS_LOADING_IFRAME = 300;
    private static final long TIMEOUT_SECS_SWITCH_TO_FRAME = 300;

    //TODO: this final var is defined also in LoggedInWelcomePage, unify this
    protected static final String ELEMENT_NAME_MAIN_IFRAME = "main-iframe";
    protected static final String ELEMENT_NAME_PANEL_IFRAME = "Panel_1_1";

    protected WebDriverWait waitForIFrame;

    public WelcomePageIFrame(@NotNull ExtendedRemoteWebDriver driver) {
        super(driver);

        this.waitForIFrame = new WebDriverWait(this.driver,
                TIMEOUT_SECS_SWITCH_TO_FRAME);
    }

    protected static ExpectedCondition<WebElement> styleNotBlock(
            final By locator) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    return elementIfStyleNotBlock(driver.findElement(locator));
                } catch (NoSuchElementException
                        | StaleElementReferenceException | TimeoutException exception) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "element located by " + locator
                        + " does not have 'block' as part of its style";
                // ??? dunno what to write here.
            }

            protected WebElement elementIfStyleNotBlock(WebElement element) {
                return element.getAttribute("style").contains("block") ? null
                        : element;
            }
        };
    }

    public T accessPanelFrame() {
        this.driver.switchTo().defaultContent();
        this.switchToFrame(ELEMENT_NAME_MAIN_IFRAME);
        this.switchToFrame(ELEMENT_NAME_PANEL_IFRAME);

        return this.castThis();
    }

    protected void ensureIFrameLoaded() {
        this.driver.findElement(styleNotBlock(By.id("loadingiframe")),
                TIMEOUT_SECS_LOADING_IFRAME);
    }

    protected T scrollUp() {
        this.getDriver().executeScript(
                "document.getElementsByTagName('html')[0].scrollIntoView();");
        return this.castThis();
    }

    protected T switchToFrame(String frameLocator) {
        log.trace("switchToFrame(" + frameLocator + ")");
        this.waitForIFrame.until(ExpectedConditions
                .frameToBeAvailableAndSwitchToIt(frameLocator));
        return this.castThis();
    }

    @SuppressWarnings("unchecked")
    private T castThis() {
        return (T) this;
    }
}
