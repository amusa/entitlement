/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvRegularForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.RegularForecast;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvRegularForecastServices.class)
public class RegularForecastBean extends JvRegularForecastServicesImpl implements JvRegularForecastServices {

    private static final Logger LOG = Logger.getLogger(RegularForecastBean.class.getName());

    public RegularForecastBean() {
        super(RegularForecast.class);
        LOG.info("ProductionBean::constructor activated...");

    }

    @Override
    public RegularForecast createInstance() {
        LOG.info("JvActualProductionBean::Creating new JvActualProduction Instance...");
        return new RegularForecast();
    }

    

    
    
}
