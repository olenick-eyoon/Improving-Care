package com.olenick.avatar.web.elements;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.exceptions.ElementNotLoadedException;
import com.olenick.avatar.web.containers.WebContainer;

/**
 * This is a home-made extension to WebElement. It has a mechanism to load the
 * page up to get the page element when.
 * <p>
 * Its thread-safety will depend on the thread-safety of
 * "waitForElementsToLoad()".
 * 
 * @see com.olenick.avatar.web.containers.WebContainer#waitForElementsToLoad()
 *      </p>
 */
public class ExtendedWebElement implements WebElement {
    private static final Logger log = LoggerFactory
            .getLogger(ExtendedWebElement.class);

    @Null
    protected final WebContainer container;
    @Null
    protected WebElement underlyingWebElement;

    public ExtendedWebElement(@Null final WebContainer container) {
        this.container = container;
    }

    public ExtendedWebElement(@Null final WebContainer container,
            @NotNull final WebElement element) {
        this.container = container;
        this.underlyingWebElement = element;
    }

    public WebElement getUnderlyingWebElement() {
        return this.underlyingWebElement;
    }

    public void setUnderlyingWebElement(@NotNull final WebElement element) {
        log.trace("setUnderlyingWebElement({})", element);
        if (element == null) {
            throw new ElementNotLoadedException();
        }
        this.underlyingWebElement = element;
    }

    public WebElement safeGetUnderlyingWebElement() {
        if (this.underlyingWebElement == null) {
            this.container.waitForElementsToLoad();
        }
        if (this.underlyingWebElement == null) {
            throw new ElementNotLoadedException();
        }
        return this.underlyingWebElement;
    }

    @Override
    public void click() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.click()", element);
        }
        element.click();
    }

    @Override
    public void submit() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.submit()", element);
        }
        element.submit();
    }

    @Override
    public void sendKeys(final CharSequence... keysToSend) {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.sendKeys({})", element, keysToSend);
        }
        this.safeGetUnderlyingWebElement().sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.clear()", element);
        }
        element.clear();
    }

    @Override
    public String getTagName() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.getTagName()", element);
        }
        return element.getTagName();
    }

    @Override
    public String getAttribute(final String name) {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.getAttribute({})", element, name);
        }
        return element.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.isSelected()", element);
        }
        return element.isSelected();
    }

    @Override
    public boolean isEnabled() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.isEnabled()", element);
        }
        return element.isEnabled();
    }

    @Override
    public String getText() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.getText()", element);
        }
        return element.getText();
    }

    @Override
    public List<WebElement> findElements(final By by) {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.findElements({})", element, by);
        }
        List<WebElement> originalElements = element.findElements(by);
        List<WebElement> elementsToReturn = new ArrayList<>(
                originalElements.size());
        for (WebElement elementToReturn : elementsToReturn) {
            elementsToReturn.add(new ExtendedWebElement(this.container,
                    elementToReturn));
        }
        return elementsToReturn;
    }

    @Override
    public WebElement findElement(final By by) {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.findElement({})", element, by);
        }
        return new ExtendedWebElement(this.container, element.findElement(by));
    }

    @Override
    public boolean isDisplayed() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.isDisplayed()", element);
        }
        return element.isDisplayed();
    }

    @Override
    public Point getLocation() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.getLocation()", element);
        }
        return element.getLocation();
    }

    @Override
    public Dimension getSize() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.getSize()", element);
        }
        return element.getSize();
    }

    @Override
    public String getCssValue(final String propertyName) {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.getCssValue()", element);
        }
        return element.getCssValue(propertyName);
    }

    public void tryScrollIntoView() {
        WebElement element = null;
        try {
            element = this.safeGetUnderlyingWebElement();
        } finally {
            log.trace("{}.tryScrollIntoView()", element);
        }
        if (this.container != null) {
            this.container.getDriver()
                    .scrollIntoView(this.underlyingWebElement);
        }
    }

    public void refreshAllElementsInContainer() {
        this.container.waitForElementsToLoad();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExtendedWebElement{");
        sb.append("container=").append(container);
        sb.append(", underlyingWebElement=").append(underlyingWebElement);
        sb.append('}');
        return sb.toString();
    }
}
