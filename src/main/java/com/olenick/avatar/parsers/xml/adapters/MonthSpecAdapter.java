package com.olenick.avatar.parsers.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.olenick.avatar.model.MonthSpec;
import com.olenick.avatar.model.java8.Month;

/**
 * MonthSpec XML Adapter
 */
public class MonthSpecAdapter extends XmlAdapter<AdaptedMonthSpec, MonthSpec> {
    @Override
    public MonthSpec unmarshal(AdaptedMonthSpec xmlObject) throws Exception {
        return new MonthSpec(Month.of(Integer.valueOf(xmlObject.getMonth())),
                xmlObject.getYear());
    }

    @Override
    public AdaptedMonthSpec marshal(MonthSpec model) throws Exception {
        return new AdaptedMonthSpec(String.format("%02d", model.getMonth()
                .getValue()), model.getYear());
    }
}
