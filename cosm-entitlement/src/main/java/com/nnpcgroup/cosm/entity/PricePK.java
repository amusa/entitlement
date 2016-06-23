/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import java.io.Serializable;

/**
 *
 * @author 18359
 */
public class PricePK implements Serializable {

    private static final long serialVersionUID = 1L;
    private int periodYear;
    private int periodMonth;

    public PricePK() {
    }

    public PricePK(int periodYear, int periodMonth) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
    }

    public int getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }

    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.periodYear;
        hash = 31 * hash + this.periodMonth;
        return hash;
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
        final PricePK other = (PricePK) obj;
        if (this.periodYear != other.periodYear) {
            return false;
        }
        if (this.periodMonth != other.periodMonth) {
            return false;
        }
        return true;
    }

}
