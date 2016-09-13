/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastDetailServices;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetailPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;
import com.nnpcgroup.cosm.util.COSMPersistence;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author 18359
 */
@Dependent
public class JvForecastServicesImpl extends ForecastServicesImpl<JvForecast> implements JvForecastServices, Serializable {

  private static final Logger LOG = Logger.getLogger(JvForecastServicesImpl.class.getName());

    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastServicesImpl(){
        super(JvForecast.class);
    }

    public JvForecastServicesImpl(Class<JvForecast> entityClass) {
        super(entityClass);
    }

    @Inject
    @COSMPersistence
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


}
