/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.CarryForecastDetailServices;
import com.nnpcgroup.cosm.entity.forecast.jv.CarryForecastDetail;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public abstract class CarryForecastDetailServicesImpl extends AlternativeFundingForecastDetailServicesImpl<CarryForecastDetail> implements CarryForecastDetailServices {

    private static final Logger LOG = Logger.getLogger(CarryForecastDetailServicesImpl.class.getName());

    public CarryForecastDetailServicesImpl(Class<CarryForecastDetail> entityClass) {
        super(entityClass);
    }

}
