package com.olenick.avatar.model.totals;

import java.util.EnumMap;

import com.olenick.avatar.model.DataSet;

/**
 * Map of Overview totals for each data-set.
 */
public class DataSetTotals {
    private EnumMap<DataSet, Long> totals;

    public DataSetTotals() {
        this.totals = new EnumMap<>(DataSet.class);
    }

    public Long get(DataSet dataSet) {
        return this.totals.get(dataSet);
    }

    public void set(DataSet dataSet, Long total) {
        this.totals.put(dataSet, total);
    }
}
