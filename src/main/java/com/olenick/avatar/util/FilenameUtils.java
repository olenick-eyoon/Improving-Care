package com.olenick.avatar.util;

import java.io.File;

/**
 * Filename Utils.
 */
public class FilenameUtils {
    public static String slashed(String dirName) {
        String slashed;
        if ("".equals(dirName) || dirName.endsWith(File.separator)) {
            slashed = dirName;
        } else {
            slashed = dirName + File.separator;
        }
        return slashed;
    }

    public static String basename(String fullFilename) {
        return new File(fullFilename).getName();
    }
}
