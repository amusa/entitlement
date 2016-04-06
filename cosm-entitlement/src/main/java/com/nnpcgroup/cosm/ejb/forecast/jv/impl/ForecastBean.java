/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.RegularForecast;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvForecast.class)
@Dependent
public class ForecastBean extends JvForecastServicesImpl<Forecast> implements JvForecast, Serializable{

    private static final Logger LOG = Logger.getLogger(ForecastBean.class.getName());

    public ForecastBean() {
        super(Forecast.class);
        LOG.info("Constructor activated...");

    }

    @Override
    public Forecast createInstance() {
        LOG.info("Creating new RegularForecast Instance...");
        return new RegularForecast();
    }
    
}
