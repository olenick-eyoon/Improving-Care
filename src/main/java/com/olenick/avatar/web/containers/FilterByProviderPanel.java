package com.olenick.avatar.web.containers;

import java.util.EnumMap;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;

import com.olenick.avatar.model.ProviderFilter;
import com.olenick.avatar.model.ProviderGrouping;
import com.olenick.selenium.containers.WebContainer;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;
import com.olenick.selenium.elements.ExclusiveGroup;
import com.olenick.selenium.elements.ExtendedSelectWebElement;
import com.olenick.selenium.elements.ExtendedWebElement;

/**
 * "Filter by Provider" panel inside "REPORT FILTERS".
 */
public class FilterByProviderPanel extends WebContainer<FilterByProviderPanel> {
    public static final String ELEMENT_ID_APPLY_BUTTON = "applycpg";
    public static final String ELEMENT_ID_CLOSE_BUTTON = "imgclscpg";
    public static final String ELEMENT_ID_CUSTOM_GROUPING_LIST = "prvdr";
    public static final String ELEMENT_ID_CUSTOM_GROUPING_RADIO = "cstprvgr";
    public static final String ELEMENT_ID_DEPARTMENT_DIVISION_LIST = "division";
    public static final String ELEMENT_ID_DEPARTMENT_DIVISION_RADIO = "dptdvs";
    public static final String ELEMENT_ID_PRIMARY_SPECIALTY_LIST = "splty";
    public static final String ELEMENT_ID_PRIMARY_SPECIALTY_RADIO = "prisplty";
    public static final String ELEMENT_ID_PROVIDERS_LIST = "dprvdrs";

    private PatientExperienceIFrame parent;

    private ExtendedWebElement applyButton, closeButton, customGroupingRadio,
            departmentDivisionRadio, primarySpecialtyRadio;
    private ExclusiveGroup<ProviderGrouping> providerGroupingGroup;
    private ExtendedSelectWebElement customGroupingList,
            departmentDivisionList, primarySpecialtyList, providersList;
    private EnumMap<ProviderGrouping, ExtendedSelectWebElement> lists;

    public FilterByProviderPanel(@NotNull ExtendedRemoteWebDriver driver,
            PatientExperienceIFrame parent) {
        super(driver);

        this.parent = parent;

        this.closeButton = new ExtendedWebElement(this);

        this.departmentDivisionRadio = new ExtendedWebElement(this);
        this.primarySpecialtyRadio = new ExtendedWebElement(this);
        this.customGroupingRadio = new ExtendedWebElement(this);
        this.providerGroupingGroup = new ExclusiveGroup<>(
                ProviderGrouping.class);
        this.providerGroupingGroup
                .add(ProviderGrouping.PRIMARY_SPECIALTY,
                        this.primarySpecialtyRadio)
                .add(ProviderGrouping.DEPARTMENT_DIVISION,
                        this.departmentDivisionRadio)
                .add(ProviderGrouping.CUSTOM, this.customGroupingRadio);

        this.customGroupingList = new ExtendedSelectWebElement(this);
        this.departmentDivisionList = new ExtendedSelectWebElement(this);
        this.primarySpecialtyList = new ExtendedSelectWebElement(this);
        this.lists = new EnumMap<>(ProviderGrouping.class);
        this.lists.put(ProviderGrouping.CUSTOM, this.customGroupingList);
        this.lists.put(ProviderGrouping.DEPARTMENT_DIVISION,
                this.departmentDivisionList);
        this.lists.put(ProviderGrouping.PRIMARY_SPECIALTY,
                this.primarySpecialtyList);
        this.providersList = new ExtendedSelectWebElement(this);

        this.applyButton = new ExtendedWebElement(this);
    }

    public PatientExperienceIFrame close() {
        this.closeButton.click();
        return this.parent;
    }

    public PatientExperienceIFrame configureFilters(
            ProviderFilter providerFilter) {
        if (providerFilter != null) {
            ProviderGrouping grouping = providerFilter.getGrouping();
            this.providerGroupingGroup.safeSelect(grouping);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
            this.lists.get(grouping).selectByValue(
                    providerFilter.getGroupingElements());
            this.providersList.selectByValue(providerFilter.getProviders());
        }
        return this.parent;
    }

    @Override
    public FilterByProviderPanel waitForElementsToLoad() {
        this.closeButton.setUnderlyingWebElement(this.driver.findElement(By
                .id(ELEMENT_ID_CLOSE_BUTTON)));

        this.departmentDivisionRadio.setUnderlyingWebElement(this.driver
                .findElement(By.id(ELEMENT_ID_DEPARTMENT_DIVISION_RADIO)));
        this.primarySpecialtyRadio.setUnderlyingWebElement(this.driver
                .findElement(By.id(ELEMENT_ID_PRIMARY_SPECIALTY_RADIO)));
        this.customGroupingRadio.setUnderlyingWebElement(this.driver
                .findElement(By.id(ELEMENT_ID_CUSTOM_GROUPING_RADIO)));

        this.customGroupingList.setUnderlyingWebElement(this.driver
                .findElement(By.id(ELEMENT_ID_CUSTOM_GROUPING_LIST)));
        this.departmentDivisionList.setUnderlyingWebElement(this.driver
                .findElement(By.id(ELEMENT_ID_DEPARTMENT_DIVISION_LIST)));
        this.primarySpecialtyList.setUnderlyingWebElement(this.driver
                .findElement(By.id(ELEMENT_ID_PRIMARY_SPECIALTY_LIST)));

        this.providersList.setUnderlyingWebElement(this.driver.findElement(By
                .id(ELEMENT_ID_PROVIDERS_LIST)));

        this.applyButton.setUnderlyingWebElement(this.driver.findElement(By
                .id(ELEMENT_ID_APPLY_BUTTON)));

        return this;
    }
}
