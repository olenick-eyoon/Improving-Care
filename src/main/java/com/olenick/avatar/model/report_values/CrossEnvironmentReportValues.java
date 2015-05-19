package com.olenick.avatar.model.report_values;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.Environment;

/**
 * Report values per Environment-DataSet-Item(String) combination.
 */
public class CrossEnvironmentReportValues {
    private Map<Environment, DataSetReportValues> allValues;

    public CrossEnvironmentReportValues() {
        this.allValues = Collections
                .synchronizedMap(new EnumMap<Environment, DataSetReportValues>(
                        Environment.class));
    }

    public DataSetReportValues get(@NotNull final Environment environment) {
        return this.allValues.get(environment);
    }

    public void set(@NotNull final Environment environment,
            @NotNull final DataSetReportValues dataSetReportValues) {
        this.allValues.put(environment, dataSetReportValues);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(
                "CrossEnvironmentReportValues{");
        sb.append("allValues=").append(allValues);
        sb.append('}');
        return sb.toString();
    }
}
