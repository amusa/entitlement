/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.ModifiedCarryForecastEntitlementServices;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecastEntitlement;

import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(ModifiedCarryForecastEntitlementServices.class)
public class ModifiedCarryForecastEntitlementBean extends ModifiedCarryForecastEntitlementServicesImpl implements ModifiedCarryForecastEntitlementServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryForecastEntitlementBean.class.getName());

    public ModifiedCarryForecastEntitlementBean() {
        super(ModifiedCarryForecastEntitlement.class);
    }

}
