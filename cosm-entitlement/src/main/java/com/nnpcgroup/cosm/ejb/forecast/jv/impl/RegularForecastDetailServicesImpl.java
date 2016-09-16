/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.RegularForecastDetailServices;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;

import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public abstract class RegularForecastDetailServicesImpl extends ForecastDetailServicesImpl<JvForecastDetail> implements RegularForecastDetailServices {

    private static final Logger LOG = Logger.getLogger(RegularForecastDetailServicesImpl.class.getName());

    public RegularForecastDetailServicesImpl(Class<JvForecastDetail> entityClass) {
        super(entityClass);
    }

}
