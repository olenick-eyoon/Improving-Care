package com.olenick.avatar.web.elements;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.util.SafeArrays;
import com.olenick.avatar.web.containers.WebContainer;

/**
 * Select support for Avatar's weird implementation of drop-down multi-selects.
 */
public class AvatarMultiselectWebElement extends ExtendedSelectWebElement {
    private static final Logger log = LoggerFactory
            .getLogger(AvatarMultiselectWebElement.class);

    private static final String ATTRIBUTE_NAME_MULTI_SELECT = "multiselect";
    // private static final String ATTRIBUTE_VALUE_MULTI_SELECT = "multiple";

    private int actionsBeingExecuted = 0;

    public AvatarMultiselectWebElement(final WebContainer container) {
        super(container);
    }

    public AvatarMultiselectWebElement(final WebContainer container,
            WebElement element) {
        super(container, element);
    }

    @Override
    public boolean isMultiple() {
        Boolean multiple = null;
        try {
            multiple = this.getAttribute(ATTRIBUTE_NAME_MULTI_SELECT) != null;
        } finally {
            log.trace("isMultiple(): {}", multiple);
        }
        return multiple;
    }

    @Override
    public List<ExtendedWebElement> getOptions() {
        log.trace("getOptions()");
        this.beforeAction();
        List<ExtendedWebElement> options = super.getOptions();
        this.afterAction();
        return options;
    }

    @Override
    public List<ExtendedWebElement> getAllSelectedOptions() {
        log.trace("getAllSelectedOptions()");
        this.beforeAction();
        List<ExtendedWebElement> options = super.getAllSelectedOptions();
        this.afterAction();
        return options;
    }

    @Override
    public ExtendedWebElement getFirstSelectedOption() {
        log.trace("getFirstSelectedOption()");
        this.beforeAction();
        ExtendedWebElement option = super.getFirstSelectedOption();
        this.afterAction();
        return option;
    }

    @Override
    public void selectByVisibleText(final String... texts) {
        this.selectByVisibleText(false, texts);
    }

    public void selectByVisibleText(final boolean clearBefore,
            final String... texts) {
        this.selectByVisibleText(clearBefore, SafeArrays.asList(texts));
    }

    @Override
    public void selectByVisibleText(final List<String> texts) {
        this.selectByVisibleText(false, texts);
    }

    public void selectByVisibleText(final boolean clearBefore,
            final List<String> texts) {
        log.trace("selectByVisibleText({}, {})", clearBefore, texts);
        this.beforeAction();
        if (clearBefore) {
            super.deselectAll();
        }
        // super.selectByVisibleText(texts);
        this.selectOptionsBy(new AvatarSelect.ByVisibleText(), texts);
        this.afterAction();
    }

    @Override
    public void selectByIndex(final Integer... indexes) {
        this.selectByIndex(false, indexes);
    }

    public void selectByIndex(final boolean clearBefore,
            final Integer... indexes) {
        this.selectByIndex(clearBefore, SafeArrays.asList(indexes));
    }

    @Override
    public void selectByIndex(final List<Integer> indexes) {
        this.selectByIndex(false, indexes);
    }

    public void selectByIndex(final boolean clearBefore,
            final List<Integer> indexes) {
        log.trace("selectByIndex({}, {})", clearBefore, indexes);
        this.beforeAction();
        if (clearBefore) {
            super.deselectAll();
        }
        // super.selectByIndex(indexes);
        this.selectOptionsBy(new AvatarSelect.ByIndex(), indexes);
        this.afterAction();
    }

    @Override
    public void selectByValue(final String... values) {
        this.selectByValue(false, values);
    }

    public void selectByValue(final boolean clearBefore, final String... values) {
        this.selectByValue(clearBefore, SafeArrays.asList(values));
    }

    @Override
    public void selectByValue(final List<String> values) {
        this.selectByValue(false, values);
    }

    public void selectByValue(final boolean clearBefore,
            final List<String> values) {
        log.trace("selectByValue({}, {})", clearBefore, values);
        this.beforeAction();
        if (clearBefore) {
            super.deselectAll();
        }
        // super.selectByValue(values);
        this.selectOptionsBy(new AvatarSelect.ByValue(), values);
        this.afterAction();
    }

    @Override
    public void safeSelectByVisibleText(final String... texts) {
        this.safeSelectByVisibleText(false, texts);
    }

    public void safeSelectByVisibleText(final boolean clearBefore,
            final String... texts) {
        this.safeSelectByVisibleText(clearBefore, SafeArrays.asList(texts));
    }

    @Override
    public void safeSelectByVisibleText(final List<String> texts) {
        this.safeSelectByVisibleText(false, texts);
    }

    public void safeSelectByVisibleText(final boolean clearBefore,
            final List<String> texts) {
        log.trace("safeSelectByVisibleText({}, {})", clearBefore, texts);
        if (texts != null) {
            this.beforeAction();
            if (clearBefore) {
                super.deselectAll();
            }
            // super.safeSelectByVisibleText(texts);
            this.selectOptionsBy(new AvatarSelect.ByVisibleText(), texts);
            this.afterAction();
        }
    }

    @Override
    public void safeSelectByIndex(final Integer... indexes) {
        this.safeSelectByIndex(false, indexes);
    }

    public void safeSelectByIndex(final boolean clearBefore,
            final Integer... indexes) {
        this.safeSelectByIndex(clearBefore, SafeArrays.asList(indexes));
    }

    @Override
    public void safeSelectByIndex(final List<Integer> indexes) {
        this.safeSelectByIndex(false, indexes);
    }

    public void safeSelectByIndex(final boolean clearBefore,
            final List<Integer> indexes) {
        log.trace("safeSelectByIndex({}, {})", clearBefore, indexes);
        if (indexes != null) {
            this.beforeAction();
            if (clearBefore) {
                super.deselectAll();
            }
            // super.safeSelectByIndex(indexes);
            this.selectOptionsBy(new AvatarSelect.ByIndex(), indexes);
            this.afterAction();
        }
    }

    @Override
    public void safeSelectByValue(final String... values) {
        this.safeSelectByValue(false, values);
    }

    public void safeSelectByValue(final boolean clearBefore,
            final String... values) {
        this.safeSelectByValue(clearBefore, SafeArrays.asList(values));
    }

    @Override
    public void safeSelectByValue(final List<String> values) {
        this.safeSelectByValue(false, values);
    }

    public void safeSelectByValue(final boolean clearBefore,
            final List<String> values) {
        log.trace("safeSelectByValue({}, {})", clearBefore, values);
        if (values != null) {
            this.beforeAction();
            if (clearBefore) {
                super.deselectAll();
            }
            // super.safeSelectByValue(values);
            this.selectOptionsBy(new AvatarSelect.ByValue(), values);
            this.afterAction();
        }
    }

    @Override
    public void deselectAll() {
        log.trace("deselectAll()");
        this.beforeAction();
        super.deselectAll();
        this.afterAction();
    }

    @Override
    public void deselectByValue(final String... values) {
        this.deselectByValue(SafeArrays.asList(values));
    }

    @Override
    public void deselectByValue(final List<String> values) {
        log.trace("deselectByValue({})", values);
        this.beforeAction();
        super.deselectByValue(values);
        this.afterAction();
    }

    @Override
    public void deselectByIndex(final Integer... indexes) {
        this.deselectByIndex(SafeArrays.asList(indexes));
    }

    @Override
    public void deselectByIndex(final List<Integer> indexes) {
        log.trace("deselectByIndex({})", indexes);
        this.beforeAction();
        super.deselectByIndex(indexes);
        this.afterAction();
    }

    @Override
    public void deselectByVisibleText(final String... texts) {
        this.deselectByVisibleText(SafeArrays.asList(texts));
    }

    @Override
    public void deselectByVisibleText(final List<String> texts) {
        log.trace("deselectByVisibleText({})", texts);
        this.beforeAction();
        super.deselectByVisibleText(texts);
        this.afterAction();
    }

    @Override
    public void safeDeselectByValue(final String... values) {
        this.safeDeselectByValue(SafeArrays.asList(values));
    }

    @Override
    public void safeDeselectByValue(final List<String> values) {
        log.trace("safeDeselectByValue({})", values);
        if (values != null) {
            this.beforeAction();
            super.safeDeselectByValue(values);
            this.afterAction();
        }
    }

    @Override
    public void safeDeselectByIndex(final Integer... indexes) {
        this.safeDeselectByIndex(SafeArrays.asList(indexes));
    }

    @Override
    public void safeDeselectByIndex(final List<Integer> indexes) {
        log.trace("safeDeselectByIndex({})", indexes);
        if (indexes != null) {
            this.beforeAction();
            super.safeDeselectByIndex(indexes);
            this.afterAction();
        }
    }

    @Override
    public void safeDeselectByVisibleText(final String... texts) {
        this.safeDeselectByVisibleText(SafeArrays.asList(texts));
    }

    @Override
    public void safeDeselectByVisibleText(final List<String> texts) {
        log.trace("safeDeselectByVisibleText({})", texts);
        if (texts != null) {
            this.beforeAction();
            super.safeDeselectByVisibleText(texts);
            this.afterAction();
        }
    }

    /**
     * Convenience method. It would be better to do this with Java 8, passing a
     * method or so.
     */
    protected void beforeAction() {
        log.trace("beforeAction(): {}", this.actionsBeingExecuted);
        if (this.actionsBeingExecuted++ == 0) {
            // this.tryScrollIntoView();
            this.scrollUp();
            this.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            // this.tryScrollIntoView();
            this.scrollUp();
            if (this.isMultiple()) {
                this.expandSelect();
            }
        }
    }

    /**
     * Convenience method. It would be better to do this with Java 8, passing a
     * method or so.
     */
    protected void afterAction() {
        log.trace("afterAction(): {}", this.actionsBeingExecuted);
        if (--this.actionsBeingExecuted == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            WebElement underlyingElement = this.getUnderlyingWebElement();
            // this.tryScrollIntoView();
            this.scrollUp();
            if (this.isMultiple()) {
                this.revertSelectSize();
            }
            this.sendKeys(Keys.ENTER);
            this.container
                    .getDriver()
                    .executeScript(
                            "if (document.createEvent) {"
                                    + "var evObj = document.createEvent('MouseEvents');"
                                    + "evObj.initEvent('blur', true, false);"
                                    + "arguments[0].dispatchEvent(evObj);"
                                    + "} else if(document.createEventObject) { /* IE */ "
                                    + "var evObj = document.createEventObject();"
                                    + "arguments[0].fireEvent('onblur', evObj);"
                                    + "}", underlyingElement);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }
        }
    }

    protected void expandSelect() {
        if (this.isMultiple()) {
            this.container.getDriver().executeScript(
                    "arguments[0].setAttribute('size', '50')",
                    this.getUnderlyingWebElement());
        }
    }

    protected void revertSelectSize() {
        if (this.isMultiple()) {
            this.container.getDriver().executeScript(
                    "arguments[0].setAttribute('size', '5')",
                    this.getUnderlyingWebElement());
        }
    }

    /**
     * Convenience method.
     *
     * @return Select instance for this.
     */
    @Override
    protected Select safeGetSelect() {
        if (this.select == null) {
            this.select = new AvatarSelect(this.safeGetUnderlyingWebElement());
        }
        return this.select;
    }

    protected <T> void selectOptionsBy(
            final AvatarSelect.BySelection<T> bySelection, final List<T> options) {
        Select select = this.safeGetSelect();
        for (T option : options) {
            if (option != null) {
                this.expandSelect();
                log.trace("SELECTING OPTION {}: {}", bySelection, option);
                bySelection.select(select, option);
            }
        }
    }

    protected void scrollUp() {
        this.container.getDriver().executeScript(
                "document.getElementsByTagName('html')[0].scrollIntoView();");
    }

    protected static class AvatarSelect extends Select {
        private boolean multiple = false;

        public AvatarSelect(final WebElement element) {
            super(element);
            this.multiple = element.getAttribute(ATTRIBUTE_NAME_MULTI_SELECT) != null;
        }

        public boolean isMultiple() {
            return this.multiple;
        }

        public <T> void selectBy(final BySelection<T> by, final T option) {
            by.select(this, option);
        }

        public abstract static class BySelection<T> {
            public abstract void select(final Select select, final T option);

            public abstract String toString();
        }

        public static class ByIndex extends BySelection<Integer> {
            public void select(final Select select, final Integer option) {
                select.selectByIndex(option);
            }

            @Override
            public String toString() {
                return "BY INDEX";
            }
        }

        public static class ByValue extends BySelection<String> {
            public void select(final Select select, final String option) {
                select.selectByValue(option);
            }

            @Override
            public String toString() {
                return "BY VALUE";
            }
        }

        public static class ByVisibleText extends BySelection<String> {
            public void select(final Select select, final String option) {
                select.selectByVisibleText(option);
            }

            @Override
            public String toString() {
                return "BY VISIBLE TEXT";
            }
        }
    }
}
