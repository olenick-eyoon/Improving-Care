package com.olenick.avatar.parsers.xml;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;

import com.olenick.avatar.exceptions.ParseException;
import com.olenick.avatar.uses.PatientExperienceFeature;

/**
 * Patient Experience Use Case Parser from XML.
 */
public class XMLPatientExperienceFeatureParser {
    /*
     * private static final String PASSWORD = "password"; private static final
     * String SCENARIO = "scenario"; private static final String USER = "user";
     * // private static final String OPERATION = "operation"; // private static
     * final String PERIOD = "period"; // private static final String
     * PERIOD_DATA = "period-data"; // private static final String
     * SCHEDULER_NAME = "scheduler-name"; // private static final String
     * SCHEDULER_EMAIL = "scheduler-email";
     */

    private Unmarshaller jaxbUnmarshaller;

    public XMLPatientExperienceFeatureParser() throws ParseException {
        try {
            JAXBContext jaxbContext = JAXBContext
                    .newInstance(PatientExperienceFeature.class);
            this.jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException exception) {
            throw new ParseException("Could not instantiate", exception);
        }
    }

    public PatientExperienceFeature parse(URL xmlFileURL) throws ParseException {
        PatientExperienceFeature feature;
        try {
            feature = (PatientExperienceFeature) jaxbUnmarshaller
                    .unmarshal(xmlFileURL);
        } catch (JAXBException exception) {
            throw new ParseException("Could not parse file", exception);
        }
        return feature;
    }

    public PatientExperienceFeature parse(Node root) throws ParseException {
        PatientExperienceFeature feature;
        try {
            feature = (PatientExperienceFeature) jaxbUnmarshaller
                    .unmarshal(root);
        } catch (JAXBException exception) {
            throw new ParseException("Could not parse DOM", exception);
        }
        return feature;
    }
}
