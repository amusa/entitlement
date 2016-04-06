/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.Terminal;
import java.util.List;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface CommonServices<T> extends AbstractCrudServices<T> {

    public List<T> findByYearAndMonth(int year, int month);

    public T findByContractPeriod(int year, int month, Contract cs);

    public List<T> findByContractPeriod(int year, int month, FiscalArrangement fa);
        
    public T computeEntitlement(T production);

    public T createInstance();

    public T computeOpeningStock(T production);

    public T getPreviousMonthProduction(T production);

    public T computeClosingStock(T production);
        
    public T openingStockChanged(T production);

    public T computeAvailability(T production);

    public T computeLifting(T production);
    
    public T enrich(T production);
    
    public List<T> getTerminalProduction(int year, int month, Terminal terminal);
            
}
