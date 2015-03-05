package com.olenick.avatar.parsers.xml;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.time.Month;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.olenick.avatar.exceptions.ParseException;
import com.olenick.avatar.model.Calculation;
import com.olenick.avatar.model.DateRangeGroupBy;
import com.olenick.avatar.model.MonthSpec;
import com.olenick.avatar.model.PatientDemographics;
import com.olenick.avatar.model.ReportFilter;
import com.olenick.avatar.model.ReportTab;
import com.olenick.avatar.model.ReportTabSpec;
import com.olenick.avatar.uses.PatientExperienceFeature;
import com.olenick.avatar.uses.PatientExperienceScenario;

public class XMLPatientExperienceFeatureParserTest {
    private static final String TEST_XML_SCENARIO_SYSTEM = "78";
    private static final String TEST_XML_ORGANIZATION1 = "541";
    private static final String TEST_XML_ORGANIZATION2 = "546";
    private static final String TEST_XML_LIST_ALL = "_FOC_NULL";
    private static final String TEST_XML_SURVEY_TYPE_HCAHPS = "HCAHPS";
    private static final String TEST_XML_PATIENT_TYPE_HCAHPS = "HCAHPS";
    private static final String TEST_XML_FROM_YEAR = "2012";
    private static final String TEST_XML_TO_YEAR = "2013";
    private static final String TEST_XML_TAB_FILTER = "Gender";
    private static final String ATTR_SCENARIO_NAME = "name";
    private static final String ATTR_SCENARIO_PASS = "password";
    private static final String ATTR_SCENARIO_USER = "user";
    private static final String SCENARIO_NAME = "my scenario";
    private static final String SCENARIO_PASSWORD = "some password";
    private static final String SCENARIO_USER = "a user";
    private static final String TAG_FEATURE = "feature";
    private static final String TAG_SCENARIO = "scenario";
    private static final String TEST_XML_FILE_NAME = "scenario-specs/test_feature.xml";
    private static final String TEST_XML_SCENARIO_NAME = "1A";
    private static final String TEST_XML_SCENARIO_USER = "performance.test4@avatarsolutions.com";
    private static final String TEST_XML_SCENARIO_PASS = "pass";
    private static final Calculation TEST_XML_CALCULATION = Calculation.TOP_BOX;
    private static final DateRangeGroupBy TEST_XML_GROUP_BY = DateRangeGroupBy.MONTHLY;
    private static final ReportTab TEST_XML_TAB1 = ReportTab.DEMOGRAPHICS;
    private static final ReportTab TEST_XML_TAB2 = ReportTab.OVERVIEW;
    private static final Month TEST_XML_FROM_MONTH = Month.JANUARY;
    private static final Month TEST_XML_TO_MONTH = Month.AUGUST;

    private static final PatientExperienceScenario EMPTY_SCENARIO = new PatientExperienceScenario(
            SCENARIO_NAME, SCENARIO_USER, SCENARIO_PASSWORD);
    private Document document;

    @Before
    public void setUp() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        document = docBuilder.newDocument();
    }

    @Test
    public void test_constructor_should_not_fail() throws ParseException {
        new XMLPatientExperienceFeatureParser();
    }

    @Test
    public void test_empty_feature_should_not_fail() throws ParseException {
        // Given
        Element rootElement = document.createElement(TAG_FEATURE);

        // When
        XMLPatientExperienceFeatureParser parser = new XMLPatientExperienceFeatureParser();
        PatientExperienceFeature feature = parser.parse(rootElement);

        // Then
        Assert.assertNull(feature.getScenarios());
    }

    @Ignore
    @Test
    public void test_scenario_with_no_name_must_fail() throws ParseException {
        run_test_of_empty_scenario_with_no_attribute(ATTR_SCENARIO_NAME);
    }

    @Ignore
    @Test
    public void test_scenario_with_no_user_must_fail() throws ParseException {
        run_test_of_empty_scenario_with_no_attribute(ATTR_SCENARIO_USER);
    }

    @Ignore
    @Test
    public void test_scenario_with_no_password_must_fail()
            throws ParseException {
        run_test_of_empty_scenario_with_no_attribute(ATTR_SCENARIO_PASS);
    }

    @Test
    public void test_empty_scenario_should_not_fail() throws ParseException {
        // Given
        Element rootElement = document.createElement(TAG_FEATURE);
        Element scenarioElement = document.createElement(TAG_SCENARIO);
        scenarioElement.setAttribute(ATTR_SCENARIO_NAME, SCENARIO_NAME);
        scenarioElement.setAttribute(ATTR_SCENARIO_USER, SCENARIO_USER);
        scenarioElement.setAttribute(ATTR_SCENARIO_PASS, SCENARIO_PASSWORD);
        rootElement.appendChild(scenarioElement);

        // When
        XMLPatientExperienceFeatureParser parser = new XMLPatientExperienceFeatureParser();
        PatientExperienceFeature feature = parser.parse(rootElement);

        // Then
        List<PatientExperienceScenario> scenarios = feature.getScenarios();
        assertEquals(1, scenarios.size());
        assertEquals(EMPTY_SCENARIO, scenarios.get(0));
    }

    @Test
    public void test_feature_from_test_xml_file() throws ParseException {
        // Given
        URL xmlFileName = this.getClass().getClassLoader()
                .getResource(TEST_XML_FILE_NAME);

        // When
        XMLPatientExperienceFeatureParser parser = new XMLPatientExperienceFeatureParser();
        PatientExperienceFeature feature = parser.parse(xmlFileName);

        // Then
        assertEquals(2, feature.getScenarios().size());

        Iterator<PatientExperienceScenario> scenarios = feature.getScenarios()
                .iterator();
        PatientExperienceScenario scenario1 = scenarios.next();
        assertEquals(TEST_XML_SCENARIO_NAME, scenario1.getName());
        assertEquals(TEST_XML_SCENARIO_USER, scenario1.getUser());
        assertEquals(TEST_XML_SCENARIO_PASS, scenario1.getPassword());
        ReportFilter reportFilter = scenario1.getReportFilter();
        assertEquals(TEST_XML_SCENARIO_SYSTEM, reportFilter.getSystem());
        assertEquals(
                Arrays.asList(TEST_XML_ORGANIZATION1, TEST_XML_ORGANIZATION2),
                reportFilter.getOrganizations());
        assertEquals(Arrays.asList(TEST_XML_LIST_ALL),
                reportFilter.getDepartments());
        assertEquals(Arrays.asList(TEST_XML_LIST_ALL),
                reportFilter.getLocations());
        assertEquals(TEST_XML_SURVEY_TYPE_HCAHPS, reportFilter.getSurveyType());
        assertEquals(Arrays.asList(TEST_XML_PATIENT_TYPE_HCAHPS),
                reportFilter.getPatientTypes());
        assertEquals(Arrays.asList(TEST_XML_LIST_ALL),
                reportFilter.getFactorComposites());
        assertEquals(Arrays.asList(TEST_XML_LIST_ALL),
                reportFilter.getItemQuestions());
        assertEquals(new MonthSpec(TEST_XML_FROM_MONTH, TEST_XML_FROM_YEAR),
                reportFilter.getFrom());
        assertEquals(new MonthSpec(TEST_XML_TO_MONTH, TEST_XML_TO_YEAR),
                reportFilter.getTo());
        assertEquals(TEST_XML_GROUP_BY, reportFilter.getGroupBy());
        assertEquals(TEST_XML_CALCULATION, reportFilter.getCalculation());
        PatientDemographics expectedDemographics = new PatientDemographics(
                Arrays.asList(TEST_XML_LIST_ALL),
                Arrays.asList(TEST_XML_LIST_ALL),
                Arrays.asList(TEST_XML_LIST_ALL),
                Arrays.asList(TEST_XML_LIST_ALL),
                Arrays.asList(TEST_XML_LIST_ALL),
                Arrays.asList(TEST_XML_LIST_ALL),
                Arrays.asList(TEST_XML_LIST_ALL),
                Arrays.asList(TEST_XML_LIST_ALL));
        assertEquals(expectedDemographics, reportFilter.getDemographics());
        assertEquals(Arrays.asList(new ReportTabSpec(TEST_XML_TAB1,
                TEST_XML_TAB_FILTER), new ReportTabSpec(TEST_XML_TAB2, null)),
                reportFilter.getTabSpecs());

        PatientExperienceScenario scenario2 = scenarios.next();
        assertEquals(EMPTY_SCENARIO, scenario2);
        Assert.assertFalse(scenarios.hasNext());
    }

    private void run_test_of_empty_scenario_with_no_attribute(String attribute)
            throws ParseException {
        // Given
        Element rootElement = document.createElement(TAG_FEATURE);
        Element scenarioElement = document.createElement(TAG_SCENARIO);
        if (!attribute.equals(ATTR_SCENARIO_NAME)) {
            scenarioElement.setAttribute(ATTR_SCENARIO_NAME, SCENARIO_NAME);
        }
        if (!attribute.equals(ATTR_SCENARIO_USER)) {
            scenarioElement.setAttribute(ATTR_SCENARIO_USER, SCENARIO_USER);
        }
        if (!attribute.equals(ATTR_SCENARIO_PASS)) {
            scenarioElement.setAttribute(ATTR_SCENARIO_PASS, SCENARIO_PASSWORD);
        }
        rootElement.appendChild(scenarioElement);

        // When
        XMLPatientExperienceFeatureParser parser = new XMLPatientExperienceFeatureParser();
        PatientExperienceFeature feature = parser.parse(rootElement);

        // Then
        List<PatientExperienceScenario> scenarios = feature.getScenarios();
        assertEquals(1, scenarios.size());
        assertEquals(EMPTY_SCENARIO, scenarios.get(0));
    }
}
