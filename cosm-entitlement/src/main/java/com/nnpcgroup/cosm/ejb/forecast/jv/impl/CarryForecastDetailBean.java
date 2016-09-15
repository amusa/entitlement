/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.CarryForecastDetailServices;
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
@Local(CarryForecastDetailServices.class)
public class CarryForecastDetailBean extends CarryForecastDetailServicesImpl implements CarryForecastDetailServices, Serializable {

    private static final Logger LOG = Logger.getLogger(CarryForecastDetailBean.class.getName());
    private static final long serialVersionUID = -60220421460416910L;

    public CarryForecastDetailBean() {
        super(CarryForecastDetail.class);
        LOG.info("ProductionBean::constructor activated...");

    }
   
}
