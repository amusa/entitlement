/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvRegularForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.RegularForecast;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 18359
 */
public class JvRegularForecastServicesImpl extends JvForecastServicesImpl<RegularForecast> implements JvRegularForecastServices {

    private static final Logger LOG = Logger.getLogger(JvRegularForecastServicesImpl.class.getName());

    @PersistenceContext(unitName = "entitlementPU")
    private EntityManager em;

    public JvRegularForecastServicesImpl(Class<RegularForecast> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOG.info("ForecastBean::setEntityManager() called...");
        return em;
    }

    @Override
    public List<RegularForecast> findByYearAndMonth(int year, int month) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegularForecast computeEntitlement(RegularForecast forecast) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegularForecast createInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegularForecast computeClosingStock(RegularForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegularForecast computeAvailability(RegularForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegularForecast liftingChanged(RegularForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegularForecast grossProductionChanged(RegularForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegularForecast computeStockAdjustment(RegularForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
