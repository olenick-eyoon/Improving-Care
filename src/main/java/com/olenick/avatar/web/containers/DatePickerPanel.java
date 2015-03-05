package com.olenick.avatar.web.containers;

import java.time.Month;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;

import com.olenick.avatar.model.MonthSpec;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;
import com.olenick.avatar.web.elements.ExtendedWebElement;

/**
 * Date Picker panel.
 */
public class DatePickerPanel extends WebContainer<DatePickerPanel> {
    public static final String ELEMENT_ID_OK_BUTTON = "btn_ok";
    public static final String ELEMENT_ID_CANCEL_BUTTON = "btn_cls";
    private static final int NUMBER_OF_YEARS = 9;
    private static final String ELEMENT_CLASS_MONTH_PREFIX = "month_";
    private static final String ELEMENT_CLASS_YEAR_PREFIX = "year_";

    private PatientExperienceIFrame parent;

    private EnumMap<Month, ExtendedWebElement> monthElements;
    private Map<String, ExtendedWebElement> yearElements;

    private ExtendedWebElement cancelButton, okButton;

    public DatePickerPanel(@NotNull final ExtendedRemoteWebDriver driver,
            PatientExperienceIFrame parent) {
        super(driver);

        this.parent = parent;

        this.monthElements = new EnumMap<>(Month.class);
        for (int monthIndex = 1; monthIndex <= 12; ++monthIndex) {
            this.monthElements.put(Month.of(monthIndex),
                    new ExtendedWebElement(this));
        }
        this.yearElements = new HashMap<>(NUMBER_OF_YEARS);
        int currentYear = new GregorianCalendar().get(Calendar.YEAR);
        for (int yearIndex = 0; yearIndex < NUMBER_OF_YEARS; ++yearIndex) {
            this.yearElements.put(String.valueOf(currentYear - yearIndex),
                    new ExtendedWebElement(this));
        }

        this.okButton = new ExtendedWebElement(this);
        this.cancelButton = new ExtendedWebElement(this);
    }

    public PatientExperienceIFrame pick(final MonthSpec monthSpec) {
        this.monthElements.get(monthSpec.getMonth()).click();
        this.yearElements.get(monthSpec.getYear()).click();
        this.okButton.click();
        return this.parent;
    }

    @Override
    public DatePickerPanel waitForElementsToLoad() {
        for (Map.Entry<Month, ExtendedWebElement> entry : this.monthElements
                .entrySet()) {
            entry.getValue().setUnderlyingWebElement(
                    this.driver.findElement(By
                            .className(ELEMENT_CLASS_MONTH_PREFIX
                                    + (entry.getKey().getValue() - 1))));
        }
        for (Map.Entry<String, ExtendedWebElement> entry : this.yearElements
                .entrySet()) {
            entry.getValue().setUnderlyingWebElement(
                    this.driver.findElement(By
                            .className(ELEMENT_CLASS_YEAR_PREFIX
                                    + entry.getKey())));
        }

        this.okButton.setUnderlyingWebElement(this.driver.findElement(By
                .className(ELEMENT_ID_OK_BUTTON)));
        this.cancelButton.setUnderlyingWebElement(this.driver.findElement(By
                .className(ELEMENT_ID_CANCEL_BUTTON)));

        return this;
    }
}
