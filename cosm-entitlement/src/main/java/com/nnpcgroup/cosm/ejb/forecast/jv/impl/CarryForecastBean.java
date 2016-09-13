/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.CarryForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecastDetail;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */

@Stateless
@Local(CarryForecastServices.class)
public class CarryForecastBean extends CarryForecastServicesImpl implements CarryForecastServices, Serializable {

    private static final Logger LOG = Logger.getLogger(CarryForecastBean.class.getName());
    private static final long serialVersionUID = -60220421460416910L;

    public CarryForecastBean() {
        super(CarryForecastDetail.class);
        LOG.info("ProductionBean::constructor activated...");

    }

    //@Override
    public CarryForecastDetail createInstance() {
        return new CarryForecastDetail();
    }

    
}
