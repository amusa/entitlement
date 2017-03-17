/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.ejb.*;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.production.jv.Production;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface ProductionServices<T extends Production> extends AbstractCrudServices<T>, Serializable {

    public T findByContractPeriod(int year, int month, FiscalArrangement fa);

    public void delete(int year, int month, FiscalArrangement fa);

    public List<T> findByYearAndMonth(int year, int month);

}
