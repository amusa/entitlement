/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.entity.contract.JvContract;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionEntitlement;

/**
 *
 * @author 18359
 * @param <T>
 * @param <E>
 */
public interface JvProductionEntitlementServices<T extends JvProductionEntitlement, E extends JvContract> extends ProductionEntitlementServices<T> {

    public T computeOpeningStock(T production);

    public T computeClosingStock(T production);

    public T openingStockChanged(T production);

    public T computeAvailability(T production);

    public T computeLifting(T production);

    public T find(int year, int month, E contract);

    public void delete(int year, int month, E contract);

    public T computeOperatorDeclaredEquity(T production, Double operatorDeclaredVol);

    public T computeOperatorDeclaredEquity(T production);

    public T computeOverlift(T production);

//    public T grossProductionChanged(T production);
    public T operatorDeclaredVolumeChanged(T production, Double operatorDeclaredVol);

}
