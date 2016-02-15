package com.olenick.avatar.model;

/**
 * Created by eyoon on 1/29/2016.
 */
public enum CSVParameters {
    SHEET_NUMBER_POSITION(0),
    SECTION_TITLE_POSITION(1),
    SYSTEM_CODE_POSITION(2),
    ORGANIZATION_CODE_POSITION(3),
    SURVEY_TYPE_POSITION(4),
    PATIENT_TYPE_POSITION(5),
    DATES_POSITION(6),
    ITEMS_POSITION(7),
    ENVIRONMENTS_POSITION(8);

    private int value;

    CSVParameters(int value) { this.value = value; }

    public int GetValue() {
        return value;
    }
}
