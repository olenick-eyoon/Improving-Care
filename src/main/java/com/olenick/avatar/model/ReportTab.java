package com.olenick.avatar.model;

/**
 * Report tab names
 */
public enum ReportTab {
    COMMENTS(true), COMPOSITE(true), DEMOGRAPHICS(true), HCAHPS_NATIONAL(false), IMPROVEMENT(
            true), OVERVIEW(false), PREMIUM(false), SIDE_BY_SIDE(true);

    private boolean exportsToPDF;

    private ReportTab(boolean exportsToPDF) {
        this.exportsToPDF = exportsToPDF;
    }

    public boolean isCapableOfExportingToPDF() {
        return this.exportsToPDF;
    }
}
