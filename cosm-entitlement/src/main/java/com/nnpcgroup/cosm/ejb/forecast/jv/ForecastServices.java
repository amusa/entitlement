/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.ejb.*;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetail;

import java.io.Serializable;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 * @param <T>
 */
@Dependent
public interface ForecastServices<T extends ForecastDetail> extends CommonServices<T>, Serializable {

    public T computeGrossProduction(T forecast);    
   
}
