package com.olenick.avatar.web.containers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.exceptions.ElementDiscoveryException;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;
import com.olenick.avatar.web.elements.ExtendedWebElement;

/**
 * Abstract Page class from which all Avatar pages inherit.
 * 
 * @param <T> The concrete page class.
 */
public abstract class WebContainer<T extends WebContainer> {
    private final static Logger log = LoggerFactory
            .getLogger(WebContainer.class);

    protected ExtendedRemoteWebDriver driver;

    /**
     * Made it private so it cannot be used.
     */
    @SuppressWarnings("unused")
    private WebContainer() {}

    public WebContainer(@NotNull ExtendedRemoteWebDriver driver) {
        this.driver = driver;
    }

    /**
     * This method is here for the loading of the whole page's elements, to
     * measure times.
     * 
     * @return This instance.
     */
    public abstract T waitForElementsToLoad();

    public ExtendedRemoteWebDriver getDriver() {
        return this.driver;
    }

    public T waitForPageComplete() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(this.driver, 30);
        wait.until(pageLoadCondition);
        return this.getCastedThis();
    }

    public T waitForReadyState(@NotNull final String readyState) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState").equals(readyState);
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
        return this.getCastedThis();
    }

    /**
     * Convenience method.
     *
     * @return ExtendedWebElementsSetter instance.
     */
    protected ExtendedWebElementsSetter setElements(
            ExtendedWebElement... elements) {
        return new ExtendedWebElementsSetter(this.driver, elements);
    }

    /**
     * Convenience method.
     * 
     * @return This instance, casted to the corresponding type.
     */
    @SuppressWarnings("unchecked")
    private T getCastedThis() {
        return (T) this;
    }

    protected static class ExtendedWebElementsSetter {
        private static final Boolean DEFAULT_VISIBILITY = Boolean.FALSE;
        private ExtendedRemoteWebDriver driver;
        private ExtendedWebElement[] elements;
        private Iterator<ExtendedWebElement> iterator;

        public ExtendedWebElementsSetter(ExtendedRemoteWebDriver driver,
                ExtendedWebElement[] elements) {
            this.driver = driver;
            this.elements = elements;
            this.iterator = Arrays.asList(this.elements).iterator();
        }

        public ExtendedWebElementsSetter byClassName(String... classNames) {
            return this
                    .by(DEFAULT_VISIBILITY, By.ByClassName.class, classNames);
        }

        public ExtendedWebElementsSetter byClassName(Boolean visible,
                String... classNames) {
            return this.by(visible, By.ByClassName.class, classNames);
        }

        public ExtendedWebElementsSetter byCssSelector(String... cssSelectors) {
            return this.by(DEFAULT_VISIBILITY, By.ByCssSelector.class,
                    cssSelectors);
        }

        public ExtendedWebElementsSetter byCssSelector(Boolean visible,
                String... cssSelectors) {
            return this.by(visible, By.ByCssSelector.class, cssSelectors);
        }

        public ExtendedWebElementsSetter byId(String... ids) {
            return this.by(DEFAULT_VISIBILITY, By.ById.class, ids);
        }

        public ExtendedWebElementsSetter byId(Boolean visible, String... ids) {
            return this.by(visible, By.ById.class, ids);
        }

        public ExtendedWebElementsSetter byLinkText(String... linkTexts) {
            return this.by(DEFAULT_VISIBILITY, By.ByLinkText.class, linkTexts);
        }

        public ExtendedWebElementsSetter byLinkText(Boolean visible,
                String... linkTexts) {
            return this.by(visible, By.ByLinkText.class, linkTexts);
        }

        public ExtendedWebElementsSetter byName(String... names) {
            return this.by(DEFAULT_VISIBILITY, By.ByName.class, names);
        }

        public ExtendedWebElementsSetter byName(Boolean visible,
                String... names) {
            return this.by(visible, By.ByName.class, names);
        }

        public ExtendedWebElementsSetter byPartialLinkText(
                String... partialLinkTexts) {
            return this.by(DEFAULT_VISIBILITY, By.ByPartialLinkText.class,
                    partialLinkTexts);
        }

        public ExtendedWebElementsSetter byPartialLinkText(Boolean visible,
                String... partialLinkTexts) {
            return this.by(visible, By.ByPartialLinkText.class,
                    partialLinkTexts);
        }

        public ExtendedWebElementsSetter byTagName(String... tagNames) {
            return this.by(DEFAULT_VISIBILITY, By.ByTagName.class, tagNames);
        }

        public ExtendedWebElementsSetter byTagName(Boolean visible,
                String... tagNames) {
            return this.by(visible, By.ByTagName.class, tagNames);
        }

        public ExtendedWebElementsSetter byXPath(String... xpaths) {
            return this.by(DEFAULT_VISIBILITY, By.ByXPath.class, xpaths);
        }

        public ExtendedWebElementsSetter byXPath(Boolean visible,
                String... xpaths) {
            return this.by(visible, By.ByXPath.class, xpaths);
        }

        public <T extends By> ExtendedWebElementsSetter by(Boolean visible,
                Class<T> byClass, String... parameters) {
            By[] bys = new By[parameters.length];
            Constructor<?> constructor;
            try {
                constructor = byClass.getConstructor(String.class);
            } catch (NoSuchMethodException exception) {
                throw new ElementDiscoveryException(exception);
            }
            int byIndex = 0;
            for (String parameter : parameters) {
                try {
                    bys[byIndex++] = (By) constructor.newInstance(parameter);
                } catch (IllegalAccessException | InstantiationException
                        | InvocationTargetException exception) {
                    throw new ElementDiscoveryException(exception);
                }
            }
            return this.by(visible, bys);
        }

        public ExtendedWebElementsSetter by(Boolean visible, By... bys) {
            for (By by : bys) {
                if (!this.iterator.hasNext()) {
                    throw new ElementDiscoveryException(
                            "Elements provided are less than locators.");
                }
                if (visible) {
                    ExpectedCondition<WebElement> expectedCondition = ExpectedConditions
                            .visibilityOfElementLocated(by);
                    WebElement underlyingElement = this.driver
                            .findElement(expectedCondition);
                    this.iterator.next().setUnderlyingWebElement(
                            underlyingElement);
                    log.trace("Underlying element: {}", underlyingElement);
                } else {
                    this.iterator.next().setUnderlyingWebElement(
                            this.driver.findElement(by));
                }
            }
            return this;
        }
    }
}
