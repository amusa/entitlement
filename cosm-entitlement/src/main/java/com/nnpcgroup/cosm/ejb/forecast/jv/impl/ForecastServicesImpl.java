/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.forecast.jv.ForecastServices;
import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * @param <T>
 * @author 18359
 */
@Dependent
public abstract class ForecastServicesImpl<T extends Forecast> extends CommonServicesImpl<T> implements ForecastServices<T>, Serializable {

    @Inject
    GeneralController genController;

    @EJB
    FiscalArrangementBean fiscalBean;

    public ForecastServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }
}
