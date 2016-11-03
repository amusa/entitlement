/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.ejb.CommonServices;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetail;

import java.io.Serializable;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface ForecastDetailServices<T extends ForecastDetail> extends CommonServices<T>, Serializable {

    public T computeGrossProduction(T forecast);
    
    public T computeDailyProduction(T forecast);

}
