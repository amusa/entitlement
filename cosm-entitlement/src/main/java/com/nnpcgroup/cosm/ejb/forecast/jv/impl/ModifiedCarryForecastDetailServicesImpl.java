/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.ModifiedCarryForecastDetailServices;
import com.nnpcgroup.cosm.entity.forecast.jv.ModifiedCarryForecastDetail;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
public class ModifiedCarryForecastDetailServicesImpl extends AlternativeFundingForecastDetailServicesImpl<ModifiedCarryForecastDetail> implements ModifiedCarryForecastDetailServices {

    private static final Logger LOG = Logger.getLogger(ModifiedCarryForecastDetailServicesImpl.class.getName());

    public ModifiedCarryForecastDetailServicesImpl(Class<ModifiedCarryForecastDetail> entityClass) {
        super(entityClass);
    }

}
