/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.Terminal;

import java.util.List;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface CommonServices<T> extends AbstractCrudServices<T> {

    public List<T> findByYearAndMonth(int year, int month);

    public List<T> findAnnualProduction(int year, FiscalArrangement fa);

    public List<T> findByContractPeriod(int year, Contract cs);

    public List<T> findByContractPeriod(int year, int month, Contract cs);

    public List<T> findByContractPeriod(int year, int month, FiscalArrangement fa);

    public T findSingleByContractPeriod(int year, int month, Contract cs);

    public T getPreviousMonthProduction(T production);

    public T getNextMonthProduction(T production);

    public FiscalPeriod getPreviousFiscalPeriod(FiscalPeriod fp);

    public FiscalPeriod getPreviousFiscalPeriod(int year, int month);

    public FiscalPeriod getNextFiscalPeriod(int year, int month);

    public T enrich(T production) throws Exception;

    public List<T> getTerminalProduction(int year, int month, Terminal terminal);

    public void delete(int year, int month, FiscalArrangement fa);

    public void delete(List<T> jvDetails);

    public List<T> find(int year, int month, FiscalArrangement fa);

}
