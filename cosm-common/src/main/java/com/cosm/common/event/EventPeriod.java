package com.cosm.common.event;

import javax.json.JsonObject;

public class EventPeriod {

    private int year;
    private int month;

    public EventPeriod(int year, int month) {
        super();
        this.year = year;
        this.month = month;
    }

    public EventPeriod(JsonObject jsonObject) {
        this(jsonObject.getInt("year"), jsonObject.getInt("month"));
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + month;
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EventPeriod other = (EventPeriod) obj;
        if (month != other.month) {
            return false;
        }
        if (year != other.year) {
            return false;
        }
        return true;
    }

}
