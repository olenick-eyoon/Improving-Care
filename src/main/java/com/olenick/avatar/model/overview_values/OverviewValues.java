package com.olenick.avatar.model.overview_values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.validation.constraints.NotNull;

/**
 * Overview tab values for a single view.
 */
public class OverviewValues {
    private final Map<String, OverviewValue> values;
    private boolean dataAvailable = true;

    public OverviewValues() {
        this.values = new ConcurrentSkipListMap<>();
    }

    public OverviewValue get(@NotNull final String itemName) {
        return this.values.get(itemName);
    }

    public List<String> getItemNames() {
        return new ArrayList<>(this.values.keySet());
    }

    public void set(@NotNull final String itemName,
            @NotNull final OverviewValue value) {
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
        final StringBuilder sb = new StringBuilder("OverviewValues{");
        sb.append("values=").append(values);
        sb.append(", dataAvailable=").append(dataAvailable);
        sb.append('}');
        return sb.toString();
    }
}
