 package com.olenick.avatar.parsers.xml;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class XMLParser {

	private Document xmlFile;
	
	public Document loadFile(String xmlFilePath) {
		SAXBuilder builder = new SAXBuilder();
		try {
	        // Parses the file supplied into a JDOM document
	        xmlFile = builder.build(new File(xmlFilePath));
	        return xmlFile;
	    } catch (Exception e) {
	    	e.getMessage();
	    }
	    return null;
	}

	public Element getRootElement(String xmlFilePath) {
		xmlFile = loadFile(xmlFilePath);
		return xmlFile.getRootElement();
	}

	public String getAttributeFromXML(Element rootElement, String attribute) {
		return rootElement.getChildText(attribute);
	}


	
	public String getScenario(Element rootElement) {
		return getAttributeFromXML(rootElement, "scenario");
	}

	public String getSystem(Element rootElement) {
		return getAttributeFromXML(rootElement, "system");
	}
	
	public String[] getOrganization(Element rootElement) {
		String data = getAttributeFromXML(rootElement, "organization");
		String[] output = data.split("!");
		return output;
	}

	public String[] getDepartment(Element rootElement) {
		String data = getAttributeFromXML(rootElement, "department");
		String[] output = data.split("!");
		return output;
	}

	public String[] getLocation(Element rootElement) {
		String data = getAttributeFromXML(rootElement, "location");
		String[] output = data.split("!");
		return output;
	}

	public String getSurveyType(Element rootElement) {
		return getAttributeFromXML(rootElement, "survey-type");
	}

	public String getTab(Element rootElement) {
		return getAttributeFromXML(rootElement, "tab");
	}
	
	public String[] getTabs(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "tab");
		String[] output = data.split("!");
		return output;
	}
	
	public String[] getPatientType(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-type");
		String[] output = data.split("!");
		return output;
	}

	public String[] getFactorComposite(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "factor-composite");
		String[] output = data.split("!");
		return output;
	}

	public String[] getItemQuestion(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "item-question");
		String[] output = data.split("!");
		return output;
	}

	public String getCalculation(Element rootElement) {
		return getAttributeFromXML(rootElement, "calculation");
	}
	
	public String getFromMonth(Element rootElement) {
		return getAttributeFromXML(rootElement, "from-month");
	}

	public String getFromYear(Element rootElement) {
		return getAttributeFromXML(rootElement, "from-year");
	}

	public String getToMonth(Element rootElement) {
		return getAttributeFromXML(rootElement, "to-month");
	}

	public String getToYear(Element rootElement) {
		return getAttributeFromXML(rootElement, "to-year");
	}

	public String getGroupBy(Element rootElement) {
		return getAttributeFromXML(rootElement, "group-by");
	}

	public String[] getPatientAdmission(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-admission");
		String[] output = data.split("!");
		return output;
	}
	public String[] getPatientAge(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-age");
		String[] output = data.split("!");
		return output;
	}
	public String[] getPatientDischarge(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-discharge");
		String[] output = data.split("!");
		return output;
	}
	public String[] getPatientGender(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-gender");
		String[] output = data.split("!");
		return output;
	}
	public String[] getPatientLanguage(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-language");
		String[] output = data.split("!");
		return output;
	}
	public String[] getPatientLength(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-length");
		String[] output = data.split("!");
		return output;
	}
	public String[] getPatientRace(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-race");
		String[] output = data.split("!");
		return output;
	}
	public String[] getPatientService(Element rootElement) {
		String data =  getAttributeFromXML(rootElement, "patient-service");
		String[] output = data.split("!");
		return output;
	}

	public String getFilter(Element rootElement) {
		return getAttributeFromXML(rootElement, "filter");
	}

	public String getUser(Element rootElement) {
		return getAttributeFromXML(rootElement, "user");
	}

	public String getPassword(Element rootElement) {
		return getAttributeFromXML(rootElement, "password");
	}

	public String getOperation(Element rootElement) {
		return getAttributeFromXML(rootElement, "operation");
	}
	
	public String getPeriod(Element rootElement) {
		return getAttributeFromXML(rootElement, "period");
	}

	public String getPeriodData(Element rootElement) {
		return getAttributeFromXML(rootElement, "period-data");
	}

	public String getSchedulerName(Element rootElement) {
		return getAttributeFromXML(rootElement, "scheduler-name");
	}

	public String getSchedulerEmail(Element rootElement) {
		return getAttributeFromXML(rootElement, "scheduler-email");
	}
	
	
}
