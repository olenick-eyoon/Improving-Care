package com.olenick.avatar.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByCssSelector;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.internal.FindsByLinkText;
import org.openqa.selenium.internal.FindsByName;
import org.openqa.selenium.internal.FindsByTagName;
import org.openqa.selenium.internal.FindsByXPath;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension to the normal RemoteWebDrivers provided by Selenium.
 * <p>
 * When finding an element, please be mindful of the following:
 * 
 * @see: http://docs.seleniumhq.org/docs/04_webdriver_advanced.jsp
 *       </p>
 */
public class ExtendedRemoteWebDriver implements WebDriver, WrapsDriver,
        JavascriptExecutor, FindsById, FindsByClassName, FindsByLinkText,
        FindsByName, FindsByCssSelector, FindsByTagName, FindsByXPath,
        HasInputDevices, HasCapabilities, TakesScreenshot {
    private static final Logger log = LoggerFactory
            .getLogger(ExtendedRemoteWebDriver.class);

    // TODO: Move this to configuration
    private static final long DEFAULT_TIMEOUT_IN_SECONDS = 60;

    private RemoteWebDriver underlyingDriver;

    public ExtendedRemoteWebDriver(RemoteWebDriver driver) {
        this.underlyingDriver = driver;
    }

    @Override
    public void get(String url) {
        log.trace("get({})", url);
        this.underlyingDriver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        log.trace("getCurrentUrl({})");
        return this.underlyingDriver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        log.trace("getTitle({})");
        return this.underlyingDriver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return this.findElements(by, DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public List<WebElement> findElements(By by, long timeoutInSeconds) {
        log.trace("findElements({}, {})", by, timeoutInSeconds);
        return this.findElements(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by),
                timeoutInSeconds);
    }

    public List<WebElement> findElements(ExpectedCondition<?> expectedCondition) {
        return this.findElements(expectedCondition, DEFAULT_TIMEOUT_IN_SECONDS);
    }

    @SuppressWarnings("unchecked")
    public List<WebElement> findElements(
            ExpectedCondition<?> expectedCondition, long timeoutInSeconds) {
        log.trace("findElements({}, {})", expectedCondition, timeoutInSeconds);
        return (List<WebElement>) new WebDriverWait(this.underlyingDriver,
                timeoutInSeconds).until(expectedCondition);
    }

    @Override
    public WebElement findElement(By by) {
        return this.findElement(by, DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public WebElement findElement(By by, long timeoutInSeconds) {
        return this.findElement(
                ExpectedConditions.presenceOfElementLocated(by),
                timeoutInSeconds);
    }

    public WebElement findElement(ExpectedCondition<?> expectedCondition) {
        return this.findElement(expectedCondition, DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public WebElement findElement(ExpectedCondition<?> expectedCondition,
            long timeoutInSeconds) {
        log.trace("findElement({}, {})", expectedCondition, timeoutInSeconds);
        return (WebElement) new WebDriverWait(this.underlyingDriver,
                timeoutInSeconds).until(expectedCondition);
    }

    public WebElement findVisibleElement(By by) {
        return this.findVisibleElement(by, DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public WebElement findVisibleElement(By by, long timeoutInSeconds) {
        return this.findElement(
                ExpectedConditions.visibilityOfElementLocated(by),
                timeoutInSeconds);
    }

    @Override
    public String getPageSource() {
        log.trace("getPageSource()");
        return this.underlyingDriver.getPageSource();
    }

    @Override
    public void close() {
        log.trace("close()");
        this.underlyingDriver.close();
    }

    @Override
    public void quit() {
        log.trace("quit()");
        this.underlyingDriver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        log.trace("getWindowHandles()");
        return this.underlyingDriver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        log.trace("getWindowHandle()");
        return this.underlyingDriver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        log.trace("switchTo()");
        return this.underlyingDriver.switchTo();
    }

    @Override
    public Navigation navigate() {
        log.trace("navigate()");
        return this.underlyingDriver.navigate();
    }

    @Override
    public Options manage() {
        log.trace("manage()");
        return this.underlyingDriver.manage();
    }

    @Override
    public WebElement findElementByClassName(String using) {
        return this.findElement(By.className(using));
    }

    @Override
    public List<WebElement> findElementsByClassName(String using) {
        return this.findElements(By.className(using));
    }

    @Override
    public WebElement findElementByCssSelector(String using) {
        return this.findElement(By.cssSelector(using));
    }

    @Override
    public List<WebElement> findElementsByCssSelector(String using) {
        return this.findElements(By.cssSelector(using));
    }

    @Override
    public WebElement findElementById(String using) {
        return this.findElement(By.id(using));
    }

    @Override
    public List<WebElement> findElementsById(String using) {
        return this.findElements(By.id(using));
    }

    @Override
    public WebElement findElementByLinkText(String using) {
        return this.findElement(By.linkText(using));
    }

    @Override
    public List<WebElement> findElementsByLinkText(String using) {
        return this.findElements(By.linkText(using));
    }

    @Override
    public WebElement findElementByPartialLinkText(String using) {
        return this.findElement(By.partialLinkText(using));
    }

    @Override
    public List<WebElement> findElementsByPartialLinkText(String using) {
        return this.findElements(By.partialLinkText(using));
    }

    @Override
    public WebElement findElementByName(String using) {
        return this.findElement(By.name(using));
    }

    @Override
    public List<WebElement> findElementsByName(String using) {
        return this.findElements(By.name(using));
    }

    @Override
    public WebElement findElementByTagName(String using) {
        return this.findElement(By.tagName(using));
    }

    @Override
    public List<WebElement> findElementsByTagName(String using) {
        return this.findElements(By.tagName(using));
    }

    @Override
    public WebElement findElementByXPath(String using) {
        return this.findElement(By.xpath(using));
    }

    @Override
    public List<WebElement> findElementsByXPath(String using) {
        return this.findElements(By.xpath(using));
    }

    @Override
    public Capabilities getCapabilities() {
        log.trace("getCapabilities()");
        return this.underlyingDriver.getCapabilities();
    }

    @Override
    public Keyboard getKeyboard() {
        log.trace("getKeyboard()");
        return this.underlyingDriver.getKeyboard();
    }

    @Override
    public Mouse getMouse() {
        log.trace("getMouse()");
        return this.underlyingDriver.getMouse();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        log.trace("executeScript({}, {})", script, args);
        return this.underlyingDriver.executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        log.trace("executeAsyncScript({}, {})", script, args);
        return this.underlyingDriver.executeAsyncScript(script, args);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target)
            throws WebDriverException {
        log.trace("getScreenshotAs({})", target);
        return this.underlyingDriver.getScreenshotAs(target);
    }

    public File takeScreenshot(String filename) throws IOException {
        return this.takeScreenshot(new File(filename));
    }

    public File takeScreenshot(File outputScreenshot) throws IOException {
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(outputScreenshot);
            stream.write(this.getScreenshotAs(OutputType.BYTES));
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException exception) {
                    // Nothing sane to do
                }
            }
        }
        return outputScreenshot;
    }

    @Override
    public WebDriver getWrappedDriver() {
        log.trace("getWrappedDriver()");
        return this.underlyingDriver;
    }

    public void scrollIntoView(WebElement element) {
        log.trace("scrollIntoView({})", element);
        this.executeScript("arguments[0].scrollIntoView();", element);
    }
}
