/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.psc;

import com.nnpcgroup.cosm.ejb.AbstractCrudServices;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.forecast.psc.PscForecast;

import java.util.List;

/**
 *
 * @author 18359
 */
public interface PscForecastServices extends AbstractCrudServices<PscForecast> {

    public List<PscForecast> findByYearAndMonth(int year, int month);

    public PscForecast find(int year, int month, FiscalArrangement fa);

    public void delete(int year, int month, FiscalArrangement fa);

}
