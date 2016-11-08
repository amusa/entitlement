/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast;

import com.nnpcgroup.cosm.ejb.CommonServices;
import com.nnpcgroup.cosm.entity.forecast.ForecastEntitlement;
import java.io.Serializable;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface ForecastEntitlementServices<T extends ForecastEntitlement> extends CommonServices<T>, Serializable {

    public T computeEntitlement(T production, Double grossProd);

    public T enrich(T production, Double grossProd) throws Exception;

}
