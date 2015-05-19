package com.olenick.avatar.model.report_values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.validation.constraints.NotNull;

/**
 * Overview tab values for a single view.
 */
public class ReportValues {
    private final Map<String, ReportValue> values;
    private boolean dataAvailable = true;

    public ReportValues() {
        this.values = new ConcurrentSkipListMap<>();
    }

    public ReportValue get(@NotNull final String itemName) {
        return this.values.get(itemName);
    }

    public List<String> getItemNames() {
        return new ArrayList<>(this.values.keySet());
    }

    public void set(@NotNull final String itemName,
            @NotNull final ReportValue value) {
        this.values.put(itemName, value);
    }

    public boolean isDataAvailable() {
        return dataAvailable;
    }

    public void setDataAvailable(boolean dataAvailable) {
        this.dataAvailable = dataAvailable;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReportValues{");
        sb.append("values=").append(values);
        sb.append(", dataAvailable=").append(dataAvailable);
        sb.append('}');
        return sb.toString();
    }
}
