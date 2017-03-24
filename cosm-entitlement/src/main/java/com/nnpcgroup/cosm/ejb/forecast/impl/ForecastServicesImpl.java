/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.impl;

import com.nnpcgroup.cosm.ejb.forecast.ForecastServices;
import com.nnpcgroup.cosm.ejb.impl.AbstractCrudServicesImpl;
import com.nnpcgroup.cosm.entity.forecast.Forecast;
import java.io.Serializable;

/**
 * @param <T>
 * @author 18359
 */
public abstract class ForecastServicesImpl<T extends Forecast> extends AbstractCrudServicesImpl<T> implements ForecastServices<T>, Serializable {

    public ForecastServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }
}
