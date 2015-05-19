package com.olenick.avatar.main.commands.system_report_values;

import com.olenick.avatar.model.report_values.ReportValue;

import javax.validation.constraints.NotNull;

/**
 * Report value extractor for Get-System-Report-Values command.
 */
public interface ReportValueExtractor<T extends ReportValue> {
    public Number getValue(@NotNull final String itemName,
            @NotNull final T overviewValue);
}
