package com.olenick.avatar.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * "Simple" Report Filter.
 */
@XmlRootElement
public class ReportFilter implements Cloneable {
    private Calculation calculation;
    private DataSet dataSet;
    private Boolean itemsToIncludeCore;
    private Boolean itemsToIncludeCustom;
    private Boolean itemsToIncludeAncillary;
    private DateKey dateKey;
    private DateRangeGroupBy groupBy;
    private String surveyType;
    private String system;
    private List<String> departments;
    private List<String> factorComposites; // "Composite"
    private List<String> itemQuestions; // "Item"
    private List<String> locations;
    private List<String> organizations;
    private List<String> patientTypes;
    private List<ReportTabSpec> tabSpecs;
    private MonthSpec from;
    private MonthSpec to;
    private PatientDemographics demographics;
    private ProviderFilter providerFilter;
    private ResponseFilter responseFilter;

    public ReportFilter() {
        this.itemsToIncludeCore = false;
        this.itemsToIncludeCustom = false;
        this.itemsToIncludeAncillary = false;
    }

    public Calculation getCalculation() {
        return calculation;
    }

    @XmlElement(name = "calculation")
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    @XmlElement(name = "data-set")
    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public boolean getItemsToIncludeCore() {
        return this.itemsToIncludeCore;
    }

    @XmlElement(name = "items-to-include-core")
    public void setItemsToIncludeCore(Boolean check) {
        this.itemsToIncludeCore = check;
    }

    public Boolean getItemsToIncludeCustom() {
        return this.itemsToIncludeCustom;
    }

    @XmlElement(name = "items-to-include-custom")
    public void setItemsToIncludeCustom(Boolean check) {
        this.itemsToIncludeCustom = check;
    }

    public Boolean getItemsToIncludeAncillary() {
        return this.itemsToIncludeAncillary;
    }

    @XmlElement(name = "items-to-include-ancillary")
    public void setItemsToIncludeAncillary(Boolean check) {
        this.itemsToIncludeAncillary = check;
    }

    public DateKey getDateKey() {
        return dateKey;
    }

    @XmlElement(name = "date-key")
    public void setDateKey(DateKey dateKey) {
        this.dateKey = dateKey;
    }

    public DateRangeGroupBy getGroupBy() {
        return groupBy;
    }

    @XmlElement(name = "group-by")
    public void setGroupBy(DateRangeGroupBy groupBy) {
        this.groupBy = groupBy;
    }

    public String getSurveyType() {
        return surveyType;
    }

    @XmlElement(name = "survey-type")
    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getSystem() {
        return system;
    }

    @XmlElement(name = "system")
    public void setSystem(String system) {
        this.system = system;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(String... departments) {
        this.departments = Arrays.asList(departments);
    }

    @XmlElementWrapper(name = "departments")
    @XmlElement(name = "department")
    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<String> getFactorComposites() {
        return factorComposites;
    }

    public void setFactorComposites(String... factorComposites) {
        this.factorComposites = Arrays.asList(factorComposites);
    }

    @XmlElementWrapper(name = "factor-composites")
    @XmlElement(name = "factor-composite")
    public void setFactorComposites(List<String> factorComposites) {
        this.factorComposites = factorComposites;
    }

    public List<String> getItemQuestions() {
        return itemQuestions;
    }

    public void setItemQuestions(String... itemQuestions) {
        this.itemQuestions = Arrays.asList(itemQuestions);
    }

    @XmlElementWrapper(name = "item-questions")
    @XmlElement(name = "item-question")
    public void setItemQuestions(List<String> itemQuestions) {
        this.itemQuestions = itemQuestions;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(String... locations) {
        this.locations = Arrays.asList(locations);
    }

    @XmlElementWrapper(name = "locations")
    @XmlElement(name = "location")
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<String> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(String... organizations) {
        this.organizations = Arrays.asList(organizations);
    }

    @XmlElementWrapper(name = "organizations")
    @XmlElement(name = "organization")
    public void setOrganizations(List<String> organizations) {
        this.organizations = organizations;
    }

    public List<String> getPatientTypes() {
        return patientTypes;
    }

    public void setPatientTypes(String... patientTypes) {
        this.patientTypes = Arrays.asList(patientTypes);
    }

    @XmlElementWrapper(name = "patient-types")
    @XmlElement(name = "patient-type")
    public void setPatientTypes(List<String> patientTypes) {
        this.patientTypes = patientTypes;
    }

    public List<ReportTabSpec> getTabSpecs() {
        return tabSpecs;
    }

    public void setTabSpecs(ReportTabSpec... tabs) {
        this.tabSpecs = Arrays.asList(tabs);
    }

    @XmlElementWrapper(name = "tab-specs")
    @XmlElement(name = "tab-spec")
    public void setTabSpecs(List<ReportTabSpec> tabs) {
        this.tabSpecs = tabs;
    }

    public MonthSpec getFrom() {
        return from;
    }

    @XmlElement(name = "from")
    public void setFrom(MonthSpec from) {
        this.from = from;
    }

    public MonthSpec getTo() {
        return to;
    }

    @XmlElement(name = "to")
    public void setTo(MonthSpec to) {
        this.to = to;
    }

    public PatientDemographics getDemographics() {
        return demographics;
    }

    @XmlElement(name = "demographics")
    public void setDemographics(PatientDemographics demographics) {
        this.demographics = demographics;
    }

    public ProviderFilter getProviderFilter() {
        return providerFilter;
    }

    @XmlElement(name = "provider")
    public void setProviderFilter(ProviderFilter providerFilter) {
        this.providerFilter = providerFilter;
    }

    public ResponseFilter getResponseFilter() {
        return responseFilter;
    }

    @XmlElement(name = "response")
    public void setResponseFilter(ResponseFilter responseFilter) {
        this.responseFilter = responseFilter;
    }

    @Override
    @SuppressWarnings("super")
    public ReportFilter clone() {
        ReportFilter clone = new ReportFilter();
        clone.setCalculation(this.calculation);
        clone.setDataSet(this.dataSet);
        clone.setItemsToIncludeCore(this.itemsToIncludeCore);
        clone.setItemsToIncludeCustom(this.itemsToIncludeCustom);
        clone.setItemsToIncludeAncillary(this.itemsToIncludeAncillary);
        clone.setDateKey(this.dateKey);
        clone.setGroupBy(this.groupBy);
        clone.setSurveyType(surveyType);
        clone.setSystem(system);
        if (this.departments != null) {
            clone.setDepartments(new ArrayList<>(this.departments));
        }
        if (this.factorComposites != null) {
            clone.setFactorComposites(new ArrayList<>(this.factorComposites));
        }
        if (this.itemQuestions != null) {
            clone.setItemQuestions(new ArrayList<>(this.itemQuestions));
        }
        if (this.locations != null) {
            clone.setLocations(new ArrayList<>(this.locations));
        }
        if (this.organizations != null) {
            clone.setOrganizations(new ArrayList<>(this.organizations));
        }
        if (this.patientTypes != null) {
            clone.setPatientTypes(new ArrayList<>(this.patientTypes));
        }
        if (this.tabSpecs != null) {
            List<ReportTabSpec> clonedTabSpecs = new ArrayList<>(this.tabSpecs.size());
            for (ReportTabSpec tabSpec : this.tabSpecs) {
                clonedTabSpecs.add(tabSpec.clone());
            }
            clone.setTabSpecs(clonedTabSpecs);
        }
        if (this.from != null) {
            clone.setFrom(this.from.clone());
        }
        if (this.to != null) {
            clone.setTo(this.to.clone());
        }
        if (this.demographics != null) {
            clone.setDemographics(this.demographics.clone());
        }
        if (this.providerFilter != null) {
            clone.setProviderFilter(this.providerFilter.clone());
        }
        if (this.responseFilter != null) {
            clone.setResponseFilter(this.responseFilter.clone());
        }
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ReportFilter that = (ReportFilter) o;

        if (calculation != that.calculation)
            return false;
        if (dataSet != that.dataSet)
            return false;
        if (itemsToIncludeCore != that.itemsToIncludeCore)
            return false;
        if (itemsToIncludeCustom != that.itemsToIncludeCustom)
            return false;
        if (itemsToIncludeAncillary != that.itemsToIncludeAncillary)
            return false;
        if (dateKey != that.dateKey)
            return false;
        if (demographics != null ? !demographics.equals(that.demographics)
                : that.demographics != null)
            return false;
        if (departments != null ? !departments.equals(that.departments)
                : that.departments != null)
            return false;
        if (factorComposites != null ? !factorComposites
                .equals(that.factorComposites) : that.factorComposites != null)
            return false;
        if (from != null ? !from.equals(that.from) : that.from != null)
            return false;
        if (groupBy != that.groupBy)
            return false;
        if (itemQuestions != null ? !itemQuestions.equals(that.itemQuestions)
                : that.itemQuestions != null)
            return false;
        if (locations != null ? !locations.equals(that.locations)
                : that.locations != null)
            return false;
        if (organizations != null ? !organizations.equals(that.organizations)
                : that.organizations != null)
            return false;
        if (patientTypes != null ? !patientTypes.equals(that.patientTypes)
                : that.patientTypes != null)
            return false;
        if (providerFilter != null ? !providerFilter
                .equals(that.providerFilter) : that.providerFilter != null)
            return false;
        if (responseFilter != null ? !responseFilter
                .equals(that.responseFilter) : that.responseFilter != null)
            return false;
        if (surveyType != null ? !surveyType.equals(that.surveyType)
                : that.surveyType != null)
            return false;
        if (system != null ? !system.equals(that.system) : that.system != null)
            return false;
        if (tabSpecs != null ? !tabSpecs.equals(that.tabSpecs)
                : that.tabSpecs != null)
            return false;
        if (to != null ? !to.equals(that.to) : that.to != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = calculation != null ? calculation.hashCode() : 0;
        result = 31 * result + (dataSet != null ? dataSet.hashCode() : 0);
        result = 31 * result + (itemsToIncludeCore != null ? itemsToIncludeCore.hashCode() : 0);
        result = 31 * result + (itemsToIncludeCustom != null ? itemsToIncludeCustom.hashCode() : 0);
        result = 31 * result + (itemsToIncludeAncillary != null ? itemsToIncludeAncillary.hashCode() : 0);
        result = 31 * result + (dateKey != null ? dateKey.hashCode() : 0);
        result = 31 * result + (groupBy != null ? groupBy.hashCode() : 0);
        result = 31 * result + (surveyType != null ? surveyType.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result
                + (departments != null ? departments.hashCode() : 0);
        result = 31 * result
                + (factorComposites != null ? factorComposites.hashCode() : 0);
        result = 31 * result
                + (itemQuestions != null ? itemQuestions.hashCode() : 0);
        result = 31 * result + (locations != null ? locations.hashCode() : 0);
        result = 31 * result
                + (organizations != null ? organizations.hashCode() : 0);
        result = 31 * result
                + (patientTypes != null ? patientTypes.hashCode() : 0);
        result = 31 * result + (tabSpecs != null ? tabSpecs.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result
                + (demographics != null ? demographics.hashCode() : 0);
        result = 31 * result
                + (providerFilter != null ? providerFilter.hashCode() : 0);
        result = 31 * result
                + (responseFilter != null ? responseFilter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReportFilter{");
        sb.append("calculation=").append(calculation);
        sb.append(", dataSet=").append(dataSet);
        sb.append(", itemsToIncludeCore=").append(itemsToIncludeCore);
        sb.append(", itemsToIncludeCustom=").append(itemsToIncludeCustom);
        sb.append(", itemsToIncludeAncillary=").append(itemsToIncludeAncillary);
        sb.append(", dateKey=").append(dateKey);
        sb.append(", groupBy=").append(groupBy);
        sb.append(", surveyType='").append(surveyType).append('\'');
        sb.append(", system='").append(system).append('\'');
        sb.append(", departments=").append(departments);
        sb.append(", factorComposites=").append(factorComposites);
        sb.append(", itemQuestions=").append(itemQuestions);
        sb.append(", locations=").append(locations);
        sb.append(", organizations=").append(organizations);
        sb.append(", patientTypes=").append(patientTypes);
        sb.append(", tabSpecs=").append(tabSpecs);
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append(", demographics=").append(demographics);
        sb.append(", providerFilter=").append(providerFilter);
        sb.append(", responseFilter=").append(responseFilter);
        sb.append('}');
        return sb.toString();
    }
}
