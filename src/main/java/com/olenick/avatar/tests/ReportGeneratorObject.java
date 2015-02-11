package com.olenick.avatar.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.olenick.avatar.reports.ReportGenerator;


public class ReportGeneratorObject {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createReportGenerator() {
		ReportGenerator reportGenerator = new ReportGenerator();
		assertNotNull(reportGenerator);
	}
	
	@Test
	public void printEmptyContent() {
		ReportGenerator reportGenerator = new ReportGenerator();
		assertEquals(reportGenerator.getContent(), "");
	}
	
	@Test
	public void addText() {
		ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.addText("Test");
		assertEquals(reportGenerator.getContent(), "Test");
	}
	
	@Test
	public void generateTerminalData() throws UnknownHostException {
		ReportGenerator reportGenerator = new ReportGenerator();
		assertNotEquals(reportGenerator.generateTerminalData(), "");
	}
	
	@Test
	public void addExecuterData() throws UnknownHostException {
		ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.addText(reportGenerator.generateTerminalData());
		assertNotEquals(reportGenerator.generateTerminalData(), "");
	}
	
	@Test
	public void addHeader() {
		ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.addHeader();
		assertNotEquals(reportGenerator.getContent(), "Computer Name;Date and Time;Login Page;iCare1 Page;iCare2 Page;Scenario Name;Overview;Composite;Side By Side;Demographics;PDFGeneration;");
	}

	@Test
	public void createWriter() throws FileNotFoundException, UnsupportedEncodingException {
		ReportGenerator reportGenerator = new ReportGenerator();
		PrintWriter writer = reportGenerator.createWriter("./resources/test.csv");
		assertNotNull(writer);
	}
	
	@Test
	public void printLineToFile() throws FileNotFoundException, UnsupportedEncodingException {
		ReportGenerator reportGenerator = new ReportGenerator();
		PrintWriter writer = reportGenerator.createWriter("./resources/test1.csv");
		reportGenerator.printLineToFile("TESTING LINE TESTING LINE");
		writer.close();
	}
	
	
}
