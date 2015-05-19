package com.olenick.avatar.web.containers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.model.report_values.ReportValues;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;

/**
 * Report graphs tab (e.g.: "Overview", "Composite/Item", "Side-by-Side",
 * "Demographics").
 */
public abstract class ReportGraphsTabIFrame<T extends ReportGraphsTabIFrame<T>>
        extends WelcomePageIFrame<T> {
    protected static final int TIMEOUT_SECS_GRAPH_ELEMENTS_PRESENT = 60;
    protected static final long TIMEOUT_SECS_EXPORT_ALL_TO_PDF_WINDOW_OPEN = 300;
    protected static final String ELEMENT_TAG_NO_DATA_INDICATOR = "center";
    protected static final String ELEMENT_TAG_RESULTS_TABLE = "table";
    protected static final String ELEMENT_TAG_RESULTS_CHART = "svg";
    protected static final String NO_DATA_INDICATOR = "center";
    protected static final String RESULTS_CHART = "chart";
    protected static final String RESULTS_TABLE = "table";
    private static final Logger log = LoggerFactory
            .getLogger(ReportGraphsTabIFrame.class);
    protected boolean dataAvailable;

    public ReportGraphsTabIFrame(@NotNull ExtendedRemoteWebDriver driver) {
        super(driver);
    }

    protected static ExpectedCondition<Map<String, WebElement>> graphElementsPresent() {
        return new ExpectedCondition<Map<String, WebElement>>() {
            @Override
            public Map<String, WebElement> apply(WebDriver driver) {
                try {
                    return graphElementsIfPresent((ExtendedRemoteWebDriver) driver);
                } catch (WebDriverException exception) {
                    log.warn("WebDriverException thrown by findElement",
                            exception);
                    throw exception;
                }
            }

            @Override
            public String toString() {
                return "graph elements located";
            }

            protected Map<String, WebElement> graphElementsIfPresent(
                    ExtendedRemoteWebDriver driver) {
                Map<String, WebElement> elements = new HashMap<>(3);
                this.safePutElement(driver, elements, NO_DATA_INDICATOR,
                        By.tagName(ELEMENT_TAG_NO_DATA_INDICATOR));
                this.safePutElement(driver, elements, RESULTS_CHART,
                        By.tagName(ELEMENT_TAG_RESULTS_CHART));
                this.safePutElement(driver, elements, RESULTS_TABLE,
                        By.tagName(ELEMENT_TAG_RESULTS_TABLE));
                if (elements.size() > 0) {
                    return elements;
                } else {
                    return null;
                }
            }

            protected void safePutElement(ExtendedRemoteWebDriver driver,
                    Map<String, WebElement> map, String elementSpecName,
                    By locator) {
                WebElement element = null;
                try {
                    element = driver.findElement(locator, 0);
                } catch (NoSuchElementException
                        | StaleElementReferenceException | TimeoutException ignored) {
                }
                if (element != null) {
                    map.put(elementSpecName, element);
                }
            }
        };
    }

    protected static ExpectedCondition<Boolean> moreThanOneWindowOpen() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.getWindowHandles().size() == 1 ? null
                        : Boolean.TRUE;
            }

            @Override
            public String toString() {
                return "more than one window open";
            }
        };
    }

    public abstract T exportToPDF();

    public abstract ReportValues getValues();

    protected void handlePDFNewWindow() {
        new WebDriverWait(this.getDriver(),
                TIMEOUT_SECS_EXPORT_ALL_TO_PDF_WINDOW_OPEN)
                .until(moreThanOneWindowOpen());
        driver.switchTo().window(
                driver.getWindowHandles().toArray()[1].toString());
        driver.close();
        driver.switchTo().window(
                driver.getWindowHandles().toArray()[0].toString());
    }

    protected void waitForResults() {
        this.dataAvailable = true;
        Map<String, WebElement> graphElements = new WebDriverWait(
                this.getDriver(), TIMEOUT_SECS_GRAPH_ELEMENTS_PRESENT)
                .until(graphElementsPresent());
        if (!graphElements.containsKey(RESULTS_TABLE)
                && !graphElements.containsKey(RESULTS_CHART)
                && graphElements.containsKey(NO_DATA_INDICATOR)) {
            this.dataAvailable = false;
        }
    }

    public T waitForElementsToLoad() {
        throw new RuntimeException("This should not have happened. "
                + "Either you didn't implement this method for the "
                + "corresponding child class or Java has done it again with "
                + "its static type inference.");
    }
}
