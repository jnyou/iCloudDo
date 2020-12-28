package com.blithe.cms.common.timespan;

import java.util.concurrent.TimeUnit;

public enum TimeUnitAbbr {
    NANOSECONDS(TimeUnit.NANOSECONDS, "ns"),
    MICROSECONDS(TimeUnit.MICROSECONDS, "mms"),
    MILLISECONDS(TimeUnit.MILLISECONDS, "ms"),
    SECONDS(TimeUnit.SECONDS, "s"),
    MINUTES(TimeUnit.MINUTES, "m"),
    HOURS(TimeUnit.HOURS, "h"),
    DAYS(TimeUnit.DAYS, "d");

    private TimeUnit unit;
    private String abbr;

    private TimeUnitAbbr(TimeUnit unit, String abbr) {
        this.unit = unit;
        this.abbr = abbr;
    }

    public TimeUnit toUnit() {
        return this.unit;
    }

    public static TimeUnitAbbr fromUnit(TimeUnit unit) {
        TimeUnitAbbr[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TimeUnitAbbr abbr = var1[var3];
            if (abbr.toUnit().equals(unit)) {
                return abbr;
            }
        }

        throw new IllegalArgumentException("Invalid time unit: " + unit);
    }

    public static TimeUnitAbbr parse(String s) {
        TimeUnitAbbr[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TimeUnitAbbr v = var1[var3];
            if (v.abbr.equals(s)) {
                return v;
            }
        }

        throw new IllegalArgumentException("Invalid time unit abbreviation: " + s);
    }

    public String toString() {
        return this.abbr;
    }
}