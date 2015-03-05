package com.olenick.avatar.web.containers;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Improving Care Control Center iframe.
 */
public class ControlCenterIFrame extends WebContainer<ControlCenterIFrame> {
    public ControlCenterIFrame(@NotNull ExtendedRemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public ControlCenterIFrame waitForElementsToLoad() {
        return this;
    }
}
