/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvModifiedCarryForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecast;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public class JvModifiedCarryForecastServicesImpl extends JvAlternativeFundingForecastServicesImpl<ModifiedCarryForecast> implements JvModifiedCarryForecastServices{

    private static final Logger LOG = Logger.getLogger(JvModifiedCarryForecastServicesImpl.class.getName());

    public JvModifiedCarryForecastServicesImpl(Class<ModifiedCarryForecast> entityClass) {
        super(entityClass);
    }

    @Override
    public ModifiedCarryForecast createInstance() {
        return new ModifiedCarryForecast();
    }

   
    
}
