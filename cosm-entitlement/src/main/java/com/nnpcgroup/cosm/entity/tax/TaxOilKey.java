package com.nnpcgroup.cosm.entity.tax;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;

/**
 * Created by Ayemi on 20/03/2017.
 */
public class TaxOilKey {
    private ProductionSharingContract psc;
    private int year;
    private int month;

    public TaxOilKey() {
    }

    public TaxOilKey(ProductionSharingContract psc, int year, int month) {
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

        TaxOilKey taxOilKey = (TaxOilKey) o;

        if (year != taxOilKey.year) return false;
        if (month != taxOilKey.month) return false;
        return psc != null ? psc.equals(taxOilKey.psc) : taxOilKey.psc == null;
    }

    @Override
    public int hashCode() {
        int result = psc != null ? psc.hashCode() : 0;
        result = 31 * result + year;
        result = 31 * result + month;
        return result;
    }
}
