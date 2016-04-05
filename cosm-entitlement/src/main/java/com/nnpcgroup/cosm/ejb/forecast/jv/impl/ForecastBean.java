/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecast;
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
@Local(JvForecast.class)
public class ForecastBean extends JvForecastServicesImpl<Forecast> implements JvForecast{

    private static final Logger log = Logger.getLogger(ForecastBean.class.getName());

    public ForecastBean() {
        super(Forecast.class);
        log.info("Constructor activated...");

    }

    @Override
    public Forecast createInstance() {
        log.info("Creating new RegularForecast Instance...");
        return new RegularForecast();
    }

    @Override
    public List<Forecast> findByYearAndMonth(int year, int month) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
   
}
