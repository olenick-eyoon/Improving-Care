package com.olenick.avatar.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Patient Demographics. To be used in the report filter.
 */
@XmlRootElement()
public class PatientDemographics implements Cloneable {
    private List<String> admissionSources;
    private List<String> ages;
    private List<String> dischargeStatuses;
    private List<String> genders;
    private List<String> languages;
    private List<String> lengthsOfStay;
    private List<String> races;
    private List<String> serviceLines;

    public PatientDemographics() {}

    public PatientDemographics(List<String> admissionSources,
            List<String> ages, List<String> dischargeStatuses,
            List<String> genders, List<String> languages,
            List<String> lengthsOfStay, List<String> races,
            List<String> serviceLines) {
        this.admissionSources = admissionSources;
        this.ages = ages;
        this.dischargeStatuses = dischargeStatuses;
        this.genders = genders;
        this.languages = languages;
        this.lengthsOfStay = lengthsOfStay;
        this.races = races;
        this.serviceLines = serviceLines;
    }

    public List<String> getAdmissionSources() {
        return admissionSources;
    }

    public void setAdmissionSources(String... admissionSources) {
        this.admissionSources = Arrays.asList(admissionSources);
    }

    @XmlElementWrapper(name = "admission-sources")
    @XmlElement(name = "admission-source")
    public void setAdmissionSources(List<String> admissionSources) {
        this.admissionSources = admissionSources;
    }

    public List<String> getAges() {
        return ages;
    }

    public void setAges(String... ages) {
        this.ages = Arrays.asList(ages);
    }

    @XmlElementWrapper(name = "ages")
    @XmlElement(name = "age")
    public void setAges(List<String> ages) {
        this.ages = ages;
    }

    public List<String> getDischargeStatuses() {
        return dischargeStatuses;
    }

    public void setDischargeStatuses(String... dischargeStatuses) {
        // Yes, "statuses" IS a word.
        this.dischargeStatuses = Arrays.asList(dischargeStatuses);
    }

    @XmlElementWrapper(name = "discharge-statuses")
    @XmlElement(name = "discharge-status")
    public void setDischargeStatuses(List<String> dischargeStatuses) {
        // Yes, "statuses" IS a word.
        this.dischargeStatuses = dischargeStatuses;
    }

    public List<String> getGenders() {
        return genders;
    }

    public void setGenders(String... genders) {
        this.genders = Arrays.asList(genders);
    }

    @XmlElementWrapper(name = "genders")
    @XmlElement(name = "gender")
    public void setGenders(List<String> genders) {
        this.genders = genders;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(String... languages) {
        this.languages = Arrays.asList(languages);
    }

    @XmlElementWrapper(name = "languages")
    @XmlElement(name = "language")
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getLengthsOfStay() {
        return lengthsOfStay;
    }

    public void setLengthsOfStay(String... lengthsOfStay) {
        this.lengthsOfStay = Arrays.asList(lengthsOfStay);
    }

    @XmlElementWrapper(name = "lengths-of-stay")
    @XmlElement(name = "length-of-stay")
    public void setLengthsOfStay(List<String> lengthsOfStay) {
        this.lengthsOfStay = lengthsOfStay;
    }

    public List<String> getRaces() {
        return races;
    }

    public void setRaces(String... races) {
        this.races = Arrays.asList(races);
    }

    @XmlElementWrapper(name = "races")
    @XmlElement(name = "race")
    public void setRaces(List<String> races) {
        this.races = races;
    }

    public List<String> getServiceLines() {
        return serviceLines;
    }

    public void setServiceLines(String... serviceLines) {
        this.serviceLines = Arrays.asList(serviceLines);
    }

    @XmlElementWrapper(name = "service-lines")
    @XmlElement(name = "service-line")
    public void setServiceLines(List<String> serviceLines) {
        this.serviceLines = serviceLines;
    }

    @Override
    @SuppressWarnings("super")
    public PatientDemographics clone() {
        PatientDemographics clone = new PatientDemographics();
        if (this.admissionSources != null) {
            clone.setAdmissionSources(new ArrayList<>(this.admissionSources));
        }
        if (this.ages != null) {
            clone.setAges(new ArrayList<>(this.ages));
        }
        if (this.dischargeStatuses != null) {
            clone.setDischargeStatuses(new ArrayList<>(this.dischargeStatuses));
        }
        if (this.genders != null) {
            clone.setGenders(new ArrayList<>(this.genders));
        }
        if (this.languages != null) {
            clone.setLanguages(new ArrayList<>(this.languages));
        }
        if (this.lengthsOfStay != null) {
            clone.setLengthsOfStay(new ArrayList<>(this.lengthsOfStay));
        }
        if (this.races != null) {
            clone.setRaces(new ArrayList<>(this.races));
        }
        if (this.serviceLines != null) {
            clone.setServiceLines(new ArrayList<>(this.serviceLines));
        }
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PatientDemographics that = (PatientDemographics) o;

        if (admissionSources != null ? !admissionSources
                .equals(that.admissionSources) : that.admissionSources != null) {
            return false;
        }
        if (ages != null ? !ages.equals(that.ages) : that.ages != null) {
            return false;
        }
        if (dischargeStatuses != null ? !dischargeStatuses
                .equals(that.dischargeStatuses)
                : that.dischargeStatuses != null) {
            return false;
        }
        if (genders != null ? !genders.equals(that.genders)
                : that.genders != null) {
            return false;
        }
        if (languages != null ? !languages.equals(that.languages)
                : that.languages != null) {
            return false;
        }
        if (lengthsOfStay != null ? !lengthsOfStay.equals(that.lengthsOfStay)
                : that.lengthsOfStay != null) {
            return false;
        }
        if (races != null ? !races.equals(that.races) : that.races != null) {
            return false;
        }
        if (serviceLines != null ? !serviceLines.equals(that.serviceLines)
                : that.serviceLines != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = admissionSources != null ? admissionSources.hashCode() : 0;
        result = 31 * result + (ages != null ? ages.hashCode() : 0);
        result = 31
                * result
                + (dischargeStatuses != null ? dischargeStatuses.hashCode() : 0);
        result = 31 * result + (genders != null ? genders.hashCode() : 0);
        result = 31 * result + (languages != null ? languages.hashCode() : 0);
        result = 31 * result
                + (lengthsOfStay != null ? lengthsOfStay.hashCode() : 0);
        result = 31 * result + (races != null ? races.hashCode() : 0);
        result = 31 * result
                + (serviceLines != null ? serviceLines.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatientDemographics{");
        sb.append("admissionSources=").append(admissionSources);
        sb.append(", ages=").append(ages);
        sb.append(", dischargeStatuses=").append(dischargeStatuses);
        sb.append(", genders=").append(genders);
        sb.append(", languages=").append(languages);
        sb.append(", lengthsOfStay=").append(lengthsOfStay);
        sb.append(", races=").append(races);
        sb.append(", serviceLines=").append(serviceLines);
        sb.append('}');
        return sb.toString();
    }
}
