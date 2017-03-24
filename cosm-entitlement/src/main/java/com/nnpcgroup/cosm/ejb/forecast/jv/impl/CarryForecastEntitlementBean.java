/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.CarryForecastEntitlementServices;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecastEntitlement;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(CarryForecastEntitlementServices.class)
public class CarryForecastEntitlementBean extends CarryForecastEntitlementServicesImpl implements CarryForecastEntitlementServices, Serializable {

    private static final Logger LOG = Logger.getLogger(CarryForecastEntitlementBean.class.getName());
    private static final long serialVersionUID = -60220421460416910L;

    public CarryForecastEntitlementBean() {
        super(CarryForecastEntitlement.class);
        LOG.info("ProductionBean::constructor activated...");

    }

}
