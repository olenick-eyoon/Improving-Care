package com.olenick.avatar.parsers.xml.adapters;

import javax.xml.bind.annotation.XmlAttribute;

public class AdaptedMonthSpec {
    private String month;
    private String year;

    public AdaptedMonthSpec() {}

    public AdaptedMonthSpec(String month, String year) {
        this.month = month;
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    @XmlAttribute(name = "month")
    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    @XmlAttribute(name = "year")
    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdaptedMonthSpec that = (AdaptedMonthSpec) o;

        if (month != null ? !month.equals(that.month) : that.month != null) {
            return false;
        }
        if (year != null ? !year.equals(that.year) : that.year != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = month != null ? month.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }
}
