/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.Contract;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import java.util.List;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface ProductionServices<T extends Production> extends AbstractCrudServices<T> {

    public abstract List<T> findByYearAndMonth(int year, int month);

    public T findByContractPeriod(int year, int month, Contract cs);

    public List<T> findByContractPeriod(int year, int month, FiscalArrangement fa);
        
    public abstract T computeEntitlement(T production);

    public abstract T createInstance();

    public T computeOpeningStock(T production);

    public T getPreviousMonthProduction(T production);

    public T computeClosingStock(T production);

    public T computeGrossProduction(T production);
    
    public T openingStockChanged(T production);

    public T computeAvailability(T production);

    public T computeLifting(T production);
}
