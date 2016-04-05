package com.olenick.avatar.web.containers;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.Environment;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.model.Calculation;
import com.olenick.avatar.model.DataSet;
import com.olenick.avatar.model.DateKey;
import com.olenick.avatar.model.DateRangeGroupBy;
import com.olenick.avatar.model.PatientDemographics;
import com.olenick.avatar.model.ProviderFilter;
import com.olenick.avatar.model.ReportFilter;
import com.olenick.avatar.model.ReportTab;
import com.olenick.avatar.model.ResponseFilter;
import com.olenick.avatar.web.elements.AvatarMultiselectWebElement;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;
import com.olenick.selenium.elements.ExclusiveGroup;
import com.olenick.selenium.elements.ExtendedSelectWebElement;
import com.olenick.selenium.elements.ExtendedWebElement;

/**
 * Improving Care Patient Experience iframe.
 */
public class PatientExperienceIFrame extends
        WelcomePageIFrame<PatientExperienceIFrame> {
    private final static Logger log = LoggerFactory.getLogger(PatientExperienceIFrame.class);

    private static final String ATTRIBUTE_NAME_VALUE = "value";
    private static final String ELEMENT_ID_APPLY_BUTTON = "button2";
    private static final String ELEMENT_ID_CALCULATION_ITEM_TO_INCLUDE_CORE = "corecustom_0";
    private static final String ELEMENT_ID_CALCULATION_ITEM_TO_INCLUDE_CUSTOM = "corecustom_1";
    private static final String ELEMENT_ID_CALCULATION_ITEM_TO_INCLUDE_ANCILLARY = "corecustom_2";
    private static final String ELEMENT_ID_CALCULATION_MEAN_RADIO = "top_box_0";
    private static final String ELEMENT_ID_CALCULATION_TOP_BOX_RADIO = "top_box_1";
    private static final String ELEMENT_ID_CANCEL_BUTTON = "button4";
    private static final String ELEMENT_ID_CHANGE_SYSTEM_BUTTON = "chngsys";
    private static final String ELEMENT_ID_COMPOSITE_SELECT = "factor";
    private static final String ELEMENT_ID_COMPOSITE_TAB = "tabitem2";
    private static final String ELEMENT_ID_DATA_SET_ALL_RADIO = "dataset_1";
    private static final String ELEMENT_ID_DATA_SET_QUALIFIED_RADIO = "dataset_0";
    private static final String ELEMENT_ID_DATE_RANGE_DISCHARGE_VISIT_RADIO = "date_key_0";
    private static final String ELEMENT_ID_DATE_RANGE_FROM_MONTH_SELECT = "from_mnth";
    private static final String ELEMENT_ID_DATE_RANGE_FROM_SPAN = "maskdvfrom";
    private static final String ELEMENT_ID_DATE_RANGE_FROM_YEAR_SELECT = "from_year";
    private static final String ELEMENT_ID_DATE_RANGE_SURVEY_RETURN_RADIO = "date_key_1";
    private static final String ELEMENT_ID_DATE_RANGE_TO_MONTH_SELECT = "to_mnth";
    private static final String ELEMENT_ID_DATE_RANGE_TO_SPAN = "maskdvto";
    private static final String ELEMENT_ID_DATE_RANGE_TO_YEAR_SELECT = "to_year";
    private static final String ELEMENT_ID_DEMOGRAPHICS_FILTER_LINK = "demogrph";
    private static final String ELEMENT_ID_DEMOGRAPHICS_TAB = "tabitem1";
    private static final String ELEMENT_ID_DEPARTMENT_SELECT = "department";
    private static final String ELEMENT_ID_GROUP_BY_SELECT = "date_option";
    private static final String ELEMENT_ID_HCAHPS_NATIONAL_TAB = "tabitem7";
    private static final String ELEMENT_ID_ITEM_SELECT = "item";
    private static final String ELEMENT_ID_KEEP_VISIBLE_CHECKBOX = "checkbox1_0";
    private static final String ELEMENT_ID_LOCATION_SELECT = "location";
    private static final String ELEMENT_ID_ORGANIZATION_SELECT = "organization";
    private static final String ELEMENT_ID_OVERVIEW_TAB = "tabitem4";
    private static final String ELEMENT_ID_PATIENT_TYPE_SELECT = "patienttype";
    private static final String ELEMENT_ID_PROVIDER_FILTER_LINK = "prdgrp";
    private static final String ELEMENT_ID_RESET_BUTTON = "button3";
    private static final String ELEMENT_ID_RESPONSE_FILTER_LINK = "qfltr";
    private static final String ELEMENT_ID_SAVE_SELECTIONS_BUTTON = "buttonsave";
    private static final String ELEMENT_ID_SAVE_SELECTIONS_CHECKBOX = "checkboxsave2_0";
    private static final String ELEMENT_ID_SELECTIONS_NAME_INPUT = "txtname";
    private static final String ELEMENT_ID_SET_SELECTIONS_AS_DEFAULT_CHECKBOX = "checkbox3_0";
    private static final String ELEMENT_ID_SHARE_SELECTIONS_CHECKBOX = "checkbox4_0";
    private static final String ELEMENT_ID_SIDE_BY_SIDE_TAB = "tabitem3";
    private static final String ELEMENT_ID_SURVEY_TYPE_SELECT = "surveytype";
    private static final String ELEMENT_ID_SYSTEM_SELECT = "system";
    private static final String XPATH_RELATIVE_ALL_REPORT_TABS = "../span";
    private static final int STALE_RETRIES = 3;

    private final LoggedInWelcomePage parent;

    // Change System button
    private ExtendedWebElement changeSystemButton;

    // Report Level
    private AvatarMultiselectWebElement systemSelect, organizationSelect, departmentSelect, locationSelect;

    // Survey Selection
    private AvatarMultiselectWebElement surveyTypeSelect, patientTypeSelect, compositeSelect, itemSelect;

    // Date Range
    private ExtendedWebElement dateRangeDischargeVisitRadio, dateRangeSurveyReturnRadio, dateRangeFromSpan, dateRangeToSpan;
    private ExtendedSelectWebElement dateRangeFromMonthSelect, dateRangeFromYearSelect, dateRangeToMonthSelect, dateRangeToYearSelect, dateRangeGroupBySelect;
    private ExclusiveGroup<DateKey> dateKeyGroup;

    // Calculation
    private ExtendedWebElement calculationMeanRadio, calculationTopBoxRadio, dataSetQualifiedRadio, dataSetAllRadio, demographicsFilterLink, responseFilterLink, providerFilterLink, calculationItemToIncludeCore, calculationItemToIncludeCustom, calculationItemToIncludeAncillary;
    private ExclusiveGroup<Calculation> calculationGroup;
    private ExclusiveGroup<DataSet> dataSetGroup;

    // Buttons and checkboxes of "REPORT FILTERS" tab
    private ExtendedWebElement applyButton, cancelButton, resetButton, keepVisibleCheckbox, saveSelectionsCheckbox, selectionsNameInput, setSelectionsAsDefaultCheckbox, shareSelectionsCheckbox, saveSelectionsButton;

    private FilterByDemographicsPanel filterByDemographicsPanel;
    private FilterByProviderPanel filterByProviderPanel;
    private FilterByResponsePanel filterByResponsePanel;

    // Actual report tabs
    private ExtendedWebElement overviewTab, compositeTab, sideBySideTab, demographicsTab, hcahpsNationalTab;
    private ExclusiveGroup<ReportTab> tabs;
    private EnumMap<ReportTab, ReportGraphsTabIFrame<?>> tabIFrames;

    // TODO: Divide this into chunks
    public PatientExperienceIFrame(@NotNull final ExtendedRemoteWebDriver driver, @NotNull final LoggedInWelcomePage parent) {
        super(driver);
        this.parent = parent;

        this.initChangeSystemButtonElements();
        this.initReportLevelElements();
        this.initSurveySelectionElements();
        this.initDateRangeElements();
        this.initCalculationElements();
        this.initReportFilterButtons();
        this.initFilterPanelElements();
        this.initActualReportTabElements();
    }

    public PatientExperienceIFrame applyConfiguredFilters() {
        int rebindingTrials = 0;

        while(rebindingTrials < STALE_RETRIES) {
            try {
                this.applyButton.click();

                break;
            } catch (StaleElementReferenceException exception) {
                log.warn("Got stale applyConfiguredFilters");
                log.warn("Rebinding applyConfiguredFilters. Waiting...");
                waitForButtonsToLoad();
                rebindingTrials++;
                log.warn("Rebinding applyConfiguredFilters. Done #" + rebindingTrials);
            }
        }

        return this;
    }

    public PatientExperienceIFrame changeSystem(ReportFilter reportFilter) {
        return changeSystem(reportFilter, Environment.PRODUCTION);
    }

    public PatientExperienceIFrame changeSystem(ReportFilter reportFilter, Environment env) {
        this.accessPanelFrame();

        int rebindingTrials = 0;

        while(rebindingTrials < STALE_RETRIES) {
            try {
                if (!this.systemSelect.getFirstSelectedOption().getAttribute(ATTRIBUTE_NAME_VALUE).equals(reportFilter.getSystem())) {
                    return this.openChangeSystemPanel().chooseSystem(reportFilter.getSystem(), null, null).waitForElementsToLoad().navigateToPatientExperienceTab().waitForElementsToLoad(env);
                }

                break;
            } catch (StaleElementReferenceException exception) {
                log.warn("Got stale changingSystem");
                log.warn("Rebinding changingSystem. Waiting...");
                waitForReportLevelToLoad();
                rebindingTrials++;
                log.warn("Rebinding changingSystem. Done #" + rebindingTrials);
            }
        }

        return this;
    }

    public PatientExperienceIFrame configureFilters(ReportFilter reportFilter) {
        return configureFilters(reportFilter, Environment.PRODUCTION);
    }

    public PatientExperienceIFrame configureFilters(ReportFilter reportFilter, Environment env) {
        if (reportFilter != null) {
            this.configureReportLevelFilter(reportFilter);
            this.configureSurveySelectionFilter(reportFilter).configureCalculationFilter(reportFilter, env);
            this.configureDateRangeFilter(reportFilter);

            // Filter Panels
            PatientDemographics demographics = reportFilter.getDemographics();
            if (demographics != null) {
                this.openFilterByDemographicsPanel().waitForElementsToLoad()
                        .configureFilters(reportFilter.getDemographics());
            }
            ResponseFilter responseFilter = reportFilter.getResponseFilter();
            if (responseFilter != null) {
                this.openFilterByResponsePanel().waitForElementsToLoad()
                        .configureFilters(reportFilter.getResponseFilter());
            }
            ProviderFilter providerFilter = reportFilter.getProviderFilter();
            if (providerFilter != null) {
                this.openFilterByProviderPanel().waitForElementsToLoad()
                        .configureFilters(reportFilter.getProviderFilter());
            }
        }
        return this;
    }

    public PatientExperienceIFrame configureReportLevelFilter(ReportFilter reportFilter) {
        if (reportFilter != null) {
            boolean doneRebinding = false;
            int rebindingTrials = 0;

            while(!doneRebinding && rebindingTrials < STALE_RETRIES) {
                try {
                    this.systemSelect.safeSelectByValue(reportFilter.getSystem());
                    loadCombos("system", reportFilter.getSystem());
                    this.organizationSelect.safeSelectByValue(true, reportFilter.getOrganizations());
                    loadCombos("organization", reportFilter.getOrganizations());
                    this.departmentSelect.safeSelectByValue(true, reportFilter.getDepartments());
                    loadCombos("department", reportFilter.getDepartments());
                    this.locationSelect.safeSelectByValue(true, reportFilter.getLocations());
                    loadCombos("location", reportFilter.getLocations());
                    this.scrollUp();

                    doneRebinding = true;
                } catch (StaleElementReferenceException exception) {
                    log.warn("Got stale applying SurveySelectionFilter");
                    log.warn("Rebinding SurveySelectionFilter. Waiting...");
                    waitForReportLevelToLoad();
                    rebindingTrials++;
                    log.warn("Rebinding SurveySelectionFilter. Done #" + rebindingTrials);
                }
            }
        }

        return this;
    }

    public PatientExperienceIFrame configureSurveySelectionFilter(
            ReportFilter reportFilter) {
            if (reportFilter != null) {
                this.accessPanelFrame();

                boolean doneRebinding = false;
                int rebindingTrials = 0;

                while(!doneRebinding && rebindingTrials < STALE_RETRIES) {
                    try {
                        // TODO: THESE NEXT THREE LINES ARE A BIT RIDICULOUS,
                        // BUT IT DOESN'T WORK OTHERWISE...
                        this.surveyTypeSelect.sendKeys(reportFilter.getSurveyType());
                        this.surveyTypeSelect.click();
                        this.surveyTypeSelect.click();
                        loadCombos("surveyType", reportFilter.getSurveyType());
                        new Select(this.getDriver().findElement(By.id(ELEMENT_ID_SURVEY_TYPE_SELECT))).selectByValue(reportFilter.getSurveyType());
                        this.patientTypeSelect.safeSelectByValue(true, reportFilter.getPatientTypes());
                        loadCombos("patientType", reportFilter.getPatientTypes());
                        this.compositeSelect.safeSelectByValue(true, reportFilter.getFactorComposites());
                        loadCombos("composite", reportFilter.getFactorComposites());
                        this.itemSelect.safeSelectByValue(true, reportFilter.getItemQuestions());
                        loadCombos("item", reportFilter.getItemQuestions());


                        this.scrollUp();

                        doneRebinding = true;
                    } catch (StaleElementReferenceException exception) {
                        log.warn("Got stale applying SurveySelectionFilter");
                        log.warn("Rebinding SurveySelectionFilter. Waiting...");
                        waitForSurveySelectionToLoad();
                        rebindingTrials++;
                        log.warn("Rebinding SurveySelectionFilter. Done #" + rebindingTrials);
                    }
                }
            }

        return this;
    }

    public PatientExperienceIFrame configureDateRangeFilter(ReportFilter reportFilter) {
        if (reportFilter != null) {
            this.accessPanelFrame();

            boolean doneRebinding = false;
            int rebindingTrials = 0;

            while(!doneRebinding && rebindingTrials < STALE_RETRIES) {
                try {
                    this.dateKeyGroup.safeSelect(reportFilter.getDateKey());
                    this.openDatePickerFrom().waitForElementsToLoad().safePick(reportFilter.getFrom());
                    this.openDatePickerTo().waitForElementsToLoad().safePick(reportFilter.getTo());
                    DateRangeGroupBy groupBy = reportFilter.getGroupBy();
                    if (groupBy != null) {
                        this.dateRangeGroupBySelect.safeSelectByValue(groupBy.getValue());
                    }
                    this.scrollUp();

                    doneRebinding = true;
                } catch (StaleElementReferenceException exception) {
                    log.warn("Got stale applying DateRangeFilter");
                    log.warn("Rebinding DateRangeFilter. Waiting...");
                    waitForDateRangeToLoad();
                    rebindingTrials++;
                    log.warn("Rebinding DateRangeFilter. Done #" + rebindingTrials);
                }
            }
        }

        return this;
    }

    public PatientExperienceIFrame configureCalculationFilter(ReportFilter reportFilter) {
        return configureCalculationFilter(reportFilter, Environment.PRODUCTION);
    }

    public PatientExperienceIFrame configureCalculationFilter(ReportFilter reportFilter, Environment env) {
        if (reportFilter != null) {
            this.accessPanelFrame();

            boolean doneRebinding = false;
            int rebindingTrials = 0;

            while(!doneRebinding && rebindingTrials < STALE_RETRIES) {
                try {
                    this.calculationGroup.safeSelect(reportFilter.getCalculation());
                    this.dataSetGroup.safeSelect(reportFilter.getDataSet());

                    //TODO: Remove this shitty code from here which is duplicated from GetSystemReportValuesCommand.java
                    if (reportFilter.getSurveyType().compareToIgnoreCase("Avatar") == 0 && env.equals(Environment.QA)) {
                        //At least one checkbox must be selected, that's a site design requirement
                        if (this.calculationItemToIncludeCore.isSelected() || this.calculationItemToIncludeCustom.isSelected() || this.calculationItemToIncludeAncillary.isSelected()) {
                            if (reportFilter.getItemsToIncludeCore() && !this.calculationItemToIncludeCore.isSelected() || !reportFilter.getItemsToIncludeCore() && this.calculationItemToIncludeCore.isSelected())
                                this.calculationItemToIncludeCore.click();
                            if (reportFilter.getItemsToIncludeCustom() && !this.calculationItemToIncludeCustom.isSelected() || !reportFilter.getItemsToIncludeCustom() && this.calculationItemToIncludeCustom.isSelected())
                                this.calculationItemToIncludeCustom.click();
                            if (reportFilter.getItemsToIncludeAncillary() && !this.calculationItemToIncludeAncillary.isSelected() || !reportFilter.getItemsToIncludeAncillary() && this.calculationItemToIncludeAncillary.isSelected())
                                this.calculationItemToIncludeAncillary.click();
                        }
                    }

                    doneRebinding = true;
                } catch(StaleElementReferenceException exception) {
                    log.warn("Got stale applying CalculationFilter");
                    log.warn("Rebinding CalculationFilter. Waiting...");
                    waitForCalculationToLoad();
                    rebindingTrials++;
                    log.warn("Rebinding CalculationFilter. Done #" + rebindingTrials);
                }
            }
        }
        return this;
    }

    public ChangeSystemPanel openChangeSystemPanel() {
        this.changeSystemButton.click();
        return new ChangeSystemPanel(this.getDriver(), this);
    }

    public DatePickerPanel openDatePickerFrom() {
        this.dateRangeFromSpan.click();
        return new DatePickerPanel(this.getDriver(), this);
    }

    public DatePickerPanel openDatePickerTo() {
        this.dateRangeToSpan.click();
        return new DatePickerPanel(this.getDriver(), this);
    }

    public FilterByDemographicsPanel openFilterByDemographicsPanel() {
        this.demographicsFilterLink.click();
        return this.filterByDemographicsPanel;
    }

    public FilterByProviderPanel openFilterByProviderPanel() {
        this.providerFilterLink.click();
        return this.filterByProviderPanel;
    }

    public FilterByResponsePanel openFilterByResponsePanel() {
        this.responseFilterLink.click();
        return this.filterByResponsePanel;
    }

    public CompositeTabIFrame openCompositeTab() {
        return (CompositeTabIFrame) this.openReportTab(ReportTab.COMPOSITE);
    }

    public DemographicsTabIFrame openDemographicsTab() {
        return (DemographicsTabIFrame) this
                .openReportTab(ReportTab.DEMOGRAPHICS);
    }

    public HCAHPSNationalTabIFrame openHCAHPSNationalTab() {
        return (HCAHPSNationalTabIFrame) this
                .openReportTab(ReportTab.HCAHPS_NATIONAL);
    }

    public OverviewTabIFrame openOverviewTab() {
        return (OverviewTabIFrame) this.openReportTab(ReportTab.OVERVIEW);
    }

    public SideBySideTabIFrame openSideBySideTab() {
        return (SideBySideTabIFrame) this.openReportTab(ReportTab.SIDE_BY_SIDE);
    }

    public <F extends ReportGraphsTabIFrame<F>> F openReportTab(
            ReportTab reportTab) {
        F tabIFrame;
        try {
            tabIFrame = this.<F>unsafeOpenReportTab(reportTab);
        } catch (UnhandledAlertException exception) {
            try {
                this.getDriver().switchTo().alert();
                this.getDriver().switchTo().alert().accept();
                this.getDriver().switchTo().defaultContent();
            } catch (NoAlertPresentException ignored) {
            }
            tabIFrame = this.<F>unsafeOpenReportTab(reportTab);
        }
        return tabIFrame;
    }

    @SuppressWarnings("unchecked")
    public <F extends ReportGraphsTabIFrame<F>> F unsafeOpenReportTab(
            ReportTab reportTab) {
        this.accessPanelFrame();
        F tabIFrame = null;
        if (reportTab != null) {
            this.tabs.select(reportTab);
            tabIFrame = (F) this.tabIFrames.get(reportTab);
            this.ensureIFrameLoaded();
        }
        return tabIFrame;
    }

    @Override
    public PatientExperienceIFrame waitForElementsToLoad() {
        return waitForElementsToLoad(Environment.PRODUCTION);
    }

    public PatientExperienceIFrame waitForElementsToLoad(Environment env) {
        this.accessPanelFrame();
        this.ensureIFrameLoaded();

        this.waitForChangeSystemButtonToLoad();
        this.waitForReportLevelToLoad();
        this.waitForSurveySelectionToLoad();
        this.waitForDateRangeToLoad();
        this.waitForCalculationToLoad(env);
        this.waitForButtonsToLoad();
        this.waitForReportTabsToLoad();

        return this;
    }

    private void initChangeSystemButtonElements() {
        this.changeSystemButton = new ExtendedWebElement(this);
    }

    private void initCalculationElements() {
        this.calculationMeanRadio = new ExtendedWebElement(this);
        this.calculationTopBoxRadio = new ExtendedWebElement(this);
        this.dataSetQualifiedRadio = new ExtendedWebElement(this);
        this.dataSetAllRadio = new ExtendedWebElement(this);
        this.demographicsFilterLink = new ExtendedWebElement(this);
        this.responseFilterLink = new ExtendedWebElement(this);
        this.providerFilterLink = new ExtendedWebElement(this);
        this.calculationGroup = new ExclusiveGroup<>(Calculation.class);
        this.calculationGroup.add(Calculation.MEAN, this.calculationMeanRadio)
                .add(Calculation.TOP_BOX, this.calculationTopBoxRadio);
        this.dataSetGroup = new ExclusiveGroup<>(DataSet.class);
        this.dataSetGroup.add(DataSet.ALL, this.dataSetAllRadio).add(
                DataSet.QUALIFIED, this.dataSetQualifiedRadio);
        this.calculationItemToIncludeCore = new ExtendedWebElement(this);
        this.calculationItemToIncludeCustom = new ExtendedWebElement(this);
        this.calculationItemToIncludeAncillary = new ExtendedWebElement(this);
    }

    private void initDateRangeElements() {
        this.dateRangeDischargeVisitRadio = new ExtendedWebElement(this);
        this.dateRangeSurveyReturnRadio = new ExtendedWebElement(this);
        this.dateRangeFromSpan = new ExtendedWebElement(this);
        this.dateRangeFromMonthSelect = new ExtendedSelectWebElement(this);
        this.dateRangeFromYearSelect = new ExtendedSelectWebElement(this);
        this.dateRangeToSpan = new ExtendedWebElement(this);
        this.dateRangeToMonthSelect = new ExtendedSelectWebElement(this);
        this.dateRangeToYearSelect = new ExtendedSelectWebElement(this);
        this.dateRangeGroupBySelect = new ExtendedSelectWebElement(this);
        this.dateKeyGroup = new ExclusiveGroup<>(DateKey.class);
        this.dateKeyGroup.add(DateKey.DISCHARGE_VISIT,
                this.dateRangeDischargeVisitRadio).add(DateKey.SURVEY_RETURN,
                this.dateRangeSurveyReturnRadio);
    }

    private void initFilterPanelElements() {
        this.filterByDemographicsPanel = new FilterByDemographicsPanel(
                this.getDriver(), this);
        this.filterByResponsePanel = new FilterByResponsePanel(
                this.getDriver(), this);
        this.filterByProviderPanel = new FilterByProviderPanel(
                this.getDriver(), this);
    }

    private void initReportFilterButtons() {
        this.applyButton = new ExtendedWebElement(this);
        this.cancelButton = new ExtendedWebElement(this);
        this.resetButton = new ExtendedWebElement(this);
        this.keepVisibleCheckbox = new ExtendedWebElement(this);
        this.saveSelectionsCheckbox = new ExtendedWebElement(this);
        this.selectionsNameInput = new ExtendedWebElement(this);
        this.setSelectionsAsDefaultCheckbox = new ExtendedWebElement(this);
        this.shareSelectionsCheckbox = new ExtendedWebElement(this);
        this.saveSelectionsButton = new ExtendedWebElement(this);
    }

    private void initReportLevelElements() {
        this.systemSelect = new AvatarMultiselectWebElement(this);
        this.organizationSelect = new AvatarMultiselectWebElement(this);
        this.departmentSelect = new AvatarMultiselectWebElement(this);
        this.locationSelect = new AvatarMultiselectWebElement(this);
    }

    private void initSurveySelectionElements() {
        this.surveyTypeSelect = new AvatarMultiselectWebElement(this);
        this.patientTypeSelect = new AvatarMultiselectWebElement(this);
        this.compositeSelect = new AvatarMultiselectWebElement(this);
        this.itemSelect = new AvatarMultiselectWebElement(this);
    }

    private void initActualReportTabElements() {
        this.tabs = new ExclusiveGroup<>(ReportTab.class);
        this.tabIFrames = new EnumMap<>(ReportTab.class);

        this.overviewTab = new ExtendedWebElement(this);
        OverviewTabIFrame overviewTabIFrame = new OverviewTabIFrame(
                this.getDriver(), this);
        this.tabs.add(ReportTab.OVERVIEW, this.overviewTab);
        this.tabIFrames.put(ReportTab.OVERVIEW, overviewTabIFrame);

        this.compositeTab = new ExtendedWebElement(this);
        CompositeTabIFrame compositeTabIFrame = new CompositeTabIFrame(
                this.getDriver(), this);
        this.tabs.add(ReportTab.COMPOSITE, this.compositeTab);
        this.tabIFrames.put(ReportTab.COMPOSITE, compositeTabIFrame);

        this.sideBySideTab = new ExtendedWebElement(this);
        SideBySideTabIFrame sideBySideTabIFrame = new SideBySideTabIFrame(
                this.getDriver(), this);
        this.tabs.add(ReportTab.SIDE_BY_SIDE, this.sideBySideTab);
        this.tabIFrames.put(ReportTab.SIDE_BY_SIDE, sideBySideTabIFrame);

        this.demographicsTab = new ExtendedWebElement(this);
        DemographicsTabIFrame demographicsTabIFrame = new DemographicsTabIFrame(
                this.getDriver(), this);
        this.tabs.add(ReportTab.DEMOGRAPHICS, this.demographicsTab);
        this.tabIFrames.put(ReportTab.DEMOGRAPHICS, demographicsTabIFrame);

        this.hcahpsNationalTab = new ExtendedWebElement(this);
        HCAHPSNationalTabIFrame hcahpsNationalTabIFrame = new HCAHPSNationalTabIFrame(
                this.getDriver(), this);
        this.tabs.add(ReportTab.HCAHPS_NATIONAL, this.hcahpsNationalTab);
        this.tabIFrames.put(ReportTab.HCAHPS_NATIONAL, hcahpsNationalTabIFrame);
    }

    protected void loadCombos(String control, String value) {
        loadCombos(control, Arrays.asList(value));
    }

    protected void loadCombos(String control, List<String> values) {
        log.debug("{}: {}", control, values);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
    }

    protected void waitForChangeSystemButtonToLoad() {
        this.setElements(this.changeSystemButton).byId(
                ELEMENT_ID_CHANGE_SYSTEM_BUTTON);
    }

    protected void waitForButtonsToLoad() {
        this.setElements(this.applyButton, this.cancelButton, this.resetButton,
                this.keepVisibleCheckbox, this.saveSelectionsCheckbox,
                this.selectionsNameInput, this.setSelectionsAsDefaultCheckbox,
                this.shareSelectionsCheckbox, this.saveSelectionsButton)
                .byId(true, ELEMENT_ID_APPLY_BUTTON, ELEMENT_ID_CANCEL_BUTTON,
                        ELEMENT_ID_RESET_BUTTON,
                        ELEMENT_ID_KEEP_VISIBLE_CHECKBOX,
                        ELEMENT_ID_SAVE_SELECTIONS_CHECKBOX)
                .byId(false, ELEMENT_ID_SELECTIONS_NAME_INPUT,
                        ELEMENT_ID_SET_SELECTIONS_AS_DEFAULT_CHECKBOX,
                        ELEMENT_ID_SHARE_SELECTIONS_CHECKBOX,
                        ELEMENT_ID_SAVE_SELECTIONS_BUTTON);
    }

    protected void waitForCalculationToLoad() {
        waitForCalculationToLoad(Environment.PRODUCTION);
    }

    protected void waitForCalculationToLoad(Environment env) {
        this.setElements(this.calculationMeanRadio,
                this.calculationTopBoxRadio, this.dataSetQualifiedRadio, this.dataSetAllRadio,
                this.demographicsFilterLink, this.responseFilterLink, this.providerFilterLink).byId(true,
                ELEMENT_ID_CALCULATION_MEAN_RADIO,
                ELEMENT_ID_CALCULATION_TOP_BOX_RADIO,
                ELEMENT_ID_DATA_SET_QUALIFIED_RADIO,
                ELEMENT_ID_DATA_SET_ALL_RADIO,
                ELEMENT_ID_DEMOGRAPHICS_FILTER_LINK,
                ELEMENT_ID_RESPONSE_FILTER_LINK,
                ELEMENT_ID_PROVIDER_FILTER_LINK);

        if (env.equals(Environment.QA)) {
            this.setElements(this.calculationItemToIncludeCore, this.calculationItemToIncludeCustom,
                    this.calculationItemToIncludeAncillary).byId(false,
                    ELEMENT_ID_CALCULATION_ITEM_TO_INCLUDE_CORE,
                    ELEMENT_ID_CALCULATION_ITEM_TO_INCLUDE_CUSTOM,
                    ELEMENT_ID_CALCULATION_ITEM_TO_INCLUDE_ANCILLARY);
        }
    }

    protected void waitForDateRangeToLoad() {
        this.setElements(this.dateRangeDischargeVisitRadio,
                this.dateRangeSurveyReturnRadio, this.dateRangeFromSpan,
                this.dateRangeToSpan, this.dateRangeGroupBySelect,
                this.dateRangeFromMonthSelect, this.dateRangeFromYearSelect,
                this.dateRangeToMonthSelect, this.dateRangeToYearSelect)
                .byId(true, ELEMENT_ID_DATE_RANGE_DISCHARGE_VISIT_RADIO,
                        ELEMENT_ID_DATE_RANGE_SURVEY_RETURN_RADIO,
                        ELEMENT_ID_DATE_RANGE_FROM_SPAN,
                        ELEMENT_ID_DATE_RANGE_TO_SPAN,
                        ELEMENT_ID_GROUP_BY_SELECT)
                .byId(false, ELEMENT_ID_DATE_RANGE_FROM_MONTH_SELECT,
                        ELEMENT_ID_DATE_RANGE_FROM_YEAR_SELECT,
                        ELEMENT_ID_DATE_RANGE_TO_MONTH_SELECT,
                        ELEMENT_ID_DATE_RANGE_TO_YEAR_SELECT);
    }

    protected void waitForReportLevelToLoad() {
        this.setElements(this.systemSelect, this.organizationSelect,
                this.departmentSelect, this.locationSelect).byId(true,
                ELEMENT_ID_SYSTEM_SELECT, ELEMENT_ID_ORGANIZATION_SELECT,
                ELEMENT_ID_DEPARTMENT_SELECT, ELEMENT_ID_LOCATION_SELECT);
    }

    protected void waitForReportTabsToLoad() {
        this.setElements(this.overviewTab, this.compositeTab,
                this.sideBySideTab, this.demographicsTab).byId(true,
                ELEMENT_ID_OVERVIEW_TAB, ELEMENT_ID_COMPOSITE_TAB,
                ELEMENT_ID_SIDE_BY_SIDE_TAB, ELEMENT_ID_DEMOGRAPHICS_TAB);
        // TODO: Find a better way to do this...
        for (WebElement tabRelated : this.overviewTab.findElements(By
                .xpath(XPATH_RELATIVE_ALL_REPORT_TABS))) {
            if (ELEMENT_ID_HCAHPS_NATIONAL_TAB.equals(tabRelated
                    .getAttribute("id"))) {
                this.hcahpsNationalTab.setUnderlyingWebElement(tabRelated);
                break;
            }
        }
    }

    protected void waitForSurveySelectionToLoad() {
        this.setElements(this.surveyTypeSelect, this.patientTypeSelect,
                this.compositeSelect, this.itemSelect).byId(true,
                ELEMENT_ID_SURVEY_TYPE_SELECT, ELEMENT_ID_PATIENT_TYPE_SELECT,
                ELEMENT_ID_COMPOSITE_SELECT, ELEMENT_ID_ITEM_SELECT);
    }
}
