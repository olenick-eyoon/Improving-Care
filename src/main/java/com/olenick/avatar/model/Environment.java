package com.olenick.avatar.model;

/**
 * Environment: Production, QA
 */
public enum Environment {
    PRODUCTION("https://icare2.improvingcare.com/ibi_apps"),
    QA("http://172.16.20.210:8080/ibi_apps");

    private final String urlRoot;

    private Environment(String urlRoot) {
        this.urlRoot = urlRoot;
    }

    public String getURLRoot() {
        return this.urlRoot;
    }
}
