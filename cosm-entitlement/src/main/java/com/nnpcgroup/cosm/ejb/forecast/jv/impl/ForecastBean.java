/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.ejb.production.jv.impl.RegularProductionBean;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import com.nnpcgroup.cosm.entity.forecast.jv.RegularForecast;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvForecastServices.class)
public class ForecastBean extends JvForecastServicesImpl<Forecast> implements JvForecastServices<Forecast> {

    private static final Logger log = Logger.getLogger(RegularProductionBean.class.getName());

    public ForecastBean() {
        super(Forecast.class);
        log.info("ProductionBean::constructor activated...");

    }

    @Override
    public RegularForecast createInstance() {
        log.info("JvActualProductionBean::Creating new JvActualProduction Instance...");
        return new RegularForecast();
    }

    @Override
    public List<Forecast> findByYearAndMonth(int year, int month) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Forecast computeEntitlement(Forecast forecast) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Forecast computeClosingStock(Forecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Forecast computeAvailability(Forecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Forecast liftingChanged(Forecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Forecast grossProductionChanged(Forecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Forecast computeStockAdjustment(Forecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
