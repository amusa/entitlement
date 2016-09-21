/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv.impl;

import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastDetailServices;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvForecastDetailServices.class)
public class JvForecastDetailServicesBean extends JvForecastDetailServicesImpl<JvForecastDetail> implements JvForecastDetailServices<JvForecastDetail>, Serializable {

    private static final Logger LOG = Logger.getLogger(JvForecastDetailServicesBean.class.getName());
    private static final long serialVersionUID = 8993596753945847377L;

    public JvForecastDetailServicesBean() {
        super(JvForecastDetail.class);
    }

}
