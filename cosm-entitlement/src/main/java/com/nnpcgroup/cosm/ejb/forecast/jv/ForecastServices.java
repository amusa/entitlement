/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.forecast.jv;

import com.nnpcgroup.cosm.ejb.AbstractCrudServices;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import java.io.Serializable;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface ForecastServices<T extends Forecast> extends AbstractCrudServices<T>, Serializable {
    
}
