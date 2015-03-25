package com.olenick.avatar.model.totals;

import java.util.EnumMap;

import com.olenick.avatar.model.DataSet;
import com.olenick.avatar.model.Environment;

/**
 * Totals per Environment-DataSet combination.
 */
public class Totals {
    private EnumMap<Environment, DataSetTotals> totals;

    public Totals() {
        this.totals = new EnumMap<>(Environment.class);
    }

    public DataSetTotals get(Environment environment) {
        return this.totals.get(environment);
    }

    public Long get(Environment environment, DataSet dataSet) {
        DataSetTotals dataSetTotals = this.totals.get(environment);
        if (dataSetTotals != null) {
            return dataSetTotals.get(dataSet);
        }
        return null;
    }

    public void set(Environment environment, DataSetTotals dataSetTotals) {
        this.totals.put(environment, dataSetTotals);
    }

    public void set(Environment environment, DataSet dataSet, Long total) {
        DataSetTotals dataSetTotals = this.totals.get(environment);
        if (dataSetTotals == null) {
            dataSetTotals = new DataSetTotals();
            this.totals.put(environment, dataSetTotals);
        }
        dataSetTotals.set(dataSet, total);
    }
}
