/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.domain.service.internal;


import com.cosm.common.domain.service.FiscalPeriodIf;

/**
 * @author 18359
 */
public class FiscalPeriod implements FiscalPeriodIf {
    private int year;
    private int month;
    private int refYear;

    public FiscalPeriod() {
    }

    public FiscalPeriod(int year, int month) {
        this.year = year;
        this.month = month;
        this.refYear = year;
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

    public int getRefYear() {
        return refYear;
    }

    public void setRefYear(int refYear) {
        this.refYear = refYear;
    }


    @Override
    public FiscalPeriod getPreviousFiscalPeriod() {
        if (month > 1) {
            --month;
        } else {
            month = 12;
            --year;
        }

        return this;
    }

    @Override
    public FiscalPeriod getNextFiscalPeriod() {
        month = (month % 12) + 1;

        if (year == 1) {
            ++year;
        }

        return this;
    }

    @Override
    public FiscalPeriod getPreviousYearFiscalPeriod() {
        return new FiscalPeriod(year - 1, 12);
    }

    @Override
    public boolean isCurrentYear() {
        return year == refYear;
    }
}
