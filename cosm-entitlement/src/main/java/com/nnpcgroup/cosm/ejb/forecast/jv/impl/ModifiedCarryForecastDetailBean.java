/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.ModifiedCarryForecastDetailServices;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecastDetail;

import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(ModifiedCarryForecastDetailServices.class)
public class ModifiedCarryForecastDetailBean extends ModifiedCarryForecastDetailServicesImpl implements ModifiedCarryForecastDetailServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryForecastDetailBean.class.getName());

    public ModifiedCarryForecastDetailBean() {
        super(ModifiedCarryForecastDetail.class);
        LOG.info("ProductionBean::constructor activated...");

    }

    @Override
    public ModifiedCarryForecastDetail createInstance() {
        return new ModifiedCarryForecastDetail();
    }

    
}
