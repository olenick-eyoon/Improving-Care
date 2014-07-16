package com.olenick.avatar.reports;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportGenerator {

	StringBuilder builder;
	PrintWriter writer;
	
	public ReportGenerator() {
		 builder = new StringBuilder();
	}
	
	public String getContent() {
		return builder.toString();
	}

	public void addText(String textToAdd) {
		builder.append(textToAdd);
	}

	public String generateTerminalData() throws UnknownHostException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    Date date = new Date();
	    InetAddress.getLocalHost().getHostName();
		return InetAddress.getLocalHost().getHostName() + ";" + dateFormat.format(date) + ";";
	}

	public void addHeader() {
		addText(""
				+ "Computer Name; "
				+ "Date and Time; "
				+ "Login Page; "
				+ "iCare1 Page; "
				+ "iCare2 Page; "
				+ "Scenario Name; "
				+ "Overview; "
				+ "Composite; "
				+ "Side By Side; "
				+ "Demographics; "
				+ "PDFGeneration;");
		
	}

	public PrintWriter createWriter(String filename) throws FileNotFoundException, UnsupportedEncodingException {
		writer = new PrintWriter(filename, "UTF-8");
		return writer;
	}

	public void wrapUpFile() {
		if (writer != null) writer.close();
	}

	public void printLineToFile(String lineToPrint) {
		addText(lineToPrint);
		writer.println(getContent());
		builder.replace(0, builder.length()-1, "");
	}
}
