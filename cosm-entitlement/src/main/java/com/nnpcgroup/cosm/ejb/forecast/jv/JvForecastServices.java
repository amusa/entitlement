/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.ejb.*;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface JvForecastServices<T> extends ProductionServices<T> {

    public T computeGrossProduction(T production);
   
}
