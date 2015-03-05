package com.olenick.avatar.parsers.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.olenick.avatar.uses.PatientExperienceScenario;

public class PatientExperienceScenarioAdapter extends
        XmlAdapter<AdaptedPatientExperienceScenario, PatientExperienceScenario> {
    @Override
    public PatientExperienceScenario unmarshal(
            AdaptedPatientExperienceScenario xmlObject) throws Exception {
        PatientExperienceScenario model = new PatientExperienceScenario(
                xmlObject.getName(), xmlObject.getUser(),
                xmlObject.getPassword());
        model.setReportFilter(xmlObject.getReportFilter());
        return model;
    }

    @Override
    public AdaptedPatientExperienceScenario marshal(
            PatientExperienceScenario model) throws Exception {
        AdaptedPatientExperienceScenario adapted = new AdaptedPatientExperienceScenario();
        adapted.setName(model.getName());
        adapted.setUser(model.getUser());
        adapted.setPassword(model.getPassword());
        adapted.setReportFilter(model.getReportFilter());
        return adapted;
    }
}
