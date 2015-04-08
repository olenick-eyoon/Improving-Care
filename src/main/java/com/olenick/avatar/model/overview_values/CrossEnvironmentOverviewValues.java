package com.olenick.avatar.model.overview_values;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.Environment;

/**
 * Overview values per Environment-DataSet-Item(String) combination.
 */
public class CrossEnvironmentOverviewValues {
    private Map<Environment, DataSetOverviewValues> allValues;

    public CrossEnvironmentOverviewValues() {
        this.allValues = Collections
                .synchronizedMap(new EnumMap<Environment, DataSetOverviewValues>(
                        Environment.class));
    }

    public DataSetOverviewValues get(@NotNull final Environment environment) {
        return this.allValues.get(environment);
    }

    public void set(@NotNull final Environment environment,
            @NotNull final DataSetOverviewValues dataSetOverviewValues) {
        this.allValues.put(environment, dataSetOverviewValues);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(
                "CrossEnvironmentOverviewValues{");
        sb.append("allValues=").append(allValues);
        sb.append('}');
        return sb.toString();
    }
}
