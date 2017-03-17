/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.impl;

import com.nnpcgroup.cosm.controller.GeneralController;
import com.nnpcgroup.cosm.ejb.FiscalArrangementBean;
import com.nnpcgroup.cosm.ejb.forecast.ForecastDetailServices;
import com.nnpcgroup.cosm.ejb.impl.CommonServicesImpl;
import com.nnpcgroup.cosm.entity.forecast.ForecastDetail;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 * @param <T>
 * @author 18359
 */
public abstract class ForecastDetailServicesImpl<T extends ForecastDetail> extends CommonServicesImpl<T> implements ForecastDetailServices<T>, Serializable {

    @Inject
    GeneralController genController;

    @EJB
    FiscalArrangementBean fiscalBean;

    public ForecastDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

    public GeneralController getGenController() {
        return genController;
    }
}
