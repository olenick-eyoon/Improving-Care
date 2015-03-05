package com.olenick.avatar.web.containers;

import org.junit.Test;

import com.olenick.avatar.SeleniumTest;

public class LoggedInWelcomePageTest extends SeleniumTest {
    private static final String USERNAME = "performance.test3@avatarsolutions.com";
    private static final String PASSWORD = "password";

    @Test
    public void test_open() {
        new LoginPage(this.driver, URL_ROOT_DEV).open()
                .login(USERNAME, PASSWORD).waitForElementsToLoad();
    }

    @Test
    public void test_open_patient_experience_tab() {
        new LoginPage(this.driver, URL_ROOT_DEV).open()
                .login(USERNAME, PASSWORD).waitForElementsToLoad()
                .navigateToPatientExperienceTab().waitForElementsToLoad();
    }
}
