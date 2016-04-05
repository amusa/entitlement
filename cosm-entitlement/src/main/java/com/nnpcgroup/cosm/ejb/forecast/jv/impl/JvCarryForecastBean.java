/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvCarryForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecast;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvCarryForecastServices.class)
public class JvCarryForecastBean extends JvCarryForecastServicesImpl implements JvCarryForecastServices {

    private static final Logger LOG = Logger.getLogger(JvCarryForecastBean.class.getName());

    public JvCarryForecastBean() {
        super(CarryForecast.class);
        LOG.info("ProductionBean::constructor activated...");

    }

    @Override
    public CarryForecast createInstance() {
        return new CarryForecast();
    }

    
}
