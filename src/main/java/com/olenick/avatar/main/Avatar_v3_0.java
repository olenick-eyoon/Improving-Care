package com.olenick.avatar.main;

import com.olenick.avatar.main.commands.ExecuteFeatureCommand;
import com.olenick.avatar.main.commands.GetSystemReportValuesCommand;

/**
 * TODO: Split this per command. it doesn't make sense having the same jar for
 * the both of them.
 */
public class Avatar_v3_0 {
    private static final int ERROR_IN_ARGUMENTS = -1;

    private static void printUsage(String errorMessage, int returnValue) {
        System.err.println(errorMessage);
        System.err.println();
        System.err.println("Usage: java -jar {THIS_JAR_NAME.jar} {ARGUMENTS}");
        System.err.println();
        System.err.println("Feature execution arguments: FEATURE_XML_FILENAME");
        System.err.println("  E.g.: java -jar avatar.jar feature1.xml");
        System.err.println("  E.g.: java -jar avatar.jar /xmls/all-1.xml");
        System.err.println();
        System.err
                .println("Report values arguments: SPEC_CSV_FILENAME EXCEL_FILENAME");
        System.err
                .println("  E.g.: java -jar avatar.jar totals1.csv totals1.xlsx");
        System.exit(returnValue);
    }

    public static void main(String[] args) throws Exception {
        // TODO: Clean this mental stuff up.
        if (args.length < 1 || args[0] == null) {
            printUsage("No arguments provided.", ERROR_IN_ARGUMENTS);
        }

        if (args[0].toLowerCase().endsWith(".xml")) {
            if (args.length != 1) {
                printUsage("Too many arguments.", ERROR_IN_ARGUMENTS);
            }
            new ExecuteFeatureCommand(args[0]).execute();
        } else if (args[0].toLowerCase().endsWith(".csv")) {
            if (args.length < 2 || args[1] == null) {
                printUsage("No Excel filename provided.", ERROR_IN_ARGUMENTS);
            } else if (args.length > 2) {
                printUsage("Too many arguments.", ERROR_IN_ARGUMENTS);
            }
            new GetSystemReportValuesCommand(args[0], args[1]).execute();
        } else {
            printUsage("Unexpected argument" + (args.length > 1 ? "s" : "")
                    + ".", ERROR_IN_ARGUMENTS);
        }
    }
}
