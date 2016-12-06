/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastEntitlementServices;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastEntitlement;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvForecastEntitlementServices.class)
public class JvForecastEntitlementServicesBean extends JvForecastEntitlementServicesImpl<JvForecastEntitlement> implements JvForecastEntitlementServices<JvForecastEntitlement>, Serializable {

    private static final Logger LOG = Logger.getLogger(JvForecastEntitlementServicesBean.class.getName());
    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastEntitlementServicesBean() {
        super(JvForecastEntitlement.class);
    }

}
