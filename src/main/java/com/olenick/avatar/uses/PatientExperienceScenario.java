package com.olenick.avatar.uses;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.olenick.avatar.model.ReportFilter;
import com.olenick.avatar.parsers.xml.adapters.PatientExperienceScenarioAdapter;

/**
 * Patient Experience Use Case
 */
@XmlJavaTypeAdapter(PatientExperienceScenarioAdapter.class)
public class PatientExperienceScenario {
    private ReportFilter reportFilter;
    private String password;
    private String name;
    private String user;

    public PatientExperienceScenario(final String name, final String user,
            final String password) {
        this.name = name;
        this.user = user;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return password;
    }

    public ReportFilter getReportFilter() {
        return this.reportFilter;
    }

    public void setReportFilter(ReportFilter reportFilter) {
        this.reportFilter = reportFilter;
    }

    public String getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatientExperienceScenario that = (PatientExperienceScenario) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (password != null ? !password.equals(that.password)
                : that.password != null) {
            return false;
        }
        if (reportFilter != null ? !reportFilter.equals(that.reportFilter)
                : that.reportFilter != null) {
            return false;
        }
        if (user != null ? !user.equals(that.user) : that.user != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = reportFilter != null ? reportFilter.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatientExperienceScenario{");
        sb.append("reportFilter=").append(reportFilter);
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
