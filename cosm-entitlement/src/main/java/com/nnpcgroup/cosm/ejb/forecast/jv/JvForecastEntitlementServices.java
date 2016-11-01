/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastEntitlement;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface JvForecastEntitlementServices<T extends JvForecastEntitlement> extends ForecastEntitlementServices<T> {

    public T computeOpeningStock(T production);

    public T computeClosingStock(T production);

    public T openingStockChanged(T production);

    public T computeAvailability(T production);

    public T computeLifting(T production);

    public T find(int year, int month, Contract contract);

    public void delete(int year, int month, Contract contract);

}
