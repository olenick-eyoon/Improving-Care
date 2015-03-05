package com.olenick.avatar.web.pages;

import org.junit.Test;

public class LoginPageTest extends PageTest {
    private static final String URL_ROOT_DEV = "http://172.16.20.210:8080/ibi_apps";
    private static final String USERNAME = "chicho@avatarsolutions.com";
    private static final String PASSWORD = "Chicho1";
    private static final String BAD_USERNAME = "invalid-user";
    private static final String BAD_PASSWORD = "invalid-password";

    @Test
    public void test_loading() {
        new LoginPage(this.driver, URL_ROOT_DEV).open()
                .waitForImportantElementsToLoad();
    }

    @Test
    public void test_login() {
        new LoginPage(this.driver, URL_ROOT_DEV).open().login(USERNAME,
                PASSWORD).waitForPageComplete();
    }

    @Test
    public void test_login_failure() {
        new LoginPage(this.driver, URL_ROOT_DEV).open().loginFailure(BAD_USERNAME, BAD_PASSWORD);
    }
}
