package com.olenick.avatar.web.containers;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Improving Care Home page.
 */
public class HomeIFrame extends WebContainer<HomeIFrame> {
    public HomeIFrame(@NotNull ExtendedRemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public HomeIFrame waitForElementsToLoad() {
        return this;
    }
}
