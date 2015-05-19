package com.olenick.avatar.main.commands.system_report_values;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.report_values.HCAHPSNationalValue;

/**
 * HCAHPS National value extractor for Get-System-Report-Values command.
 */
public class HCAHPSNationalValueExtractor implements
        ReportValueExtractor<HCAHPSNationalValue> {
    private static final String TOTAL = "Total";

    public Number getValue(@NotNull final String itemName,
            @NotNull final HCAHPSNationalValue hcahpsNationalValue) {
        return hcahpsNationalValue.getAdjustedScore();
    }
}
