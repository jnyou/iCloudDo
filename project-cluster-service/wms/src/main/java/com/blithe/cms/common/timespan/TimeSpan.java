package com.blithe.cms.common.timespan;

import java.util.concurrent.TimeUnit;

public class TimeSpan implements Comparable<Object> {
    private static final TimeUnit DEFAULT_UNIT;
    private TimeUnit unit;
    private long numeral;

    private TimeSpan() {
        this.unit = DEFAULT_UNIT;
    }

    public TimeSpan(long n, TimeUnit u) {
        this();
        this.numeral = n > 0L ? n : 0L;
        this.unit = u;
    }

    public TimeSpan(TimeSpan span) {
        this(span.numeral, span.unit);
    }

    public TimeUnit getUnit() {
        return this.unit;
    }

    public long getNumeral() {
        return this.numeral;
    }

    public String toString() {
        return (new Long(this.numeral)).toString().trim() + TimeUnitAbbr.fromUnit(this.unit);
    }

    public static TimeSpan parseValue(String s) throws IllegalArgumentException {
        if (s == null) {
            throw new IllegalArgumentException("null");
        } else {
            int len = s.length();

            int index;
            for(index = 0; index < len && Character.isDigit(s.charAt(index)); ++index) {
            }

            String numStr = s.substring(0, index);
            String unitStr = s.substring(index, len).trim();

            long num;
            try {
                num = Long.parseLong(numStr);
            } catch (NumberFormatException var8) {
                throw new IllegalArgumentException("numeral format error: " + numStr);
            }

            TimeUnit u;
            if (unitStr.length() == 0 && num == 0L) {
                u = DEFAULT_UNIT;
            } else {
                u = TimeUnitAbbr.parse(unitStr).toUnit();
            }

            return new TimeSpan(num, u);
        }
    }

    public long toMicros() {
        return this.unit.toMicros(this.numeral);
    }

    public long toNanos() {
        return this.unit.toNanos(this.numeral);
    }

    public long toMillis() {
        return this.unit.toMillis(this.numeral);
    }

    public long toSeconds() {
        return this.unit.toSeconds(this.numeral);
    }

    public long toMinutes() {
        return this.unit.toMinutes(this.numeral);
    }

    public long toHours() {
        return this.unit.toHours(this.numeral);
    }

    public long toDays() {
        return this.unit.toDays(this.numeral);
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (!(other instanceof TimeSpan)) {
            return false;
        } else {
            return this.toNanos() == ((TimeSpan)other).toNanos();
        }
    }

    public int compareTo(Object other) {
        if (!(other instanceof TimeSpan)) {
            throw new IllegalArgumentException("a TimeSpan value cannot compare to a null value");
        } else {
            long r = this.toNanos() - ((TimeSpan)other).toNanos();
            if (r == 0L) {
                return 0;
            } else {
                return r > 0L ? 1 : -1;
            }
        }
    }

    static {
        DEFAULT_UNIT = TimeUnit.SECONDS;
    }
}