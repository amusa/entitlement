/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.PscForecastDetailServices;
import com.nnpcgroup.cosm.entity.forecast.jv.PscForecast;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Stateless
@Local(PscForecastDetailServices.class)
@Dependent
public abstract class PscForecastDetailServicesImpl extends ForecastDetailServicesImpl<PscForecast> implements PscForecastDetailServices, Serializable{

    private static final Logger LOG = Logger.getLogger(PscForecastDetailServicesImpl.class.getName());
    private static final long serialVersionUID = 8993596753945847377L;


    public PscForecastDetailServicesImpl(Class<PscForecast> entityClass) {
        super(entityClass);
    }
}
