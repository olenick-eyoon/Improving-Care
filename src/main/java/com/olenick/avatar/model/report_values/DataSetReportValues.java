package com.olenick.avatar.model.report_values;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.DataSet;

/**
 * Report values, per item name, for each data-set.
 */
public class DataSetReportValues {
    private Map<DataSet, ReportValues> values;

    public DataSetReportValues() {
        this.values = Collections
                .synchronizedMap(new EnumMap<DataSet, ReportValues>(
                        DataSet.class));
    }

    public ReportValues get(@NotNull final DataSet dataSet) {
        return this.values.get(dataSet);
    }

    public void set(@NotNull final DataSet dataSet,
            @NotNull final ReportValues reportValues) {
        this.values.put(dataSet, reportValues);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataSetReportValues{");
        sb.append("values=").append(values);
        sb.append('}');
        return sb.toString();
    }
}
