package com.olenick.avatar.parsers.xml.adapters;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.olenick.avatar.model.ReportFilter;

public class AdaptedPatientExperienceScenario {
    private String name;
    private String password;
    private String user;
    private ReportFilter reportFilter;

    public AdaptedPatientExperienceScenario() {}

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @XmlAttribute(name = "password", required = true)
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    @XmlAttribute(name = "user", required = true)
    public void setUser(String user) {
        this.user = user;
    }

    public ReportFilter getReportFilter() {
        return reportFilter;
    }

    @XmlElement(name = "report-filter")
    public void setReportFilter(ReportFilter reportFilter) {
        this.reportFilter = reportFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdaptedPatientExperienceScenario that = (AdaptedPatientExperienceScenario) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        } else if (password != null ? !password.equals(that.password)
                : that.password != null) {
            return false;
        }
        if (reportFilter != null ? !reportFilter.equals(that.reportFilter)
                : that.reportFilter != null) {
            return false;
        } else if (user != null ? !user.equals(that.user) : that.user != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result
                + (reportFilter != null ? reportFilter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(
                "AdaptedPatientExperienceScenario{");
        sb.append("name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", reportFilter=").append(reportFilter);
        sb.append('}');
        return sb.toString();
    }
}
