package com.olenick.avatar.web.containers;

import org.junit.Test;

import com.olenick.avatar.SeleniumTest;

public class LoginPageTest extends SeleniumTest {
    private static final String BAD_USERNAME = "invalid-user";
    private static final String BAD_PASSWORD = "invalid-password";
    private static final String USERNAME = "performance.test3@avatarsolutions.com";
    private static final String PASSWORD = "password";

    @Test
    public void test_loading() {
        new LoginPage(this.driver, URL_ROOT_DEV).open().waitForElementsToLoad();
    }

    @Test
    public void test_login() {
        new LoginPage(this.driver, URL_ROOT_DEV).open()
                .login(USERNAME, PASSWORD).waitForPageComplete();
    }

    @Test
    public void test_login_failure() {
        new LoginPage(this.driver, URL_ROOT_DEV).open().loginFailure(
                BAD_USERNAME, BAD_PASSWORD);
    }
}
