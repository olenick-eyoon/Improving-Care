package com.olenick.avatar.main.commands.system_report_values;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.report_values.OverviewValue;

/**
 * Overview value extractor for Get-System-Report-Values command.
 */
public class OverviewValueExtractor implements ReportValueExtractor<OverviewValue> {
    private static final String TOTAL = "Total";

    public Number getValue(@NotNull final String itemName,
            @NotNull final OverviewValue overviewValue) {
        if (TOTAL.equals(itemName)) {
            return overviewValue.getCount();
        } else {
            return overviewValue.getTopBoxPercentage();
        }
    }
}
