/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.entity.EquityType;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.JointVenture;
import com.nnpcgroup.cosm.entity.contract.ContractPK;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastPK;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.exceptions.NoRealizablePriceException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvForecastServices.class)
//@Dependent
public class JvForecastServicesBean extends JvForecastServicesImpl<JvForecast> implements JvForecastServices<JvForecast>, Serializable{

    private static final Logger LOG = Logger.getLogger(JvForecastServicesBean.class.getName());
    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastServicesBean(Class<JvForecast> entityClass) {
        super(entityClass);
    }

    public JvForecastServicesBean(){
        super(JvForecast.class);
    }




}
