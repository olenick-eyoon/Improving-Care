package com.olenick.avatar.model.report_values;

import com.olenick.avatar.model.ReportTab;

/**
 * Single HCAHPS National value.
 */
public class HCAHPSNationalValue implements ReportValue {
    private final float adjustedScore;

    public HCAHPSNationalValue(final float adjustedScore) {
        this.adjustedScore = adjustedScore;
    }

    public float getAdjustedScore() {
        return adjustedScore;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HCAHPSNationalValue{");
        sb.append("adjustedScore=").append(adjustedScore);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public ReportTab getTab() {
        return ReportTab.HCAHPS_NATIONAL;
    }
}
