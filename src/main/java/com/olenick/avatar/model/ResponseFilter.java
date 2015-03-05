package com.olenick.avatar.model;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response filter.
 */
@XmlRootElement
public class ResponseFilter {
    private String surveyType;
    private String patientType;
    private String composite;
    private String item;
    private List<String> responses;

    public String getSurveyType() {
        return surveyType;
    }

    @XmlElement(name = "survey-type")
    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getPatientType() {
        return patientType;
    }

    @XmlElement(name = "patient-type")
    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getComposite() {
        return composite;
    }

    @XmlElement(name = "composite")
    public void setComposite(String composite) {
        this.composite = composite;
    }

    public String getItem() {
        return item;
    }

    @XmlElement(name = "item")
    public void setItem(String item) {
        this.item = item;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(String... responses) {
        this.responses = Arrays.asList(responses);
    }

    @XmlElementWrapper(name = "responses")
    @XmlElement(name = "response")
    public void setResponses(List<String> responses) {
        this.responses = responses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ResponseFilter that = (ResponseFilter) o;

        if (composite != null ? !composite.equals(that.composite)
                : that.composite != null)
            return false;
        if (item != null ? !item.equals(that.item) : that.item != null)
            return false;
        if (patientType != null ? !patientType.equals(that.patientType)
                : that.patientType != null)
            return false;
        if (responses != null ? !responses.equals(that.responses)
                : that.responses != null)
            return false;
        if (surveyType != null ? !surveyType.equals(that.surveyType)
                : that.surveyType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = surveyType != null ? surveyType.hashCode() : 0;
        result = 31 * result
                + (patientType != null ? patientType.hashCode() : 0);
        result = 31 * result + (composite != null ? composite.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (responses != null ? responses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseFilter{");
        sb.append("surveyType='").append(surveyType).append('\'');
        sb.append(", patientType='").append(patientType).append('\'');
        sb.append(", composite='").append(composite).append('\'');
        sb.append(", item='").append(item).append('\'');
        sb.append(", responses=").append(responses);
        sb.append('}');
        return sb.toString();
    }
}
