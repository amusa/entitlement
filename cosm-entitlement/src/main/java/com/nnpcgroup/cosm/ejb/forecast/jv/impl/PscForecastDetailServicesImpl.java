/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.PscForecastDetailServices;
import com.nnpcgroup.cosm.entity.forecast.jv.PscForecast;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

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
public class PscForecastDetailServicesImpl extends ForecastDetailServicesImpl<PscForecast> implements PscForecastDetailServices, Serializable {

    private static final Logger LOG = Logger.getLogger(PscForecastDetailServicesImpl.class.getName());
    private static final long serialVersionUID = 8993596753945847377L;

    public PscForecastDetailServicesImpl() {
        super(PscForecast.class);
    }

    public PscForecastDetailServicesImpl(Class<PscForecast> entityClass) {
        super(entityClass);
    }

    @Override
    public PscForecast computeEntitlement(PscForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecast computeOpeningStock(PscForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecast getPreviousMonthProduction(PscForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecast getNextMonthProduction(PscForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecast computeClosingStock(PscForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecast computeAvailability(PscForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecast computeLifting(PscForecast production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecast enrich(PscForecast production) throws NoRealizablePriceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PscForecast computeGrossProduction(PscForecast forecast) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
