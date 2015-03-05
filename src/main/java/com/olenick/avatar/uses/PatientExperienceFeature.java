package com.olenick.avatar.uses;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "feature")
public class PatientExperienceFeature {
    private List<PatientExperienceScenario> scenarios;

    public List<PatientExperienceScenario> getScenarios() {
        return scenarios;
    }

    @XmlElement(name = "scenario")
    public void setScenarios(List<PatientExperienceScenario> scenarios) {
        this.scenarios = scenarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatientExperienceFeature feature = (PatientExperienceFeature) o;

        return !(scenarios != null ? !scenarios.equals(feature.scenarios)
                : feature.scenarios != null);
    }

    @Override
    public int hashCode() {
        return scenarios != null ? scenarios.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatientExperienceFeature{");
        sb.append("scenarios=").append(scenarios);
        sb.append('}');
        return sb.toString();
    }
}
