/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvCarryForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecast;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */

public abstract class JvCarryForecastServicesImpl extends JvAlternativeFundingForecastServicesImpl<CarryForecast> implements JvCarryForecastServices{

    private static final Logger LOG = Logger.getLogger(JvCarryForecastServicesImpl.class.getName());

    public JvCarryForecastServicesImpl(Class<CarryForecast> entityClass) {
        super(entityClass);
    }

   
    
    
}
