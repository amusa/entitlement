/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.ejb.*;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface JvForecastServices<T extends Forecast> extends CommonServices<T> {

    public T computeGrossProduction(T forecast);    
   
}
