package com.olenick.avatar.model.overview_values;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.DataSet;

/**
 * Overview values, per item name, for each data-set.
 */
public class DataSetOverviewValues {
    private Map<DataSet, OverviewValues> values;

    public DataSetOverviewValues() {
        this.values = Collections
                .synchronizedMap(new EnumMap<DataSet, OverviewValues>(
                        DataSet.class));
    }

    public OverviewValues get(@NotNull final DataSet dataSet) {
        return this.values.get(dataSet);
    }

    public void set(@NotNull final DataSet dataSet,
            @NotNull final OverviewValues overviewValues) {
        this.values.put(dataSet, overviewValues);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataSetOverviewValues{");
        sb.append("values=").append(values);
        sb.append('}');
        return sb.toString();
    }
}
