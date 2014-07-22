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
		return getComputerName() + ";" + getDate() + ";";
	}

	public String getComputerName() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	public void addHeader() {
		addText("Computer Name; "
				+ "Date and Time; "
				+ "Login Page; "
				+ "iCare1 Page; "
				+ "iCare2 Page; "
				+ "Scenario Name; "
				+ "Overview; "
				+ "Composite; "
				+ "Side By Side; "
				+ "Demographics; "
				+ "Export Composite Tab; "
				+ "Export Side By Side; "
				+ "Export Composite Tab");
		
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
		builder.setLength(0);
		//builder.replace(0, builder.length(), "");
	}
	
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    Date date = new Date();
	    return dateFormat.format(date);
	}
}
