/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.FiscalPeriod;

/**
 * @author Ayemi
 */
public interface FiscalPeriodService {

    FiscalPeriod getPreviousFiscalPeriod(FiscalPeriod fp);

    FiscalPeriod getPreviousFiscalPeriod(int year, int month);

    FiscalPeriod getNextFiscalPeriod(int year, int month);

    FiscalPeriod getPreviousFiscalPeriod(int year);

    boolean isCurrentYear(int year, int month);
}
