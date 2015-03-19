package com.olenick.avatar.web.elements;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.model.Valued;
import com.olenick.avatar.util.SafeArrays;
import com.olenick.avatar.web.containers.WebContainer;

/**
 * Extended Web Element that has Select capabilities.
 * 
 * @see Select
 */
public class ExtendedSelectWebElement extends ExtendedWebElement {
    private static final Logger log = LoggerFactory
            .getLogger(ExtendedSelectWebElement.class);

    protected Select select;

    public ExtendedSelectWebElement(@Null final WebContainer container) {
        super(container);
    }

    public ExtendedSelectWebElement(@Null final WebContainer container,
            @NotNull final WebElement element) {
        super(container, element);
    }

    /**
     * @return Whether this select element support selecting multiple options at
     *         the same time? This is done by checking the value of the
     *         "multiple" attribute.
     */
    public boolean isMultiple() {
        Boolean multiple = null;
        try {
            multiple = this.safeGetSelect().isMultiple();
        } finally {
            log.trace("isMultiple(): {}", multiple);
        }
        return multiple;
    }

    /**
     * @return All options belonging to this select tag
     */
    public List<ExtendedWebElement> getOptions() {
        log.trace("getOptions()");
        List<WebElement> options = this.safeGetSelect().getOptions();
        List<ExtendedWebElement> result = new ArrayList<>(options.size());
        for (WebElement option : options) {
            result.add(new ExtendedWebElement(this.container, option));
        }
        return result;
    }

    /**
     * @return All selected options belonging to this select tag
     */
    public List<ExtendedWebElement> getAllSelectedOptions() {
        log.trace("getAllSelectedOptions()");
        List<WebElement> selectedOptions = this.safeGetSelect()
                .getAllSelectedOptions();
        List<ExtendedWebElement> result = new ArrayList<>(
                selectedOptions.size());
        for (WebElement selectedOption : selectedOptions) {
            result.add(new ExtendedWebElement(this.container, selectedOption));
        }
        return result;
    }

    /**
     * @return The first selected option in this select tag (or the currently
     *         selected option in a normal select)
     */
    public ExtendedWebElement getFirstSelectedOption() {
        log.trace("getFirstSelectedOption()");
        return new ExtendedWebElement(this.container, this.safeGetSelect()
                .getFirstSelectedOption());
    }

    /**
     * Select all options that display text matching the individual arguments.
     * That is, when given "Bar" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param texts The visible texts to match against
     */
    public void selectByVisibleText(String... texts) {
        this.selectByVisibleText(SafeArrays.asList(texts));
    }

    /**
     * Select all options that display text matching the individual arguments.
     * That is, when given "Bar" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param texts The visible texts to match against
     */
    public void selectByVisibleText(List<String> texts) {
        log.trace("selectByVisibleText({})", texts);
        Select select = this.safeGetSelect();
        for (String text : texts) {
            select.selectByVisibleText(text);
        }
    }

    /**
     * Select the options at the given indexes. This is done by examining the
     * "index" attribute of the elements, and not merely by counting.
     *
     * @param indexes The options at these indexes will be selected
     */
    public void selectByIndex(Integer... indexes) {
        this.selectByIndex(SafeArrays.asList(indexes));
    }

    /**
     * Select the options at the given indexes. This is done by examining the
     * "index" attribute of the elements, and not merely by counting.
     *
     * @param indexes The options at these indexes will be selected
     */
    public void selectByIndex(List<Integer> indexes) {
        log.trace("selectByIndex({})", indexes);
        Select select = this.safeGetSelect();
        for (int index : indexes) {
            select.selectByIndex(index);
        }
    }

    /**
     * Select all options that have a value matching the individual arguments.
     * That is, when given "foo" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param values The values to match against
     */
    public void selectByValue(String... values) {
        this.selectByValue(SafeArrays.asList(values));
    }

    /**
     * Select all options that have a value matching the individual arguments.
     * That is, when given "foo" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param values The values to match against
     */
    public void selectByValue(List<String> values) {
        log.trace("selectByValue({})", values);
        Select select = this.safeGetSelect();
        for (String value : values) {
            select.selectByValue(value);
        }
    }

    /**
     * Select all options that have a value matching the individual arguments.
     * That is, when given "foo" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param valued The values to match against
     */
    public void selectByValue(Valued<String>... valued) {
        log.trace("selectByValue({})", valued);
        Select select = this.safeGetSelect();
        for (Valued<String> aValued : valued) {
            select.selectByValue(aValued.getValue());
        }
    }

    /**
     * Select all options that display text matching the individual arguments.
     * That is, when given "Bar" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param texts The visible texts to match against
     */
    public void safeSelectByVisibleText(String... texts) {
        this.safeSelectByVisibleText(SafeArrays.asList(texts));
    }

    /**
     * Select all options that display text matching the individual arguments.
     * That is, when given "Bar" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param texts The visible texts to match against
     */
    public void safeSelectByVisibleText(List<String> texts) {
        log.trace("safeSelectByVisibleText({})", texts);
        if (texts != null) {
            Select select = this.safeGetSelect();
            for (String text : texts) {
                if (text != null) {
                    select.selectByVisibleText(text);
                }
            }
        }
    }

    /**
     * Select the options at the given indexes. This is done by examining the
     * "index" attribute of the elements, and not merely by counting.
     *
     * @param indexes The options at these indexes will be selected
     */
    public void safeSelectByIndex(Integer... indexes) {
        this.safeSelectByIndex(SafeArrays.asList(indexes));
    }

    /**
     * Select the options at the given indexes. This is done by examining the
     * "index" attribute of the elements, and not merely by counting.
     *
     * @param indexes The options at these indexes will be selected
     */
    public void safeSelectByIndex(List<Integer> indexes) {
        log.trace("safeSelectByIndex({})", indexes);
        if (indexes != null) {
            Select select = this.safeGetSelect();
            for (Integer index : indexes) {
                if (index != null) {
                    select.selectByIndex(index);
                }
            }
        }
    }

    /**
     * Select all options that have a value matching the individual arguments.
     * That is, when given "foo" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param values The values to match against
     */
    public void safeSelectByValue(String... values) {
        this.safeSelectByValue(SafeArrays.asList(values));
    }

    /**
     * Select all options that have a value matching the individual arguments.
     * That is, when given "foo" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param values The values to match against
     */
    public void safeSelectByValue(List<String> values) {
        log.trace("safeSelectByValue({})", values);
        if (values != null) {
            Select select = this.safeGetSelect();
            for (String value : values) {
                if (value != null) {
                    select.selectByValue(value);
                }
            }
        }
    }

    /**
     * Select all options that have a value matching the individual arguments.
     * That is, when given "foo" this would select an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param valued The values to match against
     */
    public void safeSelectByValue(Valued<String>... valued) {
        log.trace("safeSelectByValue({})", valued);
        if (valued != null) {
            Select select = this.safeGetSelect();
            for (Valued<String> aValued : valued) {
                if (aValued != null) {
                    select.selectByValue(aValued.getValue());
                }
            }
        }
    }

    /**
     * Clear all selected entries. This is only valid when the SELECT supports
     * multiple selections.
     *
     * @throws UnsupportedOperationException If the SELECT does not support
     *             multiple selections
     */
    public void deselectAll() {
        log.trace("deselectAll()");
        this.safeGetSelect().deselectAll();
    }

    /**
     * Deselect all options that have a value matching the individual arguments.
     * That is, when given "foo" this would deselect an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param values The values to match against
     */
    public void deselectByValue(String... values) {
        this.deselectByValue(SafeArrays.asList(values));
    }

    /**
     * Deselect all options that have a value matching the individual arguments.
     * That is, when given "foo" this would deselect an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param values The values to match against
     */
    public void deselectByValue(List<String> values) {
        log.trace("deselectByValue({})", values);
        Select select = this.safeGetSelect();
        for (String value : values) {
            select.deselectByValue(value);
        }
    }

    /**
     * Deselect the options at the given indexes. This is done by examining the
     * "index" attribute of the element, and not merely by counting.
     *
     * @param indexes The options at these indexes will be deselected
     */
    public void deselectByIndex(Integer... indexes) {
        this.deselectByIndex(SafeArrays.asList(indexes));
    }

    /**
     * Deselect the options at the given indexes. This is done by examining the
     * "index" attribute of the element, and not merely by counting.
     *
     * @param indexes The options at these indexes will be deselected
     */
    public void deselectByIndex(List<Integer> indexes) {
        log.trace("deselectByIndex({})", indexes);
        Select select = this.safeGetSelect();
        for (int index : indexes) {
            select.deselectByIndex(index);
        }
    }

    /**
     * Deselect all options that display text matching the individual arguments.
     * That is, when given "Bar" this would deselect an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param texts The visible texts to match against
     */
    public void deselectByVisibleText(String... texts) {
        this.deselectByVisibleText(SafeArrays.asList(texts));
    }

    /**
     * Deselect all options that display text matching the individual arguments.
     * That is, when given "Bar" this would deselect an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param texts The visible texts to match against
     */
    public void deselectByVisibleText(List<String> texts) {
        log.trace("deselectByVisibleText({})", texts);
        Select select = this.safeGetSelect();
        for (String text : texts) {
            select.deselectByVisibleText(text);
        }
    }

    /**
     * Deselect all options that have a value matching the individual arguments.
     * That is, when given "foo" this would deselect an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param values The values to match against
     */
    public void safeDeselectByValue(String... values) {
        this.safeDeselectByValue(SafeArrays.asList(values));
    }

    /**
     * Deselect all options that have a value matching the individual arguments.
     * That is, when given "foo" this would deselect an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param values The values to match against
     */
    public void safeDeselectByValue(List<String> values) {
        log.trace("safeDeselectByValue({})", values);
        if (values != null) {
            Select select = this.safeGetSelect();
            for (String value : values) {
                if (value != null) {
                    select.deselectByValue(value);
                }
            }
        }
    }

    /**
     * Deselect the options at the given indexes. This is done by examining the
     * "index" attribute of the element, and not merely by counting.
     *
     * @param indexes The options at these indexes will be deselected
     */
    public void safeDeselectByIndex(Integer... indexes) {
        this.safeDeselectByIndex(SafeArrays.asList(indexes));
    }

    /**
     * Deselect the options at the given indexes. This is done by examining the
     * "index" attribute of the element, and not merely by counting.
     *
     * @param indexes The options at these indexes will be deselected
     */
    public void safeDeselectByIndex(List<Integer> indexes) {
        log.trace("safeDeselectByIndex({})", indexes);
        if (indexes != null) {
            Select select = this.safeGetSelect();
            for (Integer index : indexes) {
                if (index != null) {
                    select.deselectByIndex(index);
                }
            }
        }
    }

    /**
     * Deselect all options that display text matching the individual arguments.
     * That is, when given "Bar" this would deselect an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param texts The visible texts to match against
     */
    public void safeDeselectByVisibleText(String... texts) {
        this.safeDeselectByVisibleText(SafeArrays.asList(texts));
    }

    /**
     * Deselect all options that display text matching the individual arguments.
     * That is, when given "Bar" this would deselect an option like: &lt;option
     * value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param texts The visible texts to match against
     */
    public void safeDeselectByVisibleText(List<String> texts) {
        log.trace("safeDeselectByVisibleText({})", texts);
        if (texts != null) {
            Select select = this.safeGetSelect();
            for (String text : texts) {
                if (text != null) {
                    select.deselectByVisibleText(text);
                }
            }
        }
    }

    /**
     * Convenience method.
     * 
     * @return Select instance for this.
     */
    protected Select safeGetSelect() {
        if (this.select == null) {
            this.select = new Select(this.safeGetUnderlyingWebElement());
        }
        return this.select;
    }

    /**
     * Sets the underlying web element and resets the Selenium Select helper.
     * 
     * @param element Underlying web element.
     */
    @Override
    public void setUnderlyingWebElement(@NotNull final WebElement element) {
        super.setUnderlyingWebElement(element);
        this.select = null;
    }
}
