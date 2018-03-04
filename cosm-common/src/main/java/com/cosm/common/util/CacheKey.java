package com.cosm.common.util;

import com.cosm.common.domain.model.ProductionSharingContractId;

import java.io.Serializable;

/**
 * Created by Ayemi on 20/02/2018.
 */
public class CacheKey implements Serializable {
    private ProductionSharingContractId pscId;
    private int year;
    private int month;

    public CacheKey() {
    }

    public CacheKey(ProductionSharingContractId pscId, int year, int month) {
        this.pscId = pscId;
        this.year = year;
        this.month = month;
    }

    public ProductionSharingContractId getPscId() {
        return pscId;
    }

    public void setPsc(ProductionSharingContractId pscId) {
        this.pscId = pscId;
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
        return pscId != null ? pscId.equals(cacheKey.pscId) : cacheKey.pscId == null;
    }

    @Override
    public int hashCode() {
        int result = pscId != null ? pscId.hashCode() : 0;
        result = 31 * result + year;
        result = 31 * result + month;
        return result;
    }
}
