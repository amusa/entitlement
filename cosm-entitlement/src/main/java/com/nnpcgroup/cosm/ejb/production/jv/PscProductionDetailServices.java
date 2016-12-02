/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.entity.OilField;
import com.nnpcgroup.cosm.entity.production.jv.PscProductionDetail;

/**
 *
 * @author 18359
 * @param <T>
 *
 */
public interface PscProductionDetailServices<T extends PscProductionDetail> extends ProductionDetailServices<T> {

    public T computeClosingStock(T production);

    public T openingStockChanged(T production);

    public T computeAvailability(T production);

    public T computeLifting(T production);

    public void delete(int year, int month, OilField oilField);

}
