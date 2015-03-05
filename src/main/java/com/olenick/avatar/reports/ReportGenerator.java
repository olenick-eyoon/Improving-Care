package com.olenick.avatar.reports;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.olenick.avatar.timer.Timer;

/**
 * Report Generator. Instances of this class are NOT thread-safe.
 */
@Deprecated
public class ReportGenerator {
    DateFormat fileSuffixDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    DateFormat csvDateFormat = new SimpleDateFormat("MM-dd-yyyy  HH:m:mss");
    StringBuilder builder;
    PrintWriter writer;

    public ReportGenerator() {
        builder = new StringBuilder();
    }

    public void addHeader() {
        addText("_Computer Name, "
                + "_Date and Time, "
                + "_Login Page, "
                // + "_iCare1 Page; "
                + "_iCare2 Page, " + "_Scenario Name, " + "_Overview, "
                + "_Composite, " + "_Side By Side, " + "_Demographics, "
                + "_Export Composite Tab, " + "_Export Side By Side, "
                + "_Export Demographics Tab");

        writer.println(getContent());
        builder.setLength(0);
    }

    public String addResults(String scenario, Timer timer)
            throws UnknownHostException {
        builder.append(scenario).append(",");
        long[] times = new long[] { timer.getOverviewTime(),
                timer.getCompositeTime(), timer.getSideBySideTime(),
                timer.getDemographicsTime(), timer.getExportCompositeTime(),
                timer.getExportSideBySideTime(),
                timer.getExportDemographicsTime() };
        for (long time : times) {
            if (time != 0) { // != or > ?
                builder.append(time);
            }
            builder.append(',');
        }
        return builder.toString();
    }

    public void addText(String textToAdd) {
        builder.append(textToAdd);
    }

    public PrintWriter createWriter(String filename)
            throws FileNotFoundException, UnsupportedEncodingException {
        writer = new PrintWriter(filename, "UTF-8");
        return writer;
    }

    public String generateTerminalData() throws UnknownHostException {
        return getComputerName() + "," + getFormattedDate() + ",";
    }

    public String getComputerName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    public String getContent() {
        return builder.toString();
    }

    public String getFileSuffixDate() {
        Date date = new Date();
        return fileSuffixDateFormat.format(date);
    }

    public String getFormattedDate() {
        Date date = new Date();
        return csvDateFormat.format(date);
    }

    public void wrapUpFile() {
        if (writer != null)
            writer.close();
    }

    public void printLineToFile(String lineToPrint) {
        addText(lineToPrint);
        writer.println(getContent());
        builder.setLength(0);
        // builder.replace(0, builder.length(), "");
    }
}
