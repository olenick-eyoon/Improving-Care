package com.olenick.avatar.model;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.olenick.avatar.model.java8.Month;
import com.olenick.avatar.parsers.xml.adapters.MonthSpecAdapter;

@XmlJavaTypeAdapter(MonthSpecAdapter.class)
public class MonthSpec implements Cloneable {
    private Month month;
    private String year;

    public MonthSpec() {}

    public MonthSpec(Month month, String year) {
        this.month = month;
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(final Month month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    @Override
    public MonthSpec clone() {
        return new MonthSpec(this.month, this.year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MonthSpec monthSpec = (MonthSpec) o;

        if (month != monthSpec.month)
            return false;
        if (year != null ? !year.equals(monthSpec.year)
                : monthSpec.year != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = month != null ? month.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MonthSpec{");
        sb.append("month=").append(month);
        sb.append(", year='").append(year).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
