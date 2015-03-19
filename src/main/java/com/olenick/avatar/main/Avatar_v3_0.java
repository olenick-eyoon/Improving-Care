package com.olenick.avatar.main;

import com.olenick.avatar.main.commands.ExecuteFeatureCommand;
import com.olenick.avatar.main.commands.GetSystemsTotalsCommand;

public class Avatar_v3_0 {
    private static final int ERROR_IN_ARGUMENTS = -1;

    private static void printUsage(int returnValue) {
        System.err.println("Usage: java -jar {THIS_JAR_NAME.jar} {ARGUMENTS}");
        System.err.println();
        System.err.println("Feature execution arguments: FEATURE_XML_FILENAME");
        System.err.println("  E.g.: java -jar avatar.jar feature1.xml");
        System.err.println("  E.g.: java -jar avatar.jar /xmls/all-1.xml");
        System.err.println();
        System.err.println("System totals arguments: SPEC_CSV_FILENAME");
        System.err.println("  E.g.: java -jar avatar.jar totals1.csv");
        System.exit(returnValue);
    }

    public static void main(String[] args) throws Exception {
        // TODO: Clean this mental stuff up.
        if (args.length < 1 || args[0] == null) {
            printUsage(ERROR_IN_ARGUMENTS);
        }

        if (args[0].toLowerCase().endsWith(".xml")) {
            new ExecuteFeatureCommand(args[0]).execute();
        } else if (args[0].toLowerCase().endsWith(".csv")) {
            new GetSystemsTotalsCommand(args[0]).execute();
        } else {
            printUsage(ERROR_IN_ARGUMENTS);
        }
    }
}
