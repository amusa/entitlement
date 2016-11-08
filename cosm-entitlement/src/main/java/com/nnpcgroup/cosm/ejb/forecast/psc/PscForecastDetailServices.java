/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.psc;

import com.nnpcgroup.cosm.ejb.forecast.ForecastDetailServices;
import com.nnpcgroup.cosm.entity.contract.OilField;
import com.nnpcgroup.cosm.entity.forecast.psc.PscForecastDetail;

/**
 *
 * @author 18359
 */
public interface PscForecastDetailServices extends ForecastDetailServices<PscForecastDetail> {

    public PscForecastDetail find(int year, int month, OilField oilField);

    public void delete(int year, int month, OilField oilField);
}
