/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.RegularForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;

import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public abstract class RegularForecastServicesImpl extends ForecastServicesImpl<JvForecast> implements RegularForecastServices {

    private static final Logger LOG = Logger.getLogger(RegularForecastServicesImpl.class.getName());


    public RegularForecastServicesImpl(Class<JvForecast> entityClass) {
        super(entityClass);
    }

}
