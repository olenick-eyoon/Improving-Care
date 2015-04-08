package com.olenick.avatar.model.overview_values;

/**
 * Single Overview tab value.
 */
public class OverviewValue {
    private final float topBoxPercentage;
    private final long count;

    public OverviewValue(final long count, final float topBoxPercentage) {
        this.topBoxPercentage = topBoxPercentage;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public float getTopBoxPercentage() {
        return topBoxPercentage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OverviewValue{");
        sb.append("count=").append(count);
        sb.append(", %TB=").append(topBoxPercentage);
        sb.append('}');
        return sb.toString();
    }
}
