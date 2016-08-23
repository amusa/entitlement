/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvCarryForecastServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvModifiedCarryForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecast;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvModifiedCarryForecastServices.class)
public class JvModifiedCarryForecastBean extends JvModifiedCarryForecastServicesImpl implements JvModifiedCarryForecastServices {

    private static final Logger LOG = Logger.getLogger(JvModifiedCarryForecastBean.class.getName());

    public JvModifiedCarryForecastBean() {
        super(ModifiedCarryForecast.class);
        LOG.info("ProductionBean::constructor activated...");

    }

    @Override
    public ModifiedCarryForecast createInstance() {
        return new ModifiedCarryForecast();
    }

    
}
