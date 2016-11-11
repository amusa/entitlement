/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import com.nnpcgroup.cosm.entity.FiscalPeriod;

/**
 *
 * @author Ayemi
 */
public interface FiscalPeriodService {

    public FiscalPeriod getPreviousFiscalPeriod(FiscalPeriod fp);

    public FiscalPeriod getPreviousFiscalPeriod(int year, int month);

    public FiscalPeriod getNextFiscalPeriod(int year, int month);
}
