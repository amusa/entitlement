/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.ejb.*;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.Production;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface JvProductionServices<T extends Production, E extends Contract> extends CommonServices<T> {

    public T grossProductionChanged(T production);

    public T liftingChanged(T production);

    public T computeStockAdjustment(T production);
}
