/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;

import java.util.List;

/**
 *
 * @author 18359
 */
public interface JvForecastServices extends ForecastServices<JvForecast> {

    public List<JvForecast> findByYearAndMonth(int year, int month);

    public JvForecast findByContractPeriod(int year, int month, FiscalArrangement fa);

}
