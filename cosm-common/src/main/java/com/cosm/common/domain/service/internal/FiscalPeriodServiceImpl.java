/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.domain.service.internal;

import com.cosm.common.domain.service.FiscalPeriodService;

import javax.enterprise.context.Dependent;
import java.io.Serializable;

@Dependent
public class FiscalPeriodServiceImpl implements FiscalPeriodService, Serializable {

    @Override
    public FiscalPeriod getPreviousFiscalPeriod(FiscalPeriod fp) {
        int month = fp.getMonth();
        int year = fp.getYear();

        return getPreviousFiscalPeriod(year, month);
    }

    @Override
    public FiscalPeriod getPreviousFiscalPeriod(int year, int month) {
        if (month > 1) {
            --month;
        } else {
            month = 12;
            --year;
        }

        return new FiscalPeriod(year, month);
    }

    @Override
    public FiscalPeriod getNextFiscalPeriod(int year, int month) {
        int mt = (month % 12) + 1;
        int yr = year;

        if (mt == 1) {
            ++yr;
        }

        return new FiscalPeriod(yr, mt);
    }

    @Override
    public FiscalPeriod getPreviousFiscalPeriod(int year) {
        return new FiscalPeriod(year - 1, 12);
    }

    @Override
    public boolean isCurrentYear(int year, int month) {
        return getNextFiscalPeriod(year, month).getYear() == year;
    }
}
