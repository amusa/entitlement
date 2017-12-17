package com.cosm.common.domain.util;


import java.io.Serializable;

/**
 * Created by Ayemi on 20/03/2017.
 */
public class CacheKey implements Serializable {
    private ProductionSharingContract psc;
    private int year;
    private int month;

    public CacheKey() {
    }

    public CacheKey(ProductionSharingContract psc, int year, int month) {
        this.psc = psc;
        this.year = year;
        this.month = month;
    }

    public ProductionSharingContract getPsc() {
        return psc;
    }

    public void setPsc(ProductionSharingContract psc) {
        this.psc = psc;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheKey cacheKey = (CacheKey) o;

        if (year != cacheKey.year) return false;
        if (month != cacheKey.month) return false;
        return psc != null ? psc.equals(cacheKey.psc) : cacheKey.psc == null;
    }

    @Override
    public int hashCode() {
        int result = psc != null ? psc.hashCode() : 0;
        result = 31 * result + year;
        result = 31 * result + month;
        return result;
    }
}
