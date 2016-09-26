/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;
import java.util.List;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface JvForecastDetailServices<T extends JvForecastDetail> extends ForecastDetailServices<T> {

    public void delete(int year, int month, Contract contract);

    public void delete(int year, int month, FiscalArrangement fa);

    public void delete(List<T> jvDetails);

}
