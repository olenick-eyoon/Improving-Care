package com.olenick.avatar.model;

import com.olenick.selenium.model.Valued;

/**
 * Group By in the date range section of a report filter.
 */
public enum DateRangeGroupBy implements Valued<String> {
    MONTHLY("1"), QUARTERLY("2"), TWICE_A_YEAR("3"), ANNUALLY("4");

    private final String optionValue;

    private DateRangeGroupBy(final String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionValue() {
        return this.optionValue;
    }

    public String getValue() {
        return this.optionValue;
    }
}
