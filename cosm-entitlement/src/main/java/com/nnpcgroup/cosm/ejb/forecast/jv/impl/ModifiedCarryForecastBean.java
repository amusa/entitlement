/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.ModifiedCarryForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecastDetail;

import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(ModifiedCarryForecastServices.class)
public class ModifiedCarryForecastBean extends ModifiedCarryForecastServicesImpl implements ModifiedCarryForecastServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryForecastBean.class.getName());

    public ModifiedCarryForecastBean() {
        super(ModifiedCarryForecastDetail.class);
        LOG.info("ProductionBean::constructor activated...");

    }

    @Override
    public ModifiedCarryForecastDetail createInstance() {
        return new ModifiedCarryForecastDetail();
    }

    
}
