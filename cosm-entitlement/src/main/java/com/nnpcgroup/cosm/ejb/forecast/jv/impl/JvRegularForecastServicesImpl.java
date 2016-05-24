/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvRegularForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.RegularForecast;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 */
@Dependent
public abstract class JvRegularForecastServicesImpl extends JvForecastServicesImpl<RegularForecast> implements JvRegularForecastServices {

    private static final Logger LOG = Logger.getLogger(JvRegularForecastServicesImpl.class.getName());

//    @PersistenceContext(unitName = "entitlementPU")
//    private EntityManager em;

    public JvRegularForecastServicesImpl(Class<RegularForecast> entityClass) {
        super(entityClass);
    }

//    @Override
//    protected EntityManager getEntityManager() {
//        LOG.info("ForecastBean::setEntityManager() called...");
//        return em;
//    }

    
}
