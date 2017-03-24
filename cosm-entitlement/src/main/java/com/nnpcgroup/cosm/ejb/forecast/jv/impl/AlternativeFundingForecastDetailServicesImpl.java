/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.PriceBean;
import com.nnpcgroup.cosm.ejb.contract.ContractServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.AlternativeFundingForecastDetailServices;
import com.nnpcgroup.cosm.entity.forecast.jv.AlternativeFundingForecastDetail;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 * @param <T>
 * @author 18359
 */
public abstract class AlternativeFundingForecastDetailServicesImpl<T extends AlternativeFundingForecastDetail> extends JvForecastDetailServicesImpl<T> implements AlternativeFundingForecastDetailServices<T> {

    private static final Logger LOG = Logger.getLogger(AlternativeFundingForecastDetailServicesImpl.class.getName());
    private static final long serialVersionUID = -5826414842990437262L;

    @EJB
    PriceBean priceBean;

    @EJB
    ContractServices contractBean;

    public AlternativeFundingForecastDetailServicesImpl(Class<T> entityClass) {
        super(entityClass);
    }

}
