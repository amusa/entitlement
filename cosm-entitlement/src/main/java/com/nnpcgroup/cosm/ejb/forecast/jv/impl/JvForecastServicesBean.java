/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvForecastServices.class)
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
